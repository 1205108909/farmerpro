package com.phenix.util;

import com.phenix.data.OpenClose;
import com.phenix.data.Order;
import com.phenix.farmer.FarmerDataManager;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class StoredSignalUtil {
    private final static transient org.slf4j.Logger Logger = LoggerFactory.getLogger(StoredSignalUtil.class);
    private static String INS_UPD_SIGNAL_DETAILS = "[spu_InsUpdSignalDetails] ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?";
    public static void storedAllSignals(List<Order> orders_){
        Logger.info("Going to stored signals");
        DataSource ds = FarmerDataManager.getInstance().getDataSource(Constants.DB_DATASERVICE);
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = ds.getConnection();
            for (Order o : orders_){
                insertSignalDetail(conn, o);
            }
        } catch (Exception e) {
            Logger.error("storedAllSignals error ",e.getMessage(), e);
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
                Logger.error("close connection error ",e.getMessage(), e);
            }
        }
        Logger.info("Stored All Signals");
    }

    private static void insertSignalDetail(Connection conn_, Order o_){
        Logger.debug("Going to store order" + o_.toString());
        PreparedStatement ps = null;
        try {
            ps = conn_.prepareStatement(INS_UPD_SIGNAL_DETAILS);
            ps.setString(1, o_.getClOrdId());
            ps.setString(2, String.valueOf(o_.getSignalType().getValue()));
            ps.setString(3, o_.getSymbol());
            ps.setInt(4, o_.getOrderSide().getValue());
            ps.setTimestamp(5, Timestamp.valueOf(o_.getInPendingTime()));
            ps.setDouble(6, o_.getRefPx());
            ps.setInt(7, o_.isOpenPosOrder() ? OpenClose.Open.getValue() : OpenClose.Close.getValue());
            ps.setInt(8, o_.getSecType().getValue());
            ps.setDate(9, java.sql.Date.valueOf(o_.getDate()));
            ps.setString(10, o_.getPlacementReason());
            ps.setDate(11, java.sql.Date.valueOf(LocalDate.now()));
            ps.setString(12, "zhaoyu");
            ps.execute();
        } catch (Exception e) {
            Logger.error("insert order error " + o_.toString());
            Logger.error(e.getMessage(), e);
        } finally {
            try {
                ps.close();
            } catch (Exception e) {
                Logger.error("close preparedStatement error ",e.getMessage(), e);
            }
        }
    }
}
