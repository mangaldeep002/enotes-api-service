package com.enote.util;

import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.enote.handler.GenericResponse;

public class CommonUtil {
	
	public static ResponseEntity<?> createBuildResponse(Object data, HttpStatus status) {
		
		GenericResponse genericResponse = GenericResponse.builder()
											.responseStatus(status)
											.status("Succcess")
											.message("Succcess")
											.data(data)
											.build();
		return genericResponse.create();
	}
	
	public static ResponseEntity<?> createBuildResponseMessage(String message, HttpStatus status) {
		
		GenericResponse genericResponse = GenericResponse.builder()
											.responseStatus(status)
											.status("Succcess")
											.message(message)
											.build();
		return genericResponse.create();
	}
	
	public static ResponseEntity<?> createErrorResponse(Object data, HttpStatus status) {
		
		GenericResponse genericResponse = GenericResponse.builder()
											.responseStatus(status)
											.status("Failed")
											.message("Failed")
											.data(data)
											.build();
		return genericResponse.create();
	}
	
	public static ResponseEntity<?> createErrorResponseMessage(String message, HttpStatus status) {
		
		GenericResponse genericResponse = GenericResponse.builder()
											.responseStatus(status)
											.status("Failed")
											.message(message)
											.build();
		return genericResponse.create();
	}

	public static String getContentType(String originalFileName) {
		String extension = FilenameUtils.getExtension(originalFileName);
		
		switch (extension) {
			case "pdf" :
				return "application/pdf";
			case "xlsx":
				return "application/vnd.openxmlformats-officedocument.spreadsheettml.sheet";
			case "txt":
				return "text/plan";
			case "png":
				return "image/png";
			case "jpeg":
				return "image/jpeg";
			default:
				return "application/octet-stream";
		}
		
	}
	

}
