package com.phenix.activemq;

import com.phenix.farmer.config.MQConfig;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ActiveMQManager {
	private final static transient Logger Logger = LoggerFactory.getLogger(ActiveMQManager.class);
	public final static int DefaultSessionsPerConnection = 100;
	public final static int DefaultConnecitonPerFactory = 5;
	public final static int DefaultTimeOutInMillis = 10000;
	public final static String StatPrefix = "ActiveMQ.Statistics.Destination.";
	
	private Map<String, MQConfig> configs;	
	private Map<String, PooledConnectionFactory> pooledFactories;
	
	private ActiveMQManager() {
		configs = new ConcurrentHashMap<>();
		pooledFactories = new ConcurrentHashMap<>();
	}
	
	public static ActiveMQManager newInstance() {
		return new ActiveMQManager();
	}
	
	public ActiveMQManager initConnections(List<MQConfig> configs_) {
		for (MQConfig mqConfig : configs_) {			
			initConnection(mqConfig);
		}
		return this;
	}
	
	public ActiveMQManager initConnection(MQConfig config_) {
		configs.put(config_.getConnectionName(), config_);
		
		ActiveMQConnectionFactory fac;
		if(StringUtils.isBlank(config_.getUserName()) && StringUtils.isBlank(config_.getPassword()))
			fac = new ActiveMQConnectionFactory(config_.getConnStr());
		else
			fac = new ActiveMQConnectionFactory(config_.getUserName(), config_.getPassword(), config_.getConnStr());
		fac.setCopyMessageOnSend(false);//don't copy as we always create new messages on each publish
		//fac.setAlwaysSyncSend(false);
		//fac.getPrefetchPolicy().setTopicPrefetch(2);		
		fac.setAlwaysSessionAsync(false);//bypass the session internal message queue to dispatch to consumers directly
		PooledConnectionFactory pooledFac = new PooledConnectionFactory(fac);
		pooledFac.setMaxConnections(config_.getConnectionPerFactory());
		pooledFac.setMaximumActiveSessionPerConnection(config_.getSessionPerConnection());		
		pooledFac.setCreateConnectionOnStartup(true);//warm up
		pooledFac.setIdleTimeout(24 * 60 * 60 * 1000);//all day alive
		pooledFac.setBlockIfSessionPoolIsFull(false);//throw exception explicitly when pool resource are exausted
		fac.setExceptionListener(e -> {
			String errMsg = "Connection " + config_.getConnectionName() + " with url " + config_.getConnStr() + " failed";
			Logger.error(errMsg);
			throw new IllegalStateException(e);
		});

		pooledFac.start();		
		if(pooledFac.getNumConnections() == 0) {
			String errMsg = "Connection " + config_.getConnectionName() + " with url " + config_.getConnStr() + " failed";
			Logger.error(errMsg);
			throw new IllegalStateException(errMsg);
		}

		pooledFactories.put(config_.getConnectionName(), pooledFac);		
		
		Logger.info(String.format("MQConnection [%s] is connected with url [%s]", config_.getConnectionName(), config_.getConnStr()));
		return this;
	}
	
	public Connection getConnection(String name_) throws JMSException {		
		Connection conn = pooledFactories.get(name_).createConnection();
		
		if(conn.getExceptionListener() == null) {
			conn.setExceptionListener(exception -> {
					// TODO recover all the connection and topics
					Logger.error(exception.getMessage(), exception);
				}
			);
		}
		
		//Note: below start is handled by pooledConneciton, no worry about mutiple-start		
		conn.start();
		
		return conn;
	}
	
	public Session getSession(String name_, boolean transacted_, int acknowledgeMode_) throws JMSException {
		return getConnection(name_).createSession(transacted_, acknowledgeMode_);
	}
	
	//When to return
	public Session getSession(String name_) throws JMSException {		
		return getSession(name_, false, Session.AUTO_ACKNOWLEDGE);
	}
	
	public void cleanup() {		
		Logger.info("Going to clean up " + getClass().getSimpleName());
		
		pooledFactories.forEach((k, v) -> {
			v.clear();
		});		
		configs.clear();
		pooledFactories.clear();
		
		Logger.info(getClass().getSimpleName() + " is cleaned up");
	}
}
