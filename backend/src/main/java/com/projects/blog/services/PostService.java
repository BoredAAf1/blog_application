package com.projects.blog.services;

import com.projects.blog.entities.Post;
import com.projects.blog.entities.User;
import com.projects.blog.payloads.PostDTO;
import com.projects.blog.payloads.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {

	PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId);

	PostDTO updatePost(PostDTO postDTO, Integer postId);

	void deletePost(Integer postId);

	PostDTO getPostById(Integer postId);

	Page<PostDTO> getPostsByCategoryId(Integer categoryId, Pageable pageable);

	Page<PostDTO> getPostsByUserId(Integer userId, Pageable pageable);

	Page<PostDTO> getAllPosts(Pageable pageable);

	Page<PostDTO> searchPosts(String keyword, Pageable pageable);

	Post dtoToPost(PostDTO postDTO);

	PostDTO postToDTO(Post post);

}
