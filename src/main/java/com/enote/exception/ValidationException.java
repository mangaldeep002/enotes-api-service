package com.enote.exception;

import java.util.LinkedHashMap;
import java.util.Map;

public class ValidationException extends RuntimeException {
	
	private Map<String, Object> errors = new LinkedHashMap<>();
	
	public ValidationException(Map<String, Object> errors) {
		super("Error");
		this.errors = errors;
	}
	
	public Map<String, Object> getErrors() {
		return errors;
	}
}
