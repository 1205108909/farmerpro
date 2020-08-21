package com.phenix.provider;

import com.phenix.data.OrderSide;
import com.phenix.data.Security;
import com.phenix.exception.IllegalDataException;
import com.phenix.util.DateUtil;
import com.phenix.orderbook.Transaction;
import hdf.hdf5lib.H5;
import hdf.hdf5lib.HDF5Constants;
import hdf.hdf5lib.exceptions.HDF5Exception;
import hdf.hdf5lib.exceptions.HDF5LibraryException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class HDF5TransactionProvider extends AbstractIntraDayDataLoader<Transaction> {
    public HDF5TransactionProvider(String dataURL_, List<Security> secs_, List<LocalDate> dates_) {
        super(dataURL_, secs_, dates_);
    }

    @Override
    public List<Transaction> loadData(Security sec_, LocalDate date_) throws IllegalDataException {
        long fileId = -1;
        long datasetId = -1;
        long dsapceId = -1;

        //double
        double[] price, volume;
        int[] time, askorder, bidorder;
        String []bsFlag, functionCode;

        List<Transaction> trans = new ArrayList<>();

        String path = dataURL + "/" + DateUtil.date2Str(date_) + ".h5";
        try {
            fileId = H5.H5Fopen(path, HDF5Constants.H5F_ACC_RDONLY, HDF5Constants.H5P_DEFAULT);
            if (!H5.H5Lexists(fileId, sec_.getSymbol(), HDF5Constants.H5P_DEFAULT)){
                return trans;
            }

            //price
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

            //volume
            datasetId = H5.H5Dopen(fileId, "/" + sec_.getSymbol() + "/Volume", HDF5Constants.H5P_DEFAULT);
            volume = new double[size];
            H5.H5Dread(datasetId, HDF5Constants.H5T_NATIVE_DOUBLE, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL,
                    HDF5Constants.H5P_DEFAULT, volume);
            if (datasetId > 0) {
                H5.H5Dclose(datasetId);
            }

            //time
            datasetId = H5.H5Dopen(fileId, "/" + sec_.getSymbol() + "/Time", HDF5Constants.H5P_DEFAULT);
            time = new int[size];
            H5.H5Dread(datasetId, HDF5Constants.H5T_NATIVE_UINT32, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL,
                    HDF5Constants.H5P_DEFAULT, time);
            if (datasetId > 0) {
                datasetId = H5.H5Dclose(datasetId);
            }

            //askorder
            datasetId = H5.H5Dopen(fileId, "/" + sec_.getSymbol() + "/AskOrder", HDF5Constants.H5P_DEFAULT);
            askorder = new int[size];
            H5.H5Dread(datasetId, HDF5Constants.H5T_NATIVE_UINT32, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL,
                    HDF5Constants.H5P_DEFAULT, askorder);
            if (datasetId > 0) {
                datasetId = H5.H5Dclose(datasetId);
            }

            //bidorder
            datasetId = H5.H5Dopen(fileId, "/" + sec_.getSymbol() + "/BidOrder", HDF5Constants.H5P_DEFAULT);
            bidorder = new int[size];
            H5.H5Dread(datasetId, HDF5Constants.H5T_NATIVE_UINT32, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL,
                    HDF5Constants.H5P_DEFAULT, bidorder);
            if (datasetId > 0) {
                datasetId = H5.H5Dclose(datasetId);
            }

            //bsFlag
            datasetId = H5.H5Dopen(fileId, "/" + sec_.getSymbol() + "/BSFlag", HDF5Constants.H5P_DEFAULT);
            bsFlag = new String[size];
            H5.H5Dread(datasetId, HDF5Constants.H5T_FORTRAN_S1, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL,
                    HDF5Constants.H5P_DEFAULT, bsFlag);
            if (datasetId > 0) {
                datasetId = H5.H5Dclose(datasetId);
            }

            //functionCode
            datasetId = H5.H5Dopen(fileId, "/" + sec_.getSymbol() + "/FunctionCode", HDF5Constants.H5P_DEFAULT);
            functionCode = new String[size];
            H5.H5Dread(datasetId, HDF5Constants.H5T_FORTRAN_S1, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL,
                    HDF5Constants.H5P_DEFAULT, functionCode);
            if (datasetId > 0) {
                datasetId = H5.H5Dclose(datasetId);
            }

            trans = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                String timeStr = String.valueOf(time[i]);
                LocalTime lt = DateUtil.getTime2(timeStr.length() == 9 ? timeStr : "0" + timeStr);
                Transaction t = Transaction.of(price[i], volume[i], LocalDateTime.of(date_, lt), sec_, bidorder[i], askorder[i], OrderSide.parse(bsFlag[i]), functionCode[i]);
                trans.add(t);
            }

        } catch (HDF5Exception e_) {
            if(trans != null) trans.clear();
            throw new IllegalDataException(e_);
        } finally {
            if(fileId > 0) {
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
        }

        return trans;
    }
}
