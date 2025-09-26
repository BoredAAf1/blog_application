package com.projects.blog.services.impl;

import com.projects.blog.entities.Comment;
import com.projects.blog.entities.Post;
import com.projects.blog.exceptions.ResourceNotFoundException;
import com.projects.blog.payloads.CommentDTO;
import com.projects.blog.payloads.PostDTO;
import com.projects.blog.repository.CommentRepo;
import com.projects.blog.repository.PostRepo;
import com.projects.blog.services.CommentService;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private CommentRepo commentRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDTO createComment(CommentDTO commentDTO, Integer postId) {
		Post post = this.postRepo.findById(postId)
			.orElseThrow(() -> new ResourceNotFoundException("Post", "Post ID", postId));
		Comment comment = dtoToComment(commentDTO);
		comment.setPost(post);
		Comment savedComment = this.commentRepo.save(comment);
		return commentToDTO(comment);

	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment = this.commentRepo.findById(commentId)
			.orElseThrow(() -> new ResourceNotFoundException("Comment", "Comment ID", commentId));
		this.commentRepo.delete(comment);
	}

	@Override
	public CommentDTO commentToDTO(Comment comment) {
		return this.modelMapper.map(comment, CommentDTO.class);
	}

	@Override
	public Comment dtoToComment(CommentDTO commentDTO) {
		return this.modelMapper.map(commentDTO, Comment.class);
	}

}
