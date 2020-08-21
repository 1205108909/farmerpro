package com.phenix.exception;

public class IllegalDataException extends RuntimeException {


	/**
	 * 
	 */
	private static final long serialVersionUID = -1270594012492235677L;

	public IllegalDataException() {
		super();
	}
	
	public IllegalDataException(String msg_) { 
		super(msg_);
	}
	
	public IllegalDataException(String msg_, Throwable cause_) {
		super(msg_, cause_);
	}
	
	public IllegalDataException(Throwable cause_) {
		super(cause_);
	}
}
