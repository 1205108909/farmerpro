package com.phenix.farmer.command;

import com.phenix.admin.AbstractAdminCommand;
import com.phenix.farmer.FarmerConfigManager;
import com.phenix.farmer.FarmerController;
import com.phenix.farmer.FarmerDataManager;
import com.phenix.util.FormattedTable;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.mina.core.session.IoSession;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class EngineStateAdmin extends AbstractAdminCommand {
    public EngineStateAdmin() {
        super();
        level1Command = "engine";
        level2Command = "state";
        level3Command = "info";
        initOptions();
    }

    @Override
    public void handle(IoSession ioSession_, String[] paras_) {
        if (!ArrayUtils.isEmpty(paras_)) {
            ioSession_.write(NO_PARAMETER_FOUND);
        } else {
            int nc = FarmerController.getInstance().getNumOfController();
            LocalTime t = FarmerController.getInstance().getTimeNow();
            String instance = FarmerConfigManager.getInstance().getAdminConfig().getAdminStr();
            List<Object> header = new ArrayList<>(1);
            FormattedTable table = new FormattedTable();
            header.add("Instance");
            header.add("TotalMemory");
            header.add("FreeMemory");
            header.add("NumOfController");
            header.add("TimeNow");
            header.add("Mode");
            header.add("EventBufferSize");
            header.add("AvailableCash");

            table.addRow(header);

            List<Object> row = new ArrayList<>(5);
            row.add(instance);
            row.add((Runtime.getRuntime().totalMemory() >> 20) + "MB");
            row.add((Runtime.getRuntime().freeMemory() >> 20) + "MB");
            row.add(nc);
            row.add(t);
            row.add(FarmerController.getInstance().getSignalMode());
            row.add(FarmerConfigManager.getInstance().getInstanceConfig().getEventBufferSize());

            LocalDate today = FarmerDataManager.TODAY;
            double availableCash = FarmerController.getInstance().getPositionBalanceManager().getAvailableCash(today);
            row.add(availableCash);
            table.addRow(row);

            ioSession_.write(table.toString());
        }
    }

    @Override
    public void initOptions() {

    }
}
