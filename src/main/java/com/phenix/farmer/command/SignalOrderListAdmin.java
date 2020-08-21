package com.phenix.farmer.command;

import com.phenix.admin.AbstractAdminCommand;
import com.phenix.data.Order;
import com.phenix.exception.AdminCommandException;
import com.phenix.farmer.FarmerController;
import com.phenix.util.FormattedTable;
import com.phenix.util.Util;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.mina.core.session.IoSession;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class SignalOrderListAdmin extends AbstractAdminCommand {
    private final static String CLOSE_POS_ORDER = "close_pos";
    private final static String OPEN_POS_ORDER = "open_pos";
    private final static String OPEN_STATUS_ORDER = "open";

    public SignalOrderListAdmin() {
        super();
        level1Command = "signal";
        level2Command = "order";
        level3Command = "list";
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
            String all = cl.getOptionValue("a");
            String type = cl.getOptionValue("t");
            if (all != null && !"true".equalsIgnoreCase(all) && !"false".equalsIgnoreCase(all)) {
                throw new AdminCommandException("Illegal string: " + all);
            }

            boolean allFlag = all == null ? false : Boolean.parseBoolean(all);
            List<Order> orders = FarmerController.getInstance().getOrders();
            Collections.sort(orders, Order.ORDER_COMPARATOR_SIGNALTYPE_AND_PENDINGTIME);
            if (allFlag) {
            } else {
                Iterator<Order> i = orders.iterator();
                while (i.hasNext()) {
                    Order o = i.next();
                    if (OPEN_POS_ORDER.equals(type) && o.isClosePosOrder()) {
                        i.remove();
                        continue;
                    } else if (CLOSE_POS_ORDER.equals(type) && o.isOpenPosOrder()) {
                        i.remove();
                        continue;
                    } else if (OPEN_STATUS_ORDER.equals(type) && Util.isClosedStatus(o.getOrderStatus())) {
                        i.remove();
                        continue;
                    }
                }
            }

            FormattedTable tb = new FormattedTable();
            tb.addRows(orders);
            ioSession_.write(tb.toString());
        }
    }

    @Override
    public void initOptions() {
        options.addOption("a", "all", true, "list all order including open/closed");
        options.addOption("t", "type", true, "o means open position order, c means close position order");
    }
}
