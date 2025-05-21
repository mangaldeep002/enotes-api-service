package com.enote.service;

import java.util.List;

import com.enote.dto.CategoryDto;
import com.enote.dto.CategoryResponse;
import com.enote.entity.Category;

public interface CategoryService {
	
	public Boolean saveCategory(CategoryDto categoryDto);
	
	public List<CategoryDto> getAllCategory();
	
	public List<CategoryResponse> getAllActiveCategory();
}
