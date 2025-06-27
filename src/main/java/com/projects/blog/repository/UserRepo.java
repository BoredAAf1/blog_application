package com.projects.blog.repository;

import com.projects.blog.entities.Category;
import com.projects.blog.entities.Post;
import com.projects.blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {

	Optional<User> findByUsername(String username);

}
