package com.phenix.admin;

import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yangfei on 2015/11/18.
 * Description:
 */
public abstract class AbstractAdminCommand {
    protected static final Logger LOGGER = LoggerFactory.getLogger(AdminServiceIoHandler.class);
    protected final static String NO_PARAMETER_FOUND = "No parameters found";
    protected final static String NO_PARAMETER_REQUIRED = "No parameters required";
    protected final static String DONE = "Done";
    protected Options options = new Options();
    protected CommandLineParser parser = new DefaultParser();
    protected String level1Command;
    protected String level2Command;
    protected String level3Command;

    public AbstractAdminCommand() {
    }

    public abstract void handle(IoSession ioSession, String[] paras);

    public abstract void initOptions();

    public String getLevel3Command() {
        return level3Command;
    }

    public String getLevel2Command() {
        return level2Command;
    }

    public String getLevel1Command() {
        return level1Command;
    }

    public String toString() {
        return "Request :" + AdminConstants.SPACE + level1Command + AdminConstants.SPACE + level2Command + AdminConstants.SPACE + level3Command;
    }
}
