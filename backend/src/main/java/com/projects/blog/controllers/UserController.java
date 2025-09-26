package com.projects.blog.controllers;

import com.projects.blog.payloads.ApiResponse;
import com.projects.blog.payloads.UserDTO;
import com.projects.blog.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
		UserDTO createUserDTO = this.userService.createUser(userDTO);
		return new ResponseEntity<>(createUserDTO, HttpStatus.CREATED);

	}

	@PutMapping("/{user_id}")
	public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO,
			@PathVariable("user_id") Integer u_id) {
		UserDTO updatedUser = this.userService.updateUser(userDTO, u_id);
		return ResponseEntity.ok(updatedUser);
	}

	@DeleteMapping("/{user_id}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("user_id") Integer u_id) {
		this.userService.deleteUser(u_id);
		return new ResponseEntity<>(new ApiResponse("User deleted successfully", true), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<UserDTO>> getAllUsers() {
		return ResponseEntity.ok(this.userService.getAllUsers());
	}

	@GetMapping("/{user_id}")
	public ResponseEntity<UserDTO> getSingleUsers(@PathVariable("user_id") Integer u_id) {
		return ResponseEntity.ok(this.userService.getUserById(u_id));
	}

}
