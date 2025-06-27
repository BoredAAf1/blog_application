package com.projects.blog.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {

	private String fieldName;

	private String resourceName;

	private long fieldValue;

	private String fieldString;

	public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
		super(String.format("%s not found with %s : %d", resourceName, fieldName, fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

	public ResourceNotFoundException(String resourceName, String fieldName, String fieldString) {
		super(String.format("%s not found with %s : %s", resourceName, fieldName, fieldString));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldString = fieldString;
	}

}
