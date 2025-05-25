package com.enote.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.enote.dto.CategoryDto;
import com.enote.dto.CategoryResponse;
import com.enote.entity.Category;
import com.enote.exception.NameAlreadyExistException;
import com.enote.exception.ResourceNotFoundException;
import com.enote.repositry.CategoryRepositry;
import com.enote.service.CategoryService;
import com.enote.util.Validation;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepositry categoryRepo;
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private Validation validation;
	
	@Override
	public Boolean saveCategory(CategoryDto categoryDto) throws NameAlreadyExistException {
		// Category category = new Category();
		
		validation.CategoryValidation(categoryDto);
		
		Category category = mapper.map(categoryDto, Category.class);
		
		boolean nameExist = categoryRepo.existsByNameIgnoreCase(category.getName());
		if (nameExist) {
			throw new NameAlreadyExistException("Category name already exist");
		}
		
		if (ObjectUtils.isEmpty(category.getId())) {
			category.setIsDeleted(false);
			// category.setCreatedBy(1);
		} else {
			updateCategory(category);
		}
		
		// category.setName(categoryDto.getName());
		// category.setDescription(categoryDto.getDescription());
		// category.setIsActive(categoryDto.getIsActive());
		
		Category savedCategory = categoryRepo.save(category);
		
		if (ObjectUtils.isEmpty(savedCategory)) {
			return false;
		}
		
		return true;
	}

	private void updateCategory(Category category) {
		
		Optional<Category> findById = categoryRepo.findById(category.getId());
		
		if(findById.isPresent()) {
			Category existingCategory = findById.get();
			category.setCreatedBy(existingCategory.getCreatedBy());
			category.setCreatedOn(existingCategory.getCreatedOn());
			category.setIsDeleted(existingCategory.getIsDeleted());
			// category.setUpdatedBy(1);
			// category.setUpdatedOn(new Date());
		}
		
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
	public CategoryDto getCategoryById(Integer id) throws ResourceNotFoundException {
		Category category = categoryRepo.findByIdAndIsDeletedFalse(id).orElseThrow(() ->
				new ResourceNotFoundException("Category not found with id: "+ id));
		
		if(!ObjectUtils.isEmpty(category)) {
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
