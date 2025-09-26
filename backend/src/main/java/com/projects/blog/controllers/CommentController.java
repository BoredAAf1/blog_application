package com.projects.blog.controllers;

import com.projects.blog.entities.Comment;
import com.projects.blog.payloads.ApiResponse;
import com.projects.blog.payloads.CommentDTO;
import com.projects.blog.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@Autowired
	private ModelMapper modelMapper;

	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO, @PathVariable Integer postId) {

		CommentDTO createdCommentDTO = this.commentService.createComment(commentDTO, postId);
		return new ResponseEntity<>(createdCommentDTO, HttpStatus.CREATED);
	}

	@DeleteMapping("/post/{postId}/comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@RequestParam Integer commentId) {
		this.commentService.deleteComment(commentId);
		return ResponseEntity.ok(new ApiResponse("Comment deleted successfully!", true));
	}

}
