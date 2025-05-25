package com.enote.service;

import java.util.List;

import com.enote.dto.CategoryDto;
import com.enote.dto.CategoryResponse;
import com.enote.entity.Category;
import com.enote.exception.NameAlreadyExistException;
import com.enote.exception.ResourceNotFoundException;

public interface CategoryService {
	
	public Boolean saveCategory(CategoryDto categoryDto) throws NameAlreadyExistException;
	
	public List<CategoryDto> getAllCategory();
	
	public List<CategoryResponse> getAllActiveCategory();
	
	public CategoryDto getCategoryById(Integer id) throws ResourceNotFoundException;
	
	public Boolean deleteCategoryById(Integer id);
}
