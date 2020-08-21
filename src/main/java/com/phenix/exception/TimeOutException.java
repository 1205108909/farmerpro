package com.phenix.exception;

public class TimeOutException extends RuntimeException {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 4066110842092565423L;

	public TimeOutException() {
		super();
	}
	
	public TimeOutException(String msg_) { 
		super(msg_);
	}
	
	public TimeOutException(String msg_, Throwable cause_) {
		super(msg_, cause_);
	}
	
	public TimeOutException(Throwable cause_) {
		super(cause_);
	}
}
