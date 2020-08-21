package com.phenix.exception;

public class UnknownValueException extends RuntimeException {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1427870194874489318L;

	public UnknownValueException() {
		super();
	}
	
	public UnknownValueException(String msg_) { 
		super(msg_);
	}
	
	public UnknownValueException(String msg_, Throwable cause_) {
		super(msg_, cause_);
	}
	
	public UnknownValueException(Throwable cause_) {
		super(cause_);
	}
}
