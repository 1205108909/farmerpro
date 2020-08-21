package com.phenix.exception;

public class SyncTaskExecutionException extends RuntimeException {

	private static final long serialVersionUID = -3942959511920986087L;

	public SyncTaskExecutionException() {
		super();
	}

	public SyncTaskExecutionException(String msg_) {
		super(msg_);
	}

	public SyncTaskExecutionException(String msg_, Throwable cause_) {
		super(msg_, cause_);
	}

	public SyncTaskExecutionException(Throwable cause_) {
		super(cause_);
	}
}
