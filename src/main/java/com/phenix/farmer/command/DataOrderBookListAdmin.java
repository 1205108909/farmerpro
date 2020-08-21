package com.phenix.farmer.command;

import com.phenix.admin.AbstractAdminCommand;
import com.phenix.data.SecurityType;
import com.phenix.farmer.FarmerController;
import com.phenix.orderbook.OrderBook;
import com.phenix.util.Constants;
import com.phenix.util.FormattedTable;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.mina.core.session.IoSession;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataOrderBookListAdmin extends AbstractAdminCommand {
    public DataOrderBookListAdmin() {
        super();
        level1Command = "data";
        level2Command = "orderbook";
        level3Command = "list";
        initOptions();
    }

    @Override
    public void handle(IoSession ioSession_, String[] paras_) {
        if (!ArrayUtils.isEmpty(paras_)) {
            ioSession_.write(NO_PARAMETER_FOUND);
        } else {
            List<Object> header = new ArrayList<>(1);
            FormattedTable table = new FormattedTable();
            header.add("symbol");
            header.add("name");
            header.add("preCls");
            header.add("preSettle");
            header.add("time");
            header.add("lastPx");
            header.add("open");
            header.add("high");
            header.add("low");
            header.add("turnover");
            for (int i = 0; i < 5; i++) {
                header.add("bp" + (i + 1));
                header.add("bs" + (i + 1));
                header.add("ap" + (i + 1));
                header.add("as" + (i + 1));
            }
            table.addRow(header);

            List<OrderBook> obs = FarmerController.getInstance().getOrderBooks();
            Collections.sort(obs, OrderBook.ORDER_BOOK_COMPARATOR_SYMBOL_FIRST);
            List<Object> row;
            for (OrderBook o : obs) {
                row = new ArrayList<>(obs.size());
                row.add(o.getSymbol());
                row.add(o.getSecurity().getName());
                row.add(o.getPreClose());
                row.add(SecurityType.INDEX_FUTURE == o.getSecurityType() ? o.getPreSettle() : Constants.NA);
                row.add(o.getTime());
                row.add(o.getLastPx());
                row.add(o.getOpenPx());
                row.add(o.getHighPx());
                row.add(o.getLowPx());
                row.add(o.getTurnover());
                int qs = o.getQuoteSize();
                for (int i = 0; i < 5; i++) {
                    if (i <= qs - 1) {
                        row.add(o.getBidPrice(i));
                        row.add(o.getBidQty(i));
                        row.add(o.getAskPrice(i));
                        row.add(o.getAskQty(i));
                    } else {
                        row.add(Constants.NA);
                        row.add(Constants.NA);
                        row.add(Constants.NA);
                        row.add(Constants.NA);
                    }
                }
                table.addRow(row);
            }
            ioSession_.write(table.toString());
        }
    }

    @Override
    public void initOptions() {
    }
}
