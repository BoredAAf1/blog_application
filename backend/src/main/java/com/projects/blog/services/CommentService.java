package com.projects.blog.services;

import com.projects.blog.entities.Comment;
import com.projects.blog.payloads.CommentDTO;
import com.projects.blog.payloads.PostDTO;

public interface CommentService {

	CommentDTO createComment(CommentDTO commentDTO, Integer postId);

	void deleteComment(Integer commentId);

	CommentDTO commentToDTO(Comment comment);

	Comment dtoToComment(CommentDTO commentDTO);

}
