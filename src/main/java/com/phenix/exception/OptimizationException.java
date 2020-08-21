package com.phenix.exception;

/**
 *
 */
public class OptimizationException extends RuntimeException{
    private static final long serialVersionUID = 508204611633219329L;

    public OptimizationException(String msg_) {
        super(msg_);
    }

    public OptimizationException(Throwable cause_) {
        super(cause_);
    }
}
