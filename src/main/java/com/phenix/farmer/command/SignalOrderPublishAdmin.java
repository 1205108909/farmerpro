package com.phenix.farmer.command;

import com.google.common.collect.Lists;
import com.phenix.admin.AbstractAdminCommand;
import com.phenix.data.Order;
import com.phenix.exception.AdminCommandException;
import com.phenix.farmer.FarmerController;
import com.phenix.farmer.FarmerDataManager;
import com.phenix.farmer.event.SyncTaskEvent;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.mina.core.session.IoSession;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.FutureTask;

public class SignalOrderPublishAdmin extends AbstractAdminCommand {
    public SignalOrderPublishAdmin() {
        super();
        level1Command = "signal";
        level2Command = "order";
        level3Command = "publish";
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

            String orderId = cl.getOptionValue("o");
            if (StringUtils.isEmpty(orderId)) {
                throw new AdminCommandException("Illegal string: " + orderId);
            }

            List<Order> orders;
            if ("all".equalsIgnoreCase(orderId)) {
                orders = FarmerController.getInstance().getOrders();
                Collections.sort(orders, Order.ORDER_COMPARATOR_SIGNALTYPE_AND_PENDINGTIME);
            } else {
                Order o = FarmerController.getInstance().getOrder(orderId);
                if (o == null) {
                    ioSession_.write(orderId + " is not found");
                    return;
                }
                orders = Lists.newArrayList(o);
            }

            for (Order o : orders) {
                SyncTaskEvent<Boolean> ste = new SyncTaskEvent<>(new FutureTask<>(() ->
                        FarmerDataManager.getInstance().publishOrderInfo(o)
                ), o.getSec());
                FarmerController.getInstance().enqueueEvent(ste);
            }
            ioSession_.write(DONE);
        }
    }

    @Override
    public void initOptions() {
        options.addOption("o", "orderId", true, "publish order info");
    }
}
