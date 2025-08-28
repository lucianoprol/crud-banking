package com.rht.santander.exception;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	private static final String ERROR_MSG_NO_ELEMENT = "DATA NOT FOUND";
	private static final String ERROR_MSG_GENERIC = "TECHNICAL ERROR. CONTACT TO ADMINISTRATOR";
	private static final String ERROR_MSG_BIND_VALIDATION = "INVALID PARAMETERS";
	private static final String ERROR_FIELD_FORMAT = "%s: %s";
	private static final String ERROR_MSG_TO_LOGGING = "EXECUTION ERROR";

	private String getTraceId() {
		try {
			return MDC.get("traceId");
		} finally {
			MDC.clear();
		}
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
		try {
			CustomErrorResponse customErrorResponse = null;
			List<String> errors = ex.getBindingResult().getFieldErrors().stream()
					.map(x -> String.format(ERROR_FIELD_FORMAT, x.getField(), x.getDefaultMessage()))
					.toList();

			customErrorResponse = new CustomErrorResponse(getTraceId(), HttpStatus.BAD_REQUEST.getReasonPhrase().toUpperCase(),
					ERROR_MSG_BIND_VALIDATION, errors);
			customErrorResponse.setTimestamp(LocalDateTime.now());
			customErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
			customErrorResponse.setErrors(errors);

			log.error(ERROR_MSG_TO_LOGGING, ex);
			return new ResponseEntity<>(customErrorResponse, HttpStatus.BAD_REQUEST);
		} finally {
			MDC.clear();
		}
	}
	
	@ExceptionHandler(value = NotValidException.class)
	public ResponseEntity<Object> handleNotValidException(NotValidException ex) {
		try {
			List<String> errors = new ArrayList<>();
			errors.add(ex.getMessage());
			
			CustomErrorResponse customErrorResponse = null;
			customErrorResponse = new CustomErrorResponse(getTraceId(), HttpStatus.BAD_REQUEST.getReasonPhrase().toUpperCase(),
					ERROR_MSG_BIND_VALIDATION, errors);
			customErrorResponse.setTimestamp(LocalDateTime.now());
			customErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
	
			
			log.info(new Object(){}.getClass().getEnclosingMethod().getName()+ex.getMessage());
			return new ResponseEntity<>(customErrorResponse, HttpStatus.BAD_REQUEST);
		} finally {
			MDC.clear();
		}
	}
	
	@ExceptionHandler(value = NoSuchElementException.class)
	public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException ex) {
		try {
			List<String> errors = new ArrayList<>();
			errors.add(ex.getMessage());
			
			CustomErrorResponse customErrorResponse = null;
			customErrorResponse = new CustomErrorResponse(getTraceId(), HttpStatus.NOT_FOUND.getReasonPhrase().toUpperCase(),
					ERROR_MSG_NO_ELEMENT, errors);
			customErrorResponse.setTimestamp(LocalDateTime.now());
			customErrorResponse.setStatus(HttpStatus.NOT_FOUND.value());
			
			log.info(new Object(){}.getClass().getEnclosingMethod().getName()+ex.getMessage());
			return new ResponseEntity<>(customErrorResponse, HttpStatus.NOT_FOUND);
		} finally {
			MDC.clear();
		}
	}
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) {
		CustomErrorResponse customErrorResponse = null;

		customErrorResponse = new CustomErrorResponse(getTraceId(),
				HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase().toUpperCase(), ERROR_MSG_GENERIC,
				new ArrayList<>(), request);
		customErrorResponse.setTimestamp(LocalDateTime.now());
		customErrorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		customErrorResponse.getErrors().add(ex.getMessage());

		log.error(ERROR_MSG_TO_LOGGING, ex);
		return new ResponseEntity<>(customErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
