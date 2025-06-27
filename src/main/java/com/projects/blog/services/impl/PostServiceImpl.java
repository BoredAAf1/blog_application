package com.projects.blog.services.impl;

import com.projects.blog.entities.Category;
import com.projects.blog.entities.Post;
import com.projects.blog.entities.User;
import com.projects.blog.exceptions.ResourceNotFoundException;
import com.projects.blog.payloads.PostDTO;
import com.projects.blog.repository.CategoryRepo;
import com.projects.blog.repository.PostRepo;
import com.projects.blog.repository.UserRepo;
import com.projects.blog.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId) {
		User user = this.userRepo.findById(userId)
			.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		Category cat = this.categoryRepo.findById(categoryId)
			.orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryID", categoryId));
		Post post = this.modelMapper.map(postDTO, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(LocalDateTime.now());
		post.setUser(user);
		post.setCategory(cat);
		Post addedPost = this.postRepo.save(post);
		return postToDTO(post);

	}

	@Override
	public PostDTO updatePost(PostDTO postDTO, Integer postId) {
		Post post = this.postRepo.findById(postId)
			.orElseThrow(() -> new ResourceNotFoundException("Post", "Post ID", postId));
		post.setTitle(postDTO.getTitle());
		post.setContent(postDTO.getContent());
		post.setAddedDate(postDTO.getAddedDate());
		Post updatedpost = this.postRepo.save(post);
		return postToDTO(updatedpost);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId)
			.orElseThrow(() -> new ResourceNotFoundException("Post", "Post ID", postId));
		this.postRepo.delete(post);

	}

	@Override
	public PostDTO getPostById(Integer postId) {
		Post postById = this.postRepo.findById(postId)
			.orElseThrow(() -> new ResourceNotFoundException("Post", "Post ID", postId));
		return postToDTO(postById);
	}

	@Override
	public Page<PostDTO> getPostsByCategoryId(Integer categoryId, Pageable pageable) {
		Category cat = this.categoryRepo.findById(categoryId)
			.orElseThrow(() -> new ResourceNotFoundException("Category", "Category ID", categoryId));
		Page<Post> postsByCategory = this.postRepo.findByCategory(cat, pageable);
		return postsByCategory.map((this::postToDTO));
	}

	@Override
	public Page<PostDTO> getPostsByUserId(Integer userId, Pageable pageable) {
		User user = this.userRepo.findById(userId)
			.orElseThrow(() -> new ResourceNotFoundException("User", "User ID", userId));
		Page<Post> postsByUser = this.postRepo.findByUser(user, pageable);
		return postsByUser.map((this::postToDTO));
	}

	@Override
	public Page<PostDTO> getAllPosts(Pageable pageable) {
		return postRepo.findAll(pageable).map(this::postToDTO);
	}

	@Override
	public Page<PostDTO> searchPosts(String keyword, Pageable pageable) {
		Page<Post> posts = postRepo.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(keyword, keyword,
				pageable);
		;
		return posts.map(this::postToDTO);
	}

	@Override
	public Post dtoToPost(PostDTO postDTO) {
		return this.modelMapper.map(postDTO, Post.class);
	}

	@Override
	public PostDTO postToDTO(Post post) {
		return this.modelMapper.map(post, PostDTO.class);
	}

}
