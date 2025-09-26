package com.projects.blog.services.impl;

import com.projects.blog.entities.User;
import com.projects.blog.exceptions.ResourceNotFoundException;
import com.projects.blog.payloads.UserDTO;
import com.projects.blog.repository.UserRepo;
import com.projects.blog.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder; // <-- Inject the password encoder

	@Override
	public UserDTO createUser(UserDTO userDTO) {
		System.out.println(">> Creating user: " + userDTO.getUsername());
		User user = this.dtoToUser(userDTO);
		// Encode the password before saving
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		User savedUser = this.userRepo.save(user);
		return this.userToDTO(savedUser);
	}

	@Override
	public UserDTO updateUser(UserDTO userDTO, Integer user_id) {
		User user = this.userRepo.findById(user_id)
			.orElseThrow(() -> new ResourceNotFoundException("User", "id", user_id));
		user.setAbout(userDTO.getAbout());
		user.setName(userDTO.getName());
		user.setUsername(userDTO.getUsername());
		user.setPassword(userDTO.getPassword());
		user.setEmail_id(userDTO.getEmail_id());
		User updatedUser = this.userRepo.save(user);
		return this.userToDTO(updatedUser);
	}

	@Override
	public UserDTO getUserById(Integer user_id) {
		User user = this.userRepo.findById(user_id)
			.orElseThrow(() -> new ResourceNotFoundException("User", "user_id", user_id));
		return this.userToDTO((user));
	}

	@Override
	public List<UserDTO> getAllUsers() {
		List<User> users = this.userRepo.findAll();
		return users.stream().map(this::userToDTO).toList();
	}

	@Override
	public void deleteUser(Integer user_id) {
		User user = this.userRepo.findById(user_id)
			.orElseThrow(() -> new ResourceNotFoundException("User", "user_id", user_id));
		this.userRepo.delete(user);
	}

	@Override
	public User dtoToUser(UserDTO userDTO) {
		return this.modelMapper.map(userDTO, User.class);
	}

	@Override
	public UserDTO userToDTO(User user) {
		return this.modelMapper.map(user, UserDTO.class);
	}

	@Override
	public User findByUsername(String username) {
		return this.userRepo.findByUsername(username)
			.orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
	}

}
