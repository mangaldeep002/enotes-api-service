package com.enote.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enote.dto.CategoryDto;
import com.enote.dto.CategoryResponse;
import com.enote.entity.Category;
import com.enote.exception.NameAlreadyExistException;
import com.enote.exception.ResourceNotFoundException;
import com.enote.service.CategoryService;
import com.enote.util.CommonUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;	
	
	@PostMapping("/save")
	public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto) throws NameAlreadyExistException {
		Boolean savedCategory = categoryService.saveCategory(categoryDto);
		
		if (savedCategory) {
			return CommonUtil.createBuildResponseMessage("saved", HttpStatus.CREATED);
			// return new ResponseEntity<>("saved", HttpStatus.CREATED);
		} else {
			return CommonUtil.createErrorResponseMessage("not saved", HttpStatus.INTERNAL_SERVER_ERROR);
			// return new ResponseEntity<>("not saved", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/")
	public ResponseEntity<?> getAllCategory() {
		List<CategoryDto> allCategory = categoryService.getAllCategory();
		
		if (CollectionUtils.isEmpty(allCategory)) {
			return ResponseEntity.noContent().build();
		} else {
			return CommonUtil.createBuildResponse(allCategory, HttpStatus.OK);
			// return new ResponseEntity<>(allCategory, HttpStatus.OK);
		}
		
	}
	
	@GetMapping("/active")
	public ResponseEntity<?> getActiveCategory() {
		List<CategoryResponse> allActiveCategory = categoryService.getAllActiveCategory();
		
		if (CollectionUtils.isEmpty(allActiveCategory)) {
			// return ResponseEntity.noContent().build();
			return CommonUtil.createErrorResponseMessage("Internal Server Error", HttpStatus.NO_CONTENT);
		} else {
			// return new ResponseEntity<>(allActiveCategory, HttpStatus.OK);
			return CommonUtil.createBuildResponse(allActiveCategory, HttpStatus.OK);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getCategoryDetailsById(@PathVariable("id") Integer id) throws ResourceNotFoundException {
//		try {
//			CategoryDto categoryDto = categoryService.getCategoryById(id);
//			if (ObjectUtils.isEmpty(categoryDto)) {
//				return new ResponseEntity<>("Category not found with Id: "+ id, HttpStatus.NOT_FOUND);
//			} 
//			
//			return new ResponseEntity<>(categoryDto, HttpStatus.OK);
//		} catch (ResourceNotFoundException e) {
//			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//		} catch (Exception e) {
//			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//		}
		
		CategoryDto categoryDto = categoryService.getCategoryById(id);
		if (ObjectUtils.isEmpty(categoryDto)) {
			// return new ResponseEntity<>("Category not found with Id: "+ id, HttpStatus.NOT_FOUND);
			return CommonUtil.createErrorResponseMessage("Internal Server Error", HttpStatus.NO_CONTENT);
		} 
		
		// return new ResponseEntity<>(categoryDto, HttpStatus.OK);
		return CommonUtil.createBuildResponse(categoryDto, HttpStatus.OK);
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCategoryById(@PathVariable("id") Integer id) {
		Boolean isDeleted = categoryService.deleteCategoryById(id);
		
		if (isDeleted) {
			// return new ResponseEntity<>("Category deleted successfully", HttpStatus.OK);
			return CommonUtil.createBuildResponseMessage("Category deleted successfully", HttpStatus.OK);
		} 
		
		// return new ResponseEntity<>("Category not deleted", HttpStatus.INTERNAL_SERVER_ERROR);
		return CommonUtil.createErrorResponseMessage("Category can not be deleted", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
