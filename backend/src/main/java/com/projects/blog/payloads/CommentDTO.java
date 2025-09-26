package com.projects.blog.payloads;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentDTO {

	private Integer commentId;

	private Integer content;

}
