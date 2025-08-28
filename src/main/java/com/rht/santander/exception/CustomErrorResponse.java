package com.rht.santander.exception;

import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class CustomErrorResponse {

	private String code;
	private String causes;
	private Integer status;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	private LocalDateTime timestamp;
	private String traceId;
	private String service;
	private List<String> errors = null;

	public CustomErrorResponse(final String traceld, final String code, final String causes, List<String> errors) {
		this.code = code;
		this.causes = causes;
		this.traceId = traceld;
		try {
			this.service = InetAddress.getLocalHost().getCanonicalHostName();
		} catch (Exception e) {
			this.service = "Unknow";
		}
		
		if (errors != null) {
			this.errors = errors;
		} else {
			this.errors = Collections.emptyList();
		}
	}

	public CustomErrorResponse(final String traceId, final String code, final String causes, List<String> errors,
			WebRequest request) {
		this(traceId, code, causes, errors);
		if (request != null) {
			try {
				service += "+" + request.getContextPath();
			} catch (Exception e) {
				service += "+NoContextRoot";
			}
		}
	}
}
