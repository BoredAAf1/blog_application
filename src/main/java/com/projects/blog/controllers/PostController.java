package com.projects.blog.controllers;

import com.projects.blog.config.AppConsts;
import com.projects.blog.entities.Post;
import com.projects.blog.payloads.ApiResponse;
import com.projects.blog.payloads.CategoryDTO;
import com.projects.blog.payloads.PostDTO;
import com.projects.blog.services.FileService;
import com.projects.blog.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class PostController {

	@Autowired
	private FileService fileService;

	@Autowired
	private PostService postService;

	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO, @PathVariable Integer userId,
			@PathVariable Integer categoryId) {
		PostDTO createdPostDTO = this.postService.createPost(postDTO, userId, categoryId);
		return new ResponseEntity<>(createdPostDTO, HttpStatus.CREATED);
	}

	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<Page<PostDTO>> getPostsByUserId(@PathVariable Integer userId,
			@RequestParam(defaultValue = AppConsts.PAGE_NUMBER) int pageNumber,
			@RequestParam(defaultValue = AppConsts.PAGE_SIZE) int pageSize,
			@RequestParam(defaultValue = AppConsts.SORT_BY) String sortBy) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
		Page<PostDTO> postsByUserId = this.postService.getPostsByUserId(userId, pageable);
		return ResponseEntity.ok(postsByUserId);
	}

	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<Page<PostDTO>> getPostsByCategoryId(@PathVariable Integer categoryId,
			@RequestParam(defaultValue = AppConsts.PAGE_NUMBER) int pageNumber,
			@RequestParam(defaultValue = AppConsts.PAGE_SIZE) int pageSize,
			@RequestParam(defaultValue = AppConsts.SORT_BY) String sortBy) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
		Page<PostDTO> postsByCategoryId = this.postService.getPostsByCategoryId(categoryId, pageable);
		return ResponseEntity.ok(postsByCategoryId);
	}

	@GetMapping("/posts")
	public ResponseEntity<Page<PostDTO>> getAllPosts(@RequestParam(defaultValue = AppConsts.PAGE_NUMBER) int pageNumber,
			@RequestParam(defaultValue = AppConsts.PAGE_SIZE) int pageSize,
			@RequestParam(defaultValue = AppConsts.SORT_BY) String sortBy) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
		Page<PostDTO> allPosts = this.postService.getAllPosts(pageable);
		return ResponseEntity.ok(allPosts);
	}

	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDTO> getPostByPostId(@PathVariable Integer postId) {
		PostDTO postByPostId = this.postService.getPostById(postId);
		return ResponseEntity.ok(postByPostId);
	}

	@DeleteMapping("/posts/{postId}")
	public ApiResponse deletePostByPostId(@PathVariable Integer postId) {
		this.postService.deletePost(postId);
		return new ApiResponse("Post has been deleted successfully", true);
	}

	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO, @PathVariable Integer postId) {
		PostDTO updatedPostDTO = this.postService.updatePost(postDTO, postId);
		return ResponseEntity.ok(updatedPostDTO);
	}

	@GetMapping("/posts/search")
	public ResponseEntity<Page<PostDTO>> searchPost(@RequestParam String keyword,
			@RequestParam(defaultValue = AppConsts.PAGE_NUMBER) int pageNumber,
			@RequestParam(defaultValue = AppConsts.PAGE_SIZE) int pageSize,
			@RequestParam(defaultValue = AppConsts.SORT_BY) String sortBy) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
		Page<PostDTO> results = this.postService.searchPosts(keyword, pageable);
		return ResponseEntity.ok(results);

	}

	@GetMapping("/posts/image/upload/{postId}")
	public ResponseEntity<PostDTO> updatePost(@PathVariable Integer postId, @RequestParam MultipartFile imageFile)
			throws IOException {
		PostDTO postDTO = this.postService.getPostById(postId);
		String fileName = this.fileService.uploadImage(AppConsts.UPLOAD_PATH, imageFile);
		postDTO.setImageName(fileName);
		PostDTO updatedPostDTO = this.postService.updatePost(postDTO, postId);
		return ResponseEntity.ok(postDTO);
	}

	@GetMapping("/post/image/{imageName:.+}")
	public void downloadImage(@PathVariable String imageName, HttpServletResponse response) throws IOException {
		InputStream resource = this.fileService.getResource(AppConsts.UPLOAD_PATH, imageName);
		String contentType = Files.probeContentType(Path.of(AppConsts.UPLOAD_PATH, imageName));
		if (contentType == null) {
			contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
		}
		response.setContentType(contentType);
		StreamUtils.copy(resource, response.getOutputStream());
	}

}
