package com.phenix.farmer.command;

import com.phenix.admin.AbstractAdminCommand;
import com.phenix.exception.AdminCommandException;
import com.phenix.farmer.FarmerController;
import com.phenix.farmer.signal.SignalMode;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.mina.core.session.IoSession;

public class EngineStateManageAdmin extends AbstractAdminCommand {
    public EngineStateManageAdmin() {
        super();
        level1Command = "engine";
        level2Command = "state";
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
            String sManual = cl.getOptionValue("m");
            String sStop = cl.getOptionValue("s");
            if (StringUtils.isEmpty(sManual)) {
                throw new AdminCommandException("Illegal parameter -m " + sManual);
            }
            boolean stop = StringUtils.isEmpty(sStop) ? false : true;

            SignalMode nmode = SignalMode.valueOf(sManual);
            SignalMode omode = FarmerController.getInstance().getSignalMode();
            FarmerController.getInstance().setSignalMode(nmode);
            String msg = "Signal Mode is updated from " + omode + " to be " + nmode;

            if (stop) {
                FarmerController.getInstance().setStopFlag(true);
                msg = ", Engine is going to be stopped";
            }

            ioSession_.write(msg);
        }
    }

    @Override
    public void initOptions() {
        options.addOption("m", "mode", true, "mode switch");
        options.addOption("s", "stop", true, "stop engine");
    }
}
