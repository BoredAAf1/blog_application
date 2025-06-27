package com.projects.blog.services;

import com.projects.blog.entities.Category;
import com.projects.blog.payloads.CategoryDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

	CategoryDTO createCategoryDTO(CategoryDTO categoryDTO);

	CategoryDTO updateCategoryDTO(CategoryDTO categoryDTO, Integer categoryId);

	void deleteCategory(Integer categoryId);

	CategoryDTO getCategory(Integer categoryId);

	List<CategoryDTO> getAllCategories();

	CategoryDTO categoryToDTO(Category category);

	Category dtoToCategory(CategoryDTO categoryDTO);

}
