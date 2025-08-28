package com.rht.santander.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class TraceIdGenerator {

	private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	/**
	 * Generador de Trace ID random
	 * 
	 * @return String de 22 caracteres: 5 letras y luego la fecha
	 */
	public static String generateTraceId() {
		StringBuilder traceId = new StringBuilder();
		Random random = new Random();
		// Primero 5 letras random
		for (int i = 0; i < 5; i++) {
			traceId.append(LETTERS.charAt(random.nextInt(LETTERS.length())));
		}
		// Luego la fecha actual
		Date date = new Date();
		String dateId = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(date);
		traceId.append(dateId);
		
		return traceId.toString();
	}
}
