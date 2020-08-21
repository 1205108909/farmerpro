package com.phenix.farmer.command;

import com.phenix.admin.AbstractAdminCommand;
import com.phenix.data.Security;
import com.phenix.exception.AdminCommandException;
import com.phenix.farmer.FarmerDataManager;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.mina.core.session.IoSession;

public class DataCacheManageAdmin extends AbstractAdminCommand {
    public DataCacheManageAdmin() {
        super();
        level1Command = "data";
        level2Command = "cache";
        level3Command = "manage";
        initOptions();
    }

    @Override
    public void handle(IoSession ioSession_, String[] paras_) {
        if (ArrayUtils.isEmpty(paras_)) {
            ioSession_.write(NO_PARAMETER_FOUND);
        } else {
            CommandLine cl;
            try {
                cl = parser.parse(options, paras_);
            } catch (ParseException e) {
                throw new AdminCommandException(e);
            }
            String cacheName = cl.getOptionValue("n");
            String operation = cl.getOptionValue("o");
            String value = cl.getOptionValue("v");
            if (StringUtils.isEmpty(cacheName)) {
                throw new AdminCommandException("Illegal parameter -n " + cacheName);
            }
            if (StringUtils.isEmpty(operation)) {
                throw new AdminCommandException("Illegal parameter -o " + operation);
            }
            if (StringUtils.isEmpty(value)) {
                throw new AdminCommandException("Illegal parameter -v " + value);
            }

            if (cacheName.equals("ForbiddenList")) {
                Security sec = FarmerDataManager.getInstance().getSecurity(value);
                if (sec == null) {
                    ioSession_.write("Unknown Security:" + value);
                } else {
                    if ("add".equals(operation)) {
                        FarmerDataManager.getInstance().addForbiddenSecurity(sec);
                    } else if ("delete".equals(operation)) {
                        FarmerDataManager.getInstance().removeForbiddenSecurity(sec);
                    } else {
                        ioSession_.write("Unknown operation:" + operation);
                    }
                    ioSession_.write(DONE);
                }
            } else {
                ioSession_.write("Only support forbiddenList for now");
            }
        }
    }

    @Override
    public void initOptions() {
        options.addOption("n", "name", true, "cache name");
        options.addOption("o", "operation", true, "operation");
        options.addOption("v", "value", true, "cache entry");
    }
}
