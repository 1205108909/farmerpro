package com.phenix;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.phenix.data.Security;
import com.phenix.data.SecurityType;
import com.phenix.util.DateUtil;
import com.phenix.orderbook.OrderBook;
import com.phenix.orderbook.Transaction;
import com.phenix.provider.CSVIndexOrderBookProvider;
import com.phenix.provider.HDF5OrderBookProvider;
import com.phenix.provider.HDF5TransactionProvider;
import hdf.hdf5lib.H5;
import hdf.hdf5lib.HDF5Constants;
import hdf.hdf5lib.exceptions.HDF5Exception;
import hdf.hdf5lib.exceptions.HDF5LibraryException;

import java.time.LocalDate;
import java.util.List;

public class Tester {
    public static void main(String []args) {
        Tester t = new Tester();
        t.test("");
    }


    public void testTransactionHD5(String Path_) {
        Security sec = Security.of("000027.sz", SecurityType.STOCK);
        LocalDate date = DateUtil.getDate("20180920");
        HDF5TransactionProvider obp = new HDF5TransactionProvider("D:\\work\\matlab_scripts\\hdftest_tran", Lists.newArrayList(sec),
                Lists.newArrayList(date));
        Stopwatch watcher = Stopwatch.createStarted();
        List<Transaction> obs = obp.getData(sec, date);
        obs = obp.getData(Security.of("000030.sz", SecurityType.STOCK), date);
        obs = obp.getData(Security.of("000066.sz", SecurityType.STOCK), date);
        obs = obp.getData(Security.of("000001.sz", SecurityType.STOCK), date);
        obs = obp.getData(Security.of("000063.sz", SecurityType.STOCK), date);
        obs = obp.getData(Security.of("000062.sz", SecurityType.STOCK), date);
        obs = obp.getData(Security.of("000037.sz", SecurityType.STOCK), date);

        System.out.println("px = " + obs.get(999).getPrice());
        System.out.println("time = " + obs.get(999).getTime());
        System.out.println("qty = " + obs.get(999).getQty());

        System.out.println(watcher.stop());
    }


    public void testOrderBookHDF5(String path_) {
        Security sec = Security.of("000027.sz", SecurityType.STOCK);
        LocalDate date = DateUtil.getDate("20180920");
        HDF5OrderBookProvider obp = new HDF5OrderBookProvider("\\\\nas_yjs_algo\\algo_share\\Data\\h5data\\stock\\tick", Lists.newArrayList(sec),
            Lists.newArrayList(date));
        Stopwatch watcher = Stopwatch.createStarted();
        List<OrderBook> obs = obp.getData(sec, date);
        obs = obp.getData(Security.of("000030.sz", SecurityType.STOCK), date);
        obs = obp.getData(Security.of("000066.sz", SecurityType.STOCK), date);
        obs = obp.getData(Security.of("000001.sz", SecurityType.STOCK), date);
        obs = obp.getData(Security.of("000063.sz", SecurityType.STOCK), date);
        obs = obp.getData(Security.of("000062.sz", SecurityType.STOCK), date);
        obs = obp.getData(Security.of("000037.sz", SecurityType.STOCK), date);
        obs = obp.getData(Security.of("600000.sh", SecurityType.STOCK), date);
        System.out.println(watcher.stop());

//        System.out.println("op = " + obs.get(999).getOpen());
//        System.out.println("hp = " + obs.get(999).getHigh());
//        System.out.println("lp = " + obs.get(999).getLow());
//        System.out.println("lap = " + obs.get(999).getLastPrice());
//        System.out.println("cp = " + obs.get(999).getClosePx());
//
//
//        System.out.println("turnover = " + obs.get(999).getTurnover());
//        System.out.println("vol = " + obs.get(999).getVolume());
//        System.out.println("time = " + obs.get(999).getTime());
//        System.out.println("ask(3) = " + obs.get(999).getAskPrice(2) + "; askQty(3)=" + obs.get(999).getAskQty(2));
//        System.out.println("bid(3) = " + obs.get(999).getBidPrice(2) + "; askQty(3)=" + obs.get(999).getBidQty(2));
    }

    public void testOrderBookCSV(String Path_) {
        Security sec = Security.of("000300.sh", SecurityType.INDEX);
        LocalDate date = DateUtil.getDate("20180920");
        CSVIndexOrderBookProvider obp = new CSVIndexOrderBookProvider("D:/work/matlab_scripts/csvtest_indexob", Lists.newArrayList(sec),
                Lists.newArrayList(date));
        Stopwatch watcher = Stopwatch.createStarted();
        List<OrderBook> obs = obp.getData(sec, date);
        //obs = obp.getData(Security.of("000905.sh", SecurityType.INDEX), date);
        //obs = obp.getData(Security.of("000001.sh", SecurityType.INDEX), date);


        System.out.println("px = " + obs.get(999).getLastPx());
        System.out.println("time = " + obs.get(999).getTime());
        System.out.println("qty = " + obs.get(999).getVolume());
        System.out.println("turnover = " + obs.get(999).getTurnover());

        System.out.println(watcher.stop());
    }

    public void test(String path_) {
        long fileId = -1;
        long datasetId = -1;
        long dcplId = -1;
        long dsapceId = -1;
        try {
            fileId = H5.H5Fopen(path_, HDF5Constants.H5F_ACC_RDONLY, HDF5Constants.H5P_DEFAULT);
            datasetId = H5.H5Dopen((int)fileId, "/000027.sz/AccVolume", HDF5Constants.H5P_DEFAULT);
            dsapceId = H5.H5Dget_space(datasetId);
            int size = (int)H5.H5Sget_simple_extent_npoints(dsapceId);
            int dim = (int)H5.H5Sget_simple_extent_ndims(dsapceId);

            double []open = new double[size];
            H5.H5Dread(datasetId, HDF5Constants.H5T_NATIVE_DOUBLE, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL,
                    HDF5Constants.H5P_DEFAULT, open);

            System.out.println(fileId);
            System.out.println(datasetId);
            System.out.println(size + ", dim = " + dim);
            System.out.println(open[100]);
        } catch (HDF5LibraryException e) {
            e.printStackTrace();
        } catch (HDF5Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(dsapceId > 0) {
                    H5.H5Sclose(dsapceId);
                }
                if(datasetId > 0) {
                    datasetId = H5.H5Dclose(datasetId);                }
                if(fileId > 0) {
                    H5.H5Fclose(fileId);
                }
            } catch (HDF5LibraryException e) {
                e.printStackTrace();
            }
        }

        System.out.println(datasetId);
    }
}
