package com.projects.blog.services.impl;

import com.projects.blog.entities.Category;
import com.projects.blog.exceptions.ResourceNotFoundException;
import com.projects.blog.payloads.CategoryDTO;
import com.projects.blog.repository.CategoryRepo;
import com.projects.blog.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDTO createCategoryDTO(CategoryDTO categoryDTO) {
		Category category = this.modelMapper.map(categoryDTO, Category.class);
		Category addedCategory = this.categoryRepo.save(category);
		return categoryToDTO(addedCategory);
	}

	@Override
	public CategoryDTO updateCategoryDTO(CategoryDTO categoryDTO, Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
			.orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryID", categoryId));
		category.setTitle(categoryDTO.getTitle());
		category.setDescription(categoryDTO.getDescription());
		Category updatedCategory = this.categoryRepo.save(category);
		return categoryToDTO(updatedCategory);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
			.orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryID", categoryId));
		this.categoryRepo.delete(category);
	}

	@Override
	public CategoryDTO getCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
			.orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryID", categoryId));
		return categoryToDTO(category);
	}

	@Override
	public List<CategoryDTO> getAllCategories() {

		List<Category> categoryList = this.categoryRepo.findAll();
		return categoryList.stream().map((cat) -> this.modelMapper.map(cat, CategoryDTO.class)).toList();
	}

	@Override
	public CategoryDTO categoryToDTO(Category category) {
		return this.modelMapper.map(category, CategoryDTO.class);
	}

	@Override
	public Category dtoToCategory(CategoryDTO categoryDTO) {
		return this.modelMapper.map(categoryDTO, Category.class);
	}

}
