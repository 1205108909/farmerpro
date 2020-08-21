package com.phenix.farmer.command;

import com.google.common.collect.Lists;
import com.google.common.collect.Table;
import com.phenix.admin.AbstractAdminCommand;
import com.phenix.data.Security;
import com.phenix.exception.AdminCommandException;
import com.phenix.farmer.FarmerController;
import com.phenix.farmer.FarmerDataManager;
import com.phenix.farmer.event.ObjectWrapperEvent;
import com.phenix.farmer.event.SyncTaskEvent;
import com.phenix.farmer.signal.ISignal;
import com.phenix.util.FormattedTable;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.mina.core.session.IoSession;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.FutureTask;

public class SignalEvaluationAdmin extends AbstractAdminCommand {
    private final static long TIME_OUT_IN_MILLISECONDS = 5000;

    public SignalEvaluationAdmin() {
        super();
        level1Command = "signal";
        level2Command = "evaluation";
        level3Command = "print_diagnosis";
        initOptions();
    }

    @Override
    public void handle(IoSession ioSession_, String[] paras_) {
        if (ArrayUtils.isEmpty(paras_)) {
            ioSession_.write(NO_PARAMETER_FOUND);
        } else {
            try {
                CommandLine cl;
                try {
                    cl = parser.parse(options, paras_);
                } catch (ParseException e) {
                    throw new AdminCommandException(e);
                }
                String sec = cl.getOptionValue("s");
                String signalName = cl.getOptionValue("n");
                if (StringUtils.isEmpty(sec) || StringUtils.isEmpty(signalName)) {
                    throw new IllegalArgumentException("-s -n must not be empty");
                }

                Security security = FarmerDataManager.getInstance().getSecurity(sec);
                if (sec == null) {
                    ioSession_.write("unknown symbol:" + sec);
                    return;
                }

                List<Security> secs = Lists.newArrayList(security);
                Class<?> c = Class.forName(signalName);
                ISignal signal = FarmerController.getInstance().getSignal(ObjectWrapperEvent.of(security), c);
                SyncTaskEvent<Table<Security, String, String>> ste = new SyncTaskEvent<>(new FutureTask<>(() ->
                        signal.getDiagInfo(secs)
                ), security);
                FarmerController.getInstance().enqueueEvent(ste);
                Table<Security, String, String> vtable = ste.get(TIME_OUT_IN_MILLISECONDS);

                List<Object> header = new ArrayList<>(1);
                List<Object> value = new ArrayList<>();
                FormattedTable table = new FormattedTable();
                header.add("security");
                value.add(security.toString());
                header.add("diagnosis_info");
                value.add("-------");

                Map<String, String> m = vtable.row(security);
                List<String> lm = Lists.newArrayList(m.keySet());
                Collections.sort(lm);
                for (String k : lm) {
                    header.add(k);
                    value.add(m.get(k));
                }

                table.addRow(header);
                table.addRow(value);

                ioSession_.write(table.toString());
            } catch (Exception e_) {
                LOGGER.error(e_.getMessage(), e_);
                ioSession_.write(e_.getMessage());
            }
        }
    }

    @Override
    public void initOptions() {
        options.addOption("s", "secId", true, "securityId: like 000300.sh");
        //options.addOption("t", "secType", true, "type:IND/IND_FUT/STOCK");
        options.addOption("n", "signalName", true, "signalName: GlobalIndexPriceMomentumSignal");
    }
}
