package com.phenix.farmer.command;

import com.google.common.collect.Lists;
import com.phenix.admin.AbstractAdminCommand;
import com.phenix.farmer.FarmerController;
import com.phenix.farmer.signal.ISignal;
import com.phenix.util.FormattedTable;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.mina.core.session.IoSession;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SignalListAdmin extends AbstractAdminCommand {
    public SignalListAdmin() {
        super();
        level1Command = "engine";
        level2Command = "signal";
        level3Command = "list";
        initOptions();
    }

    @Override
    public void handle(IoSession ioSession_, String[] paras_) {
        if (!ArrayUtils.isEmpty(paras_)) {
            ioSession_.write(NO_PARAMETER_FOUND);
        } else {
            List<ISignal> signals = FarmerController.getInstance().getSignals();
            List<Object> header = new ArrayList<>(1);
            FormattedTable table = new FormattedTable();
            header.add("SignalName");
            table.addRow(header);

            Set<String> set = new HashSet<>();
            for (ISignal s : signals) {
                if (!set.contains(s.getClass().getName())) {
                    set.add(s.getClass().getName());
                    List<Object> row = Lists.newArrayList(s.getClass().getName());
                    table.addRow(row);
                }
            }

            ioSession_.write(table.toString());
        }
    }

    @Override
    public void initOptions() {

    }
}
