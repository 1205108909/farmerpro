package com.phenix.farmer;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import com.google.common.collect.ImmutableMap;
import com.phenix.data.IEngineConfig;
import com.phenix.data.Security;
import com.phenix.exception.DuplicateDataException;
import com.phenix.exception.IllegalDataException;
import com.phenix.farmer.config.*;
import com.phenix.farmer.signal.SignalMode;
import com.phenix.util.ConfigMatchCriteria;
import com.phenix.util.DateUtil;
import com.phenix.util.Util;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;


public class FarmerConfigManager {
    public final static int DEFAULT_NUM_OF_WORKER = 4;
    @Getter
    private final static FarmerConfigManager Instance = new FarmerConfigManager();
    private final static transient Logger Logger = LoggerFactory.getLogger(FarmerConfigManager.class);
    private Map<Class<?>, SortedMap<ConfigMatchCriteria, IEngineConfig>> signalConfigMap = new HashMap<>();
    @Getter
    private InstanceConfig instanceConfig;
    private transient Map<String, DBConfig> dbConfigs = new HashMap<>();
    private Map<String, String> topic2MQMapping = new HashMap<>();
    @Getter
    private AdminConfig adminConfig;
    @Getter
    private TDFConfig tdfConfig;
    @Getter
    private FeatureConfig featureConfig;
	@Getter
	private BackTestConfig backTestConfig;

    @Getter
    private Element signalElement;
	
    @Getter
    private List<MQConfig> mqConfigs;

    @Getter
    private PositionBalanceConfig pbConfig;
	
	private FarmerConfigManager() {
        //init(engineConfigPath, logConfigPath);
    }

    public void init(String configPath_, String logconfigPath_) {
        // TODO: initlog
        SAXBuilder builder = new SAXBuilder();
        Document doc = null;
        try {
            doc = builder.build(configPath_);
            signalElement = doc.getRootElement().getChild("FarmerEngine").getChild("Signal");

            initInstanceConfig(doc);
            initSignalConfig(doc);
            initDBConfig(doc);
            initMQConfig(doc);
            initLogConfig(logconfigPath_);
            initAdmin(doc);
            initTDFConfig(doc);
            initPositionBalanceConfig(doc);
			initFeatureConfig(doc);
            initBackTestConfig(doc);
		} catch (Exception e) {			
            throw (new RuntimeException(e));
        }
    }

    public void initLogConfig(String path_) {
		LoggerContext lc = (LoggerContext)LoggerFactory.getILoggerFactory();
        JoranConfigurator configurator = new JoranConfigurator();
        configurator.setContext(lc);
        lc.reset();
        try {
            configurator.doConfigure(path_);
        } catch (JoranException e_) {
            throw new IllegalDataException(e_);
        }
    }
	
    private void initInstanceConfig(Document doc_) {
        Element insconfig = doc_.getRootElement().getChild("InstanceConfig");
        Environment env = Environment.valueOf(insconfig.getChildText("Environment"));
        SignalMode mode = SignalMode.valueOf(insconfig.getChildText("SignalMode"));
        LocalDateTime dataDate = insconfig.getChildText("DataDate").equals("-1") ? LocalDateTime.now() : DateUtil.getDate(
                insconfig.getChildText("DataDate")).atStartOfDay();
        int numOfControllers = Integer.parseInt(insconfig.getChildText("NumOfWorker"));
        double commissionRate = Double.parseDouble(insconfig.getChildText("CommissionRate"));
        LocalTime shutDownTime = DateUtil.getTime(insconfig.getChildText("DailyShutDownTime"));
        int eventBufferSize = Util.evaluateAsInt(insconfig.getChildText("EventBufferSize"));
        String universePath = insconfig.getChildText("Universe");
        String signalSecurityMappingPath = insconfig.getChildText("SignalSecurityMapping");
        String index2FutureMappingPath = insconfig.getChildText("Index2FutureMapping");
        String tradingDayPath = insconfig.getChildText("TradingDayPath");
		String indexComponentPath = insconfig.getChildText("IndexComponentPath");
		RunningMode runningMode = RunningMode.valueOf(insconfig.getChildText("RunningMode"));
		String persisPath = insconfig.getChildText("PersistPath");
        instanceConfig = new InstanceConfig(env, LocalDateTime.now(), dataDate,
                numOfControllers == -1 ? DEFAULT_NUM_OF_WORKER : numOfControllers, mode, commissionRate, shutDownTime,
				eventBufferSize, universePath, signalSecurityMappingPath, index2FutureMappingPath, tradingDayPath,
				indexComponentPath, runningMode, persisPath);
    }
	
    private void initDBConfig(Document doc_) {
		Element ele = doc_.getRootElement().getChild("DBConfig");		
		
		for(Element e : ele.getChildren()) {
            String connName = e.getAttributeValue("name");
            String server = e.getChildText("server");
            String database = e.getChildText("database");
            String user = e.getChildText("user");
            String password = e.getChildText("password");
            String driver = e.getChildText("driver");
            dbConfigs.put(connName, new DBConfig(connName, server, user, password, database, driver));
        }
    }

    private void initTDFConfig(Document doc_) {
        Element ele = doc_.getRootElement().getChild("TDFConfig");
        String ip = ele.getChildText("ip");
        String port = ele.getChildText("port");
        String username = ele.getChildText("username");
        String password = ele.getChildText("password");
        String market = ele.getChildText("market");
        String time = ele.getChildText("time");
        tdfConfig = new TDFConfig(ip, port, username, password, market, time);
    }
	
