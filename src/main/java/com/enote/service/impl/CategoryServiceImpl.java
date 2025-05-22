package com.enote.service.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.enote.dto.CategoryDto;
import com.enote.dto.CategoryResponse;
import com.enote.entity.Category;
import com.enote.repositry.CategoryRepositry;
import com.enote.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepositry categoryRepo;
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public Boolean saveCategory(CategoryDto categoryDto) {
		// Category category = new Category();
		
		Category category = mapper.map(categoryDto, Category.class);
		
		// category.setName(categoryDto.getName());
		// category.setDescription(categoryDto.getDescription());
		// category.setIsActive(categoryDto.getIsActive());
		category.setIsDeleted(false);
		category.setCreatedBy(1);
		Category savedCategory = categoryRepo.save(category);
		
		if (ObjectUtils.isEmpty(savedCategory)) {
			return false;
		}
		
		return true;
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> categories = categoryRepo.findByIsDeletedFalse();
		
		List<CategoryDto> list = categories.stream().map(e -> mapper.map(e, CategoryDto.class)).toList();
		return list;
	}

	@Override
	public List<CategoryResponse> getAllActiveCategory() {
		
		List<Category> category = categoryRepo.findByIsActiveTrueAndIsDeletedFalse();
		List<CategoryResponse> categoryList = category.stream().map(e -> mapper.map(e, CategoryResponse.class)).toList();
		return categoryList;
	}

	@Override
	public CategoryDto getCategoryById(Integer id) {
		Optional<Category> findByCategory = categoryRepo.findByIdAndIsDeletedFalse(id);
		
		if(findByCategory.isPresent()) {
			Category category = findByCategory.get();
			return mapper.map(category, CategoryDto.class);
		}
		return null;
	}

	@Override
	public Boolean deleteCategoryById(Integer id) {
		Optional<Category> findByCategory = categoryRepo.findById(id);
		
		if(findByCategory.isPresent()) {
			Category category = findByCategory.get();
			category.setIsDeleted(true);
			categoryRepo.save(category);
			return true;
		}
		return false;
	
	}

}
