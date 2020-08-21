package com.phenix.exception;

public class DuplicateDataException extends RuntimeException {	

	/**
	 * 
	 */
	private static final long serialVersionUID = -561814316746414941L;

	public DuplicateDataException() {
		super();
	}

	public DuplicateDataException(String msg_) {
		super(msg_);
	}

	public DuplicateDataException(String msg_, Throwable cause_) {
		super(msg_, cause_);
	}

	public DuplicateDataException(Throwable cause_) {
		super(cause_);
	}
}

