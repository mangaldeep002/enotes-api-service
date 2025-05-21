package com.enote.repositry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enote.entity.Category;

public interface CategoryRepositry extends JpaRepository<Category, Integer> {

	List<Category> findByIsActiveTrue();
}
