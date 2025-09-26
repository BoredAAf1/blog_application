package com.projects.blog.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

@JsonFormat
public enum Role {

	ROLE_USER, ROLE_ADMIN;

}
