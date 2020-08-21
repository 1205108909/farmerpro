package com.phenix.exception;

public class CacheOperationException extends RuntimeException {	

	/**
	 * 
	 */
	private static final long serialVersionUID = 5823187550103803454L;

	public CacheOperationException() {
		super();
	}
	
	public CacheOperationException(String msg_) { 
		super(msg_);
	}
	
	public CacheOperationException(String msg_, Throwable cause_) {
		super(msg_, cause_);
	}
	
	public CacheOperationException(Throwable cause_) {
		super(cause_);
	}
}
