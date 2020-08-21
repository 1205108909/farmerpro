package com.phenix.admin;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handler for handling admin requests. This handler doesn't work on the handling process
 * but put an admin event to disruptor, and the admin event will be processed by disruptors.
 * 
 * @author phenix
 */
public class AdminServiceIoHandler extends IoHandlerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminServiceIoHandler.class);
    private AdminRequestFactory adminRequestFactory;

    public AdminServiceIoHandler(AdminRequestFactory adminRequestFactory_) {
        adminRequestFactory = adminRequestFactory_;
    }

    @Override
    public void messageReceived(IoSession ioSession, Object message) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Message received from " + ioSession.getRemoteAddress().toString() + " and message=" + message);
        }
        String parameters = message.toString();
        try {
            adminRequestFactory.handle(ioSession, parameters);
        } catch (Exception e_) {
            LOGGER.error(e_.getMessage(), e_);
            ioSession.write(e_.getMessage());
            ioSession.write(adminRequestFactory.getHelp());
        }
    }

    @SuppressWarnings("deprecation")
    public void sessionIdle(IoSession session, IdleStatus status) {
        session.close();
    }
}
