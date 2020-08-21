package com.phenix.exception;

public class AdminCommandException extends RuntimeException {
	private static final long serialVersionUID = 4991296473530540220L;

	public AdminCommandException() {
		super();
	}

	public AdminCommandException(String msg_) {
		super(msg_);
	}

	public AdminCommandException(String msg_, Throwable cause_) {
		super(msg_, cause_);
	}

	public AdminCommandException(Throwable cause_) {
		super(cause_);
	}
}
