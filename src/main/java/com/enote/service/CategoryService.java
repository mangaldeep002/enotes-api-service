package com.enote.service;

import java.util.List;

import com.enote.entity.Category;

public interface CategoryService {
	
	public Boolean saveCategory(Category category);
	
	public List<Category> getAllCategory();
}
