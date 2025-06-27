package com.projects.blog.services;

import com.projects.blog.entities.User;
import com.projects.blog.payloads.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

	UserDTO createUser(UserDTO user);

	UserDTO updateUser(UserDTO user, Integer user_id);

	UserDTO getUserById(Integer user_id);

	List<UserDTO> getAllUsers();

	void deleteUser(Integer user_id);

	User dtoToUser(UserDTO userDTO);

	UserDTO userToDTO(User user);

	User findByUsername(String username);

}
