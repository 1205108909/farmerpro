package com.phenix.admin;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;
import org.apache.mina.filter.logging.LogLevel;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

/**
 * Admin service controller through which we can config transports, ip, and
 * service handler.
 *
 */
public class AdminService {
	// Filter for LOGGER.
	private static final String FILTER_LOGGER = "LOGGER";
	// Filter for decoding.
	private static final String FILTER_CODEC = "codec";
	// Default Buffer size.
	private static final int DEFAULT_BUFFER_SIZE = 8192;
	// Default Idle time.
	private static final int DEFAULT_IDLE_TIME = 1;

	// Mina IoAcceptor.
	private IoAcceptor acceptor = new NioSocketAcceptor();
	// Io handler adapter.
	private IoHandlerAdapter handler; //= AdminServiceIoHandler.INSTANCE;
	// Transport.
	private int port;
	// Ip.
	private String ip;
	// Buffer size
	private int bufferSize = DEFAULT_BUFFER_SIZE;
	// Idle time.
	private int idleTime = DEFAULT_IDLE_TIME;

	/**
	 * Constructor.
	 */
	private AdminService(String ip_, int port_, IoHandlerAdapter handler_) {
		this.ip = ip_;
		this.port = port_;
		this.handler = handler_;
	}

	public static AdminService of(String ip_, int port_, IoHandlerAdapter handler_) {
		return new AdminService(ip_, port_, handler_);
	}

	/**
	 * Initialize the Admin service.
	 */
	public void start() {
		LoggingFilter loggingFilter = new LoggingFilter();
		disableLogs(loggingFilter);

		acceptor.getFilterChain().addLast(FILTER_LOGGER, loggingFilter);
		// Don't use the TextLineCodec now, since the admin.exe doesn't
		// send us the return char.
		DemuxingProtocolCodecFactory factory = new DemuxingProtocolCodecFactory();
		factory.addMessageDecoder(new AdminCodec());
		factory.addMessageEncoder(String.class, new AdminCodec());
		ProtocolCodecFilter filter = new ProtocolCodecFilter(factory);
		acceptor.getFilterChain().addLast(FILTER_CODEC, filter);
		acceptor.setHandler(handler);

		acceptor.getSessionConfig().setReadBufferSize(bufferSize);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, idleTime);

		try {
			acceptor.bind(new InetSocketAddress(ip, port));
		} catch (IOException exception) {
			throw new RuntimeException("Cannot initialize admin services at address. [ip: " + ip + ", port: " + port + "]" + exception, exception);
		}
	}

	/**
	 * Disable logs of Mina.
	 * 
	 * @param loggingFilter
	 *            Log filter.
	 */
	private void disableLogs(LoggingFilter loggingFilter) {
		loggingFilter.setSessionClosedLogLevel(LogLevel.NONE);
		loggingFilter.setSessionCreatedLogLevel(LogLevel.NONE);
		loggingFilter.setSessionOpenedLogLevel(LogLevel.NONE);
		loggingFilter.setMessageReceivedLogLevel(LogLevel.NONE);
		loggingFilter.setMessageSentLogLevel(LogLevel.NONE);
	}

	public void cleanup() {
		acceptor.unbind();
	}
}
