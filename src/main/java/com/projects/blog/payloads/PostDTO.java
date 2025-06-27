package com.projects.blog.payloads;

import com.projects.blog.entities.Category;
import com.projects.blog.entities.Comment;
import com.projects.blog.entities.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {

	private Integer postId;

	@NotBlank
	@Size(min = 3, message = "Title must be at least 3 characters long")

	private String title;

	@NotBlank
	@Size(min = 10, message = "Content must be at least 10 characters long")
	private String content;

	private String imageName;

	private LocalDateTime addedDate;

	private CategoryDTO category;

	private UserDTO user;

	private Set<CommentDTO> commentSet = new HashSet<>();

}
