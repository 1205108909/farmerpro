package com.phenix.farmer.command;

import com.phenix.admin.AbstractAdminCommand;
import com.phenix.data.Security;
import com.phenix.exception.AdminCommandException;
import com.phenix.farmer.FarmerController;
import com.phenix.farmer.signal.SignalMode;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.mina.core.session.IoSession;

import java.util.Set;

public class SignalOrderCloseAdmin extends AbstractAdminCommand {
    public SignalOrderCloseAdmin() {
        super();
        level1Command = "signal";
        level2Command = "order";
        level3Command = "close";
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

            String sForce = cl.getOptionValue("f");
            String sManual = cl.getOptionValue("m");
            if (sForce != null && !"true".equalsIgnoreCase(sForce) && !"false".equalsIgnoreCase(sForce)) {
                throw new AdminCommandException("Illegal string: " + sForce);
            }
            if (sManual != null && !"true".equalsIgnoreCase(sManual) && !"false".equalsIgnoreCase(sManual)) {
                throw new AdminCommandException("Illegal string: " + sForce);
            }
            boolean force = sForce == null ? false : Boolean.parseBoolean(sForce);
            boolean manual = sManual == null ? false : Boolean.parseBoolean(sManual);
            if (!force) {
                ioSession_.write("-f must be true, otherwise ignore");
                return;
            }

            Set<Security> securities = FarmerController.getInstance().getSecurities();
            for (Security s : securities) {
                FarmerController.getInstance().closeOrder(s);
            }

            if (manual) {
                FarmerController.getInstance().setSignalMode(SignalMode.MANUAL);
                ioSession_.write("Close order instruction has been sent to all signal and signal mode is updated to be " + SignalMode.MANUAL);
            } else {
                ioSession_.write("Close order instruction has been sent to all signal");
            }
        }
    }

    @Override
    public void initOptions() {
        options.addOption("f", "force", true, "close all order");
        options.addOption("m", "manual", true, "switch to manual mode");
    }
}