    private void initSignalConfig(Document doc_) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        XPathFactory xFactory = XPathFactory.instance();
        XPathExpression<Element> expr = xFactory.compile("/Configuration/FarmerEngine/SharedConfigs", Filters.element());
        Element es = expr.evaluateFirst(doc_);
		
        for (Element e : es.getChildren()) {
            int priority = 0;
			Logger.info("Init " + e.getName());			
            String className = e.getAttributeValue("class");
			
            @SuppressWarnings("unchecked")
            Class<IEngineConfig> c = (Class<IEngineConfig>) Class.forName(className);
            List<Element> configs = e.getChildren("Config");
			
			for(Element ec : configs) {
                IEngineConfig config = c.getDeclaredConstructor().newInstance();
                ConfigMatchCriteria criteria = new ConfigMatchCriteria(priority++, ec.getChild("Criteria").getChild("Security"));
                config.readConfig(ec.getChild("Data"));
                updateConfig(criteria, config);
            }
        }
    }

    private void updateConfig(ConfigMatchCriteria criteria_, IEngineConfig config_) {
		if(!signalConfigMap.containsKey(config_.getClass())) {
            SortedMap<ConfigMatchCriteria, IEngineConfig> configs = new TreeMap<>();
            configs.put(criteria_, config_);
            signalConfigMap.put(config_.getClass(), configs);
        } else {
            SortedMap<ConfigMatchCriteria, IEngineConfig> configs = signalConfigMap.get(config_.getClass());
            configs.put(criteria_, config_);
        }
    }
	
    private void initMQConfig(Document doc_) {
        mqConfigs = new ArrayList<>();

        List<Element> amqconfig = doc_.getRootElement().getChild("AMQConfig").getChildren("AMQConnection");
        for (Element e : amqconfig) {
            String name = e.getAttributeValue("name");
            String ip = e.getChildText("server");
            String port = e.getChildText("port");
            String protocal = e.getChildText("protocal");
            String params = e.getChildText("defaultParams");
            String userName = e.getChildText("user");
            String password = e.getChildText("password");
            int reconnectDelay = Integer.parseInt(e.getChildText("reconnectDelay"));
            int maxReconnectAttemps = Integer.parseInt(e.getChildText("maxReconnectAttempt"));
            int connectionPerFactory = Integer.parseInt(e.getChildText("connectionPerFactory"));
            int sessionPerConnection = Integer.parseInt(e.getChildText("sessionPerConnection"));
            boolean failover = Boolean.parseBoolean(e.getChildText("failover"));
            String ttopics = e.getChildText("topic");
            List<String> topics = Arrays.asList(ttopics.split(","));
			
            String cCriteria = e.getChild("Criteria").getAttributeValue("msgPrefix");
            List<String> prefixes = Arrays.asList(cCriteria.split(","));
            prefixes.forEach(v -> {
				if(topic2MQMapping.containsKey(v))
                    throw new DuplicateDataException("Duplicate mapping with prefix->mq, please check prefix name");
                topic2MQMapping.put(v, name);
            });
			
			MQConfig c = new MQConfig(name, ip, port, protocal, params, reconnectDelay, 
                    maxReconnectAttemps, connectionPerFactory, sessionPerConnection, failover, topics, userName, password);
            mqConfigs.add(c);
        }
    }

    private void initAdmin(Document doc_) {
        Element as = doc_.getRootElement().getChild("AdminServer");
        String ip = as.getChildText("HostName");
        String port = as.getChildText("Port");
        adminConfig = new AdminConfig(ip, Integer.parseInt(port));
    }

    private void initPositionBalanceConfig(Document doc_) {
        Element as = doc_.getRootElement().getChild("PositionBalanceConfig");
        double initialCash = Double.parseDouble(StringUtils.remove(as.getChildText("InitialCash"), '_'));
        pbConfig = new PositionBalanceConfig(initialCash);
    }

	private void initFeatureConfig(Document doc_) {
		Element e = doc_.getRootElement().getChild("FeatureConfig");
		String featureIndexConfig = e.getChildText("FutureIndexSpread");
		String kline = e.getChildText("KLine");
		String dailyFeature = e.getChildText("DailyFeature");
		String universe = e.getChildText("Universe");
		featureConfig = new FeatureConfig(featureIndexConfig, kline, dailyFeature, universe);
	}

	private void initBackTestConfig(Document doc_) {
        Element e = doc_.getRootElement().getChild("BackTestConfig");
		String startDate = e.getChildText("StartDate");
		String endDate = e.getChildText("EndDate");
		LocalDate sd = DateUtil.getDate(startDate);
		LocalDate ed = "-1".equalsIgnoreCase(endDate) ? LocalDate.now() : DateUtil.getDate(endDate);
		String index = e.getChildText("IndexName");
		backTestConfig = new BackTestConfig(sd, ed, index);
    }
	
    public Map<String, DBConfig> getDbConfigs() {
        return ImmutableMap.copyOf(dbConfigs);
    }
	
    public String topic2MQName(String name_) {
		for(Map.Entry<String, String> e : topic2MQMapping.entrySet()) {
			if(name_.startsWith(e.getKey())) {
                return e.getValue();
            }
        }
        return null;
    }

    public <T> T getConfig(Class<T> clazz_, Security sec_) {
        SortedMap<ConfigMatchCriteria, IEngineConfig> m = signalConfigMap.get(clazz_);
		if(m == null)
            return null;

        //m.values().forEach(System.out::println);
		T config = (T)m.entrySet().stream()
                .filter(e -> e.getKey().match(sec_))
                .findFirst()
                .get()
                .getValue();

        return config;
    }


}
