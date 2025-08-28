package com.rht.santander.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class NotValidException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public static final String MSG = "RESOURCE NOT FOUND";
	private final String errorCode;

	public NotValidException(final String msg, final String errorCode) { 
		this(msg, errorCode, null);
	}

	public NotValidException(final String msg, final String errorCode, Throwable ex) {
		super(msg, ex);
		this.errorCode = errorCode;
	}

	public NotValidException() {
		this(MSG, HttpStatus.NOT_FOUND.getReasonPhrase().toUpperCase());
	}

	public NotValidException(final String message) {
		this(message, HttpStatus.NOT_FOUND.getReasonPhrase().toUpperCase());
	}

}
