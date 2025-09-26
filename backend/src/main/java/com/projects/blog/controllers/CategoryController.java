package com.projects.blog.controllers;

import com.projects.blog.payloads.ApiResponse;
import com.projects.blog.payloads.CategoryDTO;
import com.projects.blog.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	// create
	@Autowired
	private CategoryService categoryService;

	@PostMapping
	public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
		CategoryDTO createCategoryDTO = this.categoryService.createCategoryDTO(categoryDTO);
		return new ResponseEntity<>(createCategoryDTO, HttpStatus.CREATED);
	}

	@PutMapping("/{catId}")
	public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO,
			@PathVariable Integer catId) {
		CategoryDTO updateCategoryDTO = this.categoryService.updateCategoryDTO(categoryDTO, catId);
		return new ResponseEntity<>(updateCategoryDTO, HttpStatus.OK);
	}

	@DeleteMapping("/{catId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer catId) {
		this.categoryService.deleteCategory(catId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category is deleted succesfully!", true),
				HttpStatus.OK);
	}

	@GetMapping("/{catId}")
	public ResponseEntity<CategoryDTO> getCategory(@PathVariable Integer catId) {
		CategoryDTO updateCategoryDTO = this.categoryService.getCategory(catId);
		return new ResponseEntity<>(updateCategoryDTO, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<CategoryDTO>> getAllCategories() {
		List<CategoryDTO> categoryDTOList = this.categoryService.getAllCategories();
		return ResponseEntity.ok(categoryDTOList);
	}

}
