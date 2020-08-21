package com.phenix.exception;

public class DuplicateCriteriaKeyException extends RuntimeException {	

	/**
	 * 
	 */
	private static final long serialVersionUID = -5296360308679394872L;

	public DuplicateCriteriaKeyException() {
		super();
	}

	public DuplicateCriteriaKeyException(String msg_) {
		super(msg_);
	}

	public DuplicateCriteriaKeyException(String msg_, Throwable cause_) {
		super(msg_, cause_);
	}

	public DuplicateCriteriaKeyException(Throwable cause_) {
		super(cause_);
	}

}
