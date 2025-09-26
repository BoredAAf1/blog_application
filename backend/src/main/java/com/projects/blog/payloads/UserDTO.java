package com.projects.blog.payloads;

import com.projects.blog.entities.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class UserDTO {

	private Integer userId;

	private String name;

	private String username;

	private String password;

	private String email_id;

	private String about;

	private Set<Role> roles;

}
