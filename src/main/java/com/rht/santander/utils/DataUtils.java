package com.rht.santander.utils;

import org.slf4j.MDC;

import com.rht.santander.model.BankingEntityResponse;

public class DataUtils {

	public static final String LOG_ENTRY_CONTROLLER = "Request: ";
	public static final String LOG_EXIT_CONTROLLER = "Response: ";
	public static final String LOG_ENTRY_SERVICE = " Start";
	public static final String LOG_EXIT_SERVICE_OK = " Exit: COMPLETED ";
	public static final String LOG_EXIT_SERVICE_ERROR = " Exit: UNSUCCESS ";
	public static final String LOG_ENTRY_START = " Start Execution";
	
	public static final String RESULT_OK = "OK";
	public static final String RESULT_OK_CODE = "0000";
	public static final String RESULT_OK_MSJ = "COMPLETED SUCCESFULLY";
	public static final String RESULT_ERROR = "ERROR";
	public static final String RESULT_ERROR_CODE = "0008";
	
	public static final String EXCEPTION_VALIDATION_MSG = "Exception validating request : ";
	public static final String EXCEPTION_VALIDATION_ENTITY = "Entity with code %s not found";
	public static final String EXCEPTION_VALIDATION_EXIST_ENTITY = "Entity with code %s alredy exists";
	
	public static final String EXCEPTION_STORAGE_MSG = "Exception saving entity : ";
	public static final String EXCEPTION_STORAGE_NULL_MSG = "entity is null";
	public static final String EXCEPTION_REMOVE_MSG = "Exception removing entity : ";
	public static final String EXCEPTION_REMOVE_NULL_MSG = "entity not found";
	
	public static final String NOT_NULL_MSG = "A field is null";
	public static final String NOT_EMPTY_MSG = "A field is null";
	
	public static final String EXECUTION_TIME = "Execution time: ";
	public static final String EXECUTION_TIME_MS = " ms.-";
	
	public static String getTraceId() {
		return MDC.get("traceId");
	}
	
	/**
	 * Metodo que crea una respuesta OK generica 
	 * 
	 * @param traceId
	 * @return EntityResponse
	 * 
	 * @author Luciano Prol
	 */
	public static BankingEntityResponse createResponseOk() {
		BankingEntityResponse response = new BankingEntityResponse();
		response.setTraceId(getTraceId());
		response.setResult(RESULT_OK);
		response.setCod(RESULT_OK_CODE);
		response.setMsj(RESULT_OK_MSJ);
		return response;
	}
	
	/**
	 * Metodo que crea una respuesta ERROR generica 
	 * con el mensaje enviado por parametro
	 * 
	 * @param traceId
	 * @param errorMsj
	 * @return EntityResponse
	 * 
	 * @author Luciano Prol
	 */
	public static BankingEntityResponse createResponseError(String errorMsj) {
		BankingEntityResponse response = new BankingEntityResponse();
		response.setTraceId(getTraceId());
		response.setResult(RESULT_ERROR);
		response.setCod(RESULT_ERROR_CODE);
		response.setMsj(errorMsj);
		return response;
	}
}
