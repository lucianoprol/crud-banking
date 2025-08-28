package com.rht.santander.exception;

public class TechnicalException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final String orignalMessage;

	public TechnicalException(String msg) {
		super (msg);
		this.orignalMessage = msg;
	}

	public TechnicalException(String msg, Throwable ex) {
		super (msg, ex);
		this.orignalMessage = msg;
	}

	public String getoriginalMessage() {
		return this.orignalMessage;
	}
}
