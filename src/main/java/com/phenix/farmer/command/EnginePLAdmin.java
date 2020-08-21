package com.phenix.farmer.command;

import com.phenix.admin.AbstractAdminCommand;
import com.phenix.farmer.FarmerController;
import com.phenix.farmer.FarmerDataManager;
import com.phenix.farmer.signal.PositionBalanceManager;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.mina.core.session.IoSession;

import java.time.LocalDate;

public class EnginePLAdmin extends AbstractAdminCommand {
    public EnginePLAdmin() {
        super();
        level1Command = "engine";
        level2Command = "pl";
        level3Command = "list";
        initOptions();
    }

    @Override
    public void handle(IoSession ioSession_, String[] paras_) {
        if (!ArrayUtils.isEmpty(paras_)) {
            ioSession_.write(NO_PARAMETER_FOUND);
        } else {
            PositionBalanceManager pbManager = FarmerController.getInstance().getPositionBalanceManager();
            LocalDate today = FarmerDataManager.TODAY;
            ioSession_.write(pbManager.getRealTimePLReport(today));
        }
    }

    @Override
    public void initOptions() {
    }
}
