package com.enote.util;

import java.util.LinkedHashMap;
import java.util.Map;

import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.enote.dto.CategoryDto;
import com.enote.exception.ValidationException;

@Component
public class Validation {
	
	public void CategoryValidation(CategoryDto categoryDto) {
		Map<String, Object> error = new LinkedHashMap();
		
		if (ObjectUtils.isEmpty(categoryDto)) {
			throw new IllegalIdentifierException("Category Object should not empty.");
		} else {
			
			//Validate name field
			if(ObjectUtils.isEmpty(categoryDto.getName())) {
				error.put("name", "name field is not empty or null");
			} else {
				if (categoryDto.getName().length() < 3) {
					error.put("name", "name min length 3");
				}
				
				if (categoryDto.getName().length() > 100) {
					error.put("name", "name max length 100");
				}
			}
			
			//Validate description
			if(ObjectUtils.isEmpty(categoryDto.getDescription())) {
				error.put("description", "description field is not empty or null");
			}
			
			// validation isActive
			if(ObjectUtils.isEmpty(categoryDto.getIsActive())) {
				error.put("isActive", "isActive field is not empty or null");
			} else {
				if (categoryDto.getIsActive() != Boolean.TRUE.booleanValue() &&
						categoryDto.getIsActive() != Boolean.FALSE.booleanValue()) {
					error.put("isActive", "Invaliad value active field");
				}
			}
		}
		
		if (!error.isEmpty()) {
			throw new ValidationException(error);
		}
	}
}
