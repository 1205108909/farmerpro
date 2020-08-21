package com.phenix.tdf;

import java.util.HashMap;
import java.util.Map;

import cn.com.wind.td.tdf.TDF_ERR;

public enum TdfErrorEnum {	
	UNKNOWN(TDF_ERR.TDF_ERR_UNKOWN, "Unknow error."),
    INITIALIZE_FAILURE(TDF_ERR.TDF_ERR_INITIALIZE_FAILURE, "Failed to initialize socket."),
    INVALID_PARAMS(TDF_ERR.TDF_ERR_INVALID_PARAMS, "INVALID parameters."),
    VERIFY_FAILURE(TDF_ERR.TDF_ERR_VERIFY_FAILURE, "Wrong user name or password, or exceed the limit of maximum connection for the user."),
    NO_AUTHORIZED_MARKET(TDF_ERR.TDF_ERR_NO_AUTHORIZED_MARKET, "Not authorized to get data for the specified markets."),
    NO_CODE_TABLE(TDF_ERR.TDF_ERR_NO_CODE_TABLE, "No code table is available today."),
    SUCCESS(TDF_ERR.TDF_ERR_SUCCESS, "Success.");

    // Tdf client's error code.
    private int errorCode;
    // Tdf client's error message.
    private String errorMessage;

    private static final Map<Integer, TdfErrorEnum> TDF_ERRORS = new HashMap<Integer, TdfErrorEnum>();

    static {
        // Initial mapping between error code and error enum.
        for (TdfErrorEnum errorEnum : TdfErrorEnum.values()) {
            TDF_ERRORS.put(errorEnum.errorCode, errorEnum);
        }
    }

    /**
     * Create a TdfErrorEnum with given error code and error message.
     * @param errorCode the given error code.
     * @param errorMessage the given error message.
     */
    TdfErrorEnum(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    /**
     * Get the error code.
     * @return the error code.
     */
    public int getErrorCode() {
        return this.errorCode;
    }

    /**
     * Get the error message.
     * @return the error message.
     */
    public String getErrorMessage() {
        return this.errorMessage;
    }

    /**
     * Get error from the given error code. 
     * @param errorCode the given error code.
     * @return the error enum.
     */
    public static TdfErrorEnum getErrorByErrorCode(int errorCode) {
        TdfErrorEnum error = TDF_ERRORS.get(errorCode);
        if (error == null) {
            error = UNKNOWN;
        }
        return error;
    }

}
