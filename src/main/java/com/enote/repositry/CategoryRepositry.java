package com.enote.repositry;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enote.entity.Category;

public interface CategoryRepositry extends JpaRepository<Category, Integer> {

}
