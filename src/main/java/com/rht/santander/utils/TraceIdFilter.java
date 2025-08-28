package com.rht.santander.utils;

import java.io.IOException;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * Filtro custom que uso para agregar el traceId
 * 
 * @author Luciano Prol
 */
@Component
@Slf4j
public class TraceIdFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
		try {
			// Genero el Trace ID
			String traceId = TraceIdGenerator.generateTraceId();
			MDC.put("traceId", traceId);
			
			log.info(new Object(){}.getClass().getEnclosingMethod().getName() + DataUtils.LOG_ENTRY_START);
			
			chain.doFilter(request, response);
		} finally {
			// Libero MDC por memory leaks
			MDC.clear();
		}
	}
	
	@Override
	public void init(FilterConfig filterConfig) {}
	
	@Override
	public void destroy() {}
}
