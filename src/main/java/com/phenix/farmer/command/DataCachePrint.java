package com.phenix.farmer.command;

import com.phenix.admin.AbstractAdminCommand;
import com.phenix.data.Security;
import com.phenix.exception.AdminCommandException;
import com.phenix.farmer.FarmerDataManager;
import com.phenix.util.FormattedTable;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.mina.core.session.IoSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataCachePrint extends AbstractAdminCommand {
    public DataCachePrint() {
        super();
        level1Command = "data";
        level2Command = "cache";
        level3Command = "print";
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
            if (StringUtils.isEmpty(cacheName)) {
                throw new AdminCommandException("Illegal parameter -n " + cacheName);
            }

            if (cacheName.equals("IndexFutureMapping")) {
                Map<Security, Security> sec2secMapping = FarmerDataManager.getInstance().getIndex2FutureMapping();
                List<Object> header = new ArrayList<>(2);
                FormattedTable table = new FormattedTable();
                header.add("index");
                header.add("index_future");
                table.addRow(header);

                List<Object> row;
                for (Map.Entry<Security, Security> e : sec2secMapping.entrySet()) {
                    row = new ArrayList<>(2);
                    row.add(e.getKey());
                    row.add(e.getValue());
                    table.addRow(row);
                }
                ioSession_.write(table.toString());
            } else if (cacheName.equalsIgnoreCase("ForbiddenList")) {
                FormattedTable table = new FormattedTable();
                List<Object> header = new ArrayList<>(1);
                header.add("Forbidden Security");
                table.addRow(header);

                List<Security> forbiddenList = FarmerDataManager.getInstance().getForbiddenList();
                List<Object> row = new ArrayList<>();
                for (Security s : forbiddenList) {
                    row.add(s.toString());
                }
                table.addRow(row);
                ioSession_.write(table.toString());
            } else {
                ioSession_.write("Unknown cache name");
            }
        }
    }

    @Override
    public void initOptions() {
        options.addOption("n", "name", true, "cache name");
    }
}
