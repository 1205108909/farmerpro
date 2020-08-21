package com.phenix.provider;

import com.phenix.data.Security;
import com.phenix.exception.IllegalDataException;
import com.phenix.util.DateUtil;
import com.phenix.orderbook.OrderBook;
import com.phenix.orderbook.Quote;
import hdf.hdf5lib.H5;
import hdf.hdf5lib.HDF5Constants;
import hdf.hdf5lib.exceptions.HDF5Exception;
import hdf.hdf5lib.exceptions.HDF5LibraryException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class HDF5OrderBookProvider extends AbstractIntraDayDataLoader<OrderBook> {
    public HDF5OrderBookProvider(String dataURL_, List<Security> secs_, List<LocalDate> dates_) {
        super(dataURL_, secs_, dates_);
    }

    @Override
    public List<OrderBook> loadData(Security sec_, LocalDate date_) throws IllegalDataException {
        long fileId = -1;
        long datasetId = -1;
        long dsapceId = -1;
        double[] price, preClose, openPrice, highPrice, lowPrice, askPrice10, bidPrice10, askAvgPrice, bidAvgPrice;
        int[] time, matchItem, askVol10, bidVol10;
        long[] volume, turnover, totalAskVolume, totalBidVolume;
        char[] bsFlag;
        List<OrderBook> obs = new ArrayList<OrderBook>();

        try {
            String path = dataURL + "/" + DateUtil.date2Str(date_) + ".h5";
            fileId = H5.H5Fopen(path, HDF5Constants.H5F_ACC_RDONLY, HDF5Constants.H5P_DEFAULT);

            if (!H5.H5Lexists(fileId, sec_.getSymbol(), HDF5Constants.H5P_DEFAULT)){
                return obs;
            }
            //lastPrice
            datasetId = H5.H5Dopen(fileId, "/" + sec_.getSymbol() + "/Price", HDF5Constants.H5P_DEFAULT);
            dsapceId = H5.H5Dget_space(datasetId);
            int size = (int) H5.H5Sget_simple_extent_npoints(dsapceId);
            price = new double[size];
            H5.H5Dread(datasetId, HDF5Constants.H5T_NATIVE_DOUBLE, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL,
                    HDF5Constants.H5P_DEFAULT, price);
            if (dsapceId > 0) {
                datasetId = H5.H5Sclose(dsapceId);
            }
            if (datasetId > 0) {
                H5.H5Dclose(datasetId);
            }

            //turnover
            datasetId = H5.H5Dopen(fileId, "/" + sec_.getSymbol() + "/Turnover", HDF5Constants.H5P_DEFAULT);
            turnover = new long[size];
            H5.H5Dread(datasetId, HDF5Constants.H5T_NATIVE_INT64, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL,
                    HDF5Constants.H5P_DEFAULT, turnover);
            if (datasetId > 0) {
                datasetId = H5.H5Dclose(datasetId);
            }

            //volume
            datasetId = H5.H5Dopen(fileId, "/" + sec_.getSymbol() + "/Volume", HDF5Constants.H5P_DEFAULT);
            volume = new long[size];
            H5.H5Dread(datasetId, HDF5Constants.H5T_NATIVE_INT64, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL,
                    HDF5Constants.H5P_DEFAULT, volume);
            if (datasetId > 0) {
                datasetId = H5.H5Dclose(datasetId);
            }

            //open
            datasetId = H5.H5Dopen(fileId, "/" + sec_.getSymbol() + "/Open", HDF5Constants.H5P_DEFAULT);
            openPrice = new double[size];
            H5.H5Dread(datasetId, HDF5Constants.H5T_NATIVE_DOUBLE, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL,
                    HDF5Constants.H5P_DEFAULT, openPrice);
            if (datasetId > 0) {
                datasetId = H5.H5Dclose(datasetId);
            }

            //high
            datasetId = H5.H5Dopen(fileId, "/" + sec_.getSymbol() + "/High", HDF5Constants.H5P_DEFAULT);
            highPrice = new double[size];
            H5.H5Dread(datasetId, HDF5Constants.H5T_NATIVE_DOUBLE, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL,
                    HDF5Constants.H5P_DEFAULT, highPrice);
            if (datasetId > 0) {
                datasetId = H5.H5Dclose(datasetId);
            }

            //Low
            datasetId = H5.H5Dopen(fileId, "/" + sec_.getSymbol() + "/Low", HDF5Constants.H5P_DEFAULT);
            lowPrice = new double[size];
            H5.H5Dread(datasetId, HDF5Constants.H5T_NATIVE_DOUBLE, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL,
                    HDF5Constants.H5P_DEFAULT, lowPrice);
            if (datasetId > 0) {
                datasetId = H5.H5Dclose(datasetId);
            }

            //preClose
            datasetId = H5.H5Dopen(fileId, "/" + sec_.getSymbol() + "/PreClose", HDF5Constants.H5P_DEFAULT);
            preClose = new double[size];
            H5.H5Dread(datasetId, HDF5Constants.H5T_NATIVE_DOUBLE, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL,
                    HDF5Constants.H5P_DEFAULT, preClose);
            if (datasetId > 0) {
                datasetId = H5.H5Dclose(datasetId);
            }

            //time
            datasetId = H5.H5Dopen(fileId, "/" + sec_.getSymbol() + "/Time", HDF5Constants.H5P_DEFAULT);
            time = new int[size];
            H5.H5Dread(datasetId, HDF5Constants.H5T_NATIVE_UINT32, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL,
                    HDF5Constants.H5P_DEFAULT, time);
            if (datasetId > 0) {
                datasetId = H5.H5Dclose(datasetId);
            }


            //totalAskVolume
            datasetId = H5.H5Dopen(fileId, "/" + sec_.getSymbol() + "/TotalAskVolume", HDF5Constants.H5P_DEFAULT);
            totalAskVolume = new long[size];
            H5.H5Dread(datasetId, HDF5Constants.H5T_NATIVE_INT64, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL,
                    HDF5Constants.H5P_DEFAULT, totalAskVolume);
            if (datasetId > 0) {
                datasetId = H5.H5Dclose(datasetId);
            }

            //totalBidVolume
            datasetId = H5.H5Dopen(fileId, "/" + sec_.getSymbol() + "/TotalBidVolume", HDF5Constants.H5P_DEFAULT);
            totalBidVolume = new long[size];
            H5.H5Dread(datasetId, HDF5Constants.H5T_NATIVE_INT64, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL,
                    HDF5Constants.H5P_DEFAULT, totalBidVolume);
            if (datasetId > 0) {
                datasetId = H5.H5Dclose(datasetId);
            }

            //askPx10
            datasetId = H5.H5Dopen(fileId, "/" + sec_.getSymbol() + "/AskPrice10", HDF5Constants.H5P_DEFAULT);
            askPrice10 = new double[size * 10];
            H5.H5Dread(datasetId, HDF5Constants.H5T_NATIVE_DOUBLE, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL,
                    HDF5Constants.H5P_DEFAULT, askPrice10);
            if (datasetId > 0) {
                datasetId = H5.H5Dclose(datasetId);
            }

            //askVol10
            datasetId = H5.H5Dopen(fileId, "/" + sec_.getSymbol() + "/AskVolume10", HDF5Constants.H5P_DEFAULT);
            askVol10 = new int[size * 10];
            H5.H5Dread(datasetId, HDF5Constants.H5T_NATIVE_UINT32, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL,
                    HDF5Constants.H5P_DEFAULT, askVol10);
            if (datasetId > 0) {
                //datasetId = H5.H5Dclose(datasetId);
            }

            //bidPx10
            datasetId = H5.H5Dopen(fileId, "/" + sec_.getSymbol() + "/BidPrice10", HDF5Constants.H5P_DEFAULT);
            bidPrice10 = new double[size * 10];
            H5.H5Dread(datasetId, HDF5Constants.H5T_NATIVE_DOUBLE, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL,
                    HDF5Constants.H5P_DEFAULT, bidPrice10);
            if (datasetId > 0) {
                datasetId = H5.H5Dclose(datasetId);
            }

            //bidVol10
            datasetId = H5.H5Dopen(fileId, "/" + sec_.getSymbol() + "/BidVolume10", HDF5Constants.H5P_DEFAULT);
            bidVol10 = new int[size * 10];
            H5.H5Dread(datasetId, HDF5Constants.H5T_NATIVE_UINT32, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL,
                    HDF5Constants.H5P_DEFAULT, bidVol10);
            if (datasetId > 0) {
                datasetId = H5.H5Dclose(datasetId);
            }

            // only care about the nearest 5-level
            obs = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                List<Quote> ask = new ArrayList<>(10);
                List<Quote> bid = new ArrayList<>(10);
                for (int j = 0; j < 10; j++) {
                    ask.add(new Quote(askPrice10[i * 10 + j], askVol10[i * 10 + j]));
                    bid.add(new Quote(bidPrice10[i * 10 + j], bidVol10[i * 10 + j]));
                }

                String timeStr = String.valueOf(time[i]);
                LocalTime lt;
                if (8 == timeStr.length()){
                    lt = DateUtil.getTime2("0" + timeStr);
                }else if (9 == timeStr.length()){
                    lt = DateUtil.getTime2(timeStr);
                } else {
                    continue;
                }
                OrderBook ob = OrderBook.of(ask, bid, price[i], sec_, turnover[i], volume[i], preClose[i], openPrice[i],
                        price[i], highPrice[i], lowPrice[i], LocalDateTime.of(date_, lt), totalAskVolume[i], totalBidVolume[i]);
                obs.add(ob);
            }
        }
        catch (HDF5Exception e_) {
            if(obs != null) obs.clear();
            System.out.println(sec_.getSymbol());
            throw new IllegalDataException(e_);
        }catch (DateTimeParseException e){
            if (obs != null) obs.clear();
            System.out.println(sec_.getSymbol());
        }
        finally {
            try {
                if (datasetId > 0) {
                    H5.H5Dclose(datasetId);
                }
                if (fileId > 0) {
                    H5.H5Fclose(fileId);
                }
            } catch (HDF5LibraryException e_) {
                throw new IllegalDataException(e_);
            }
        }

        return obs;
    }
}
