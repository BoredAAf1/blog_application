// src/main/java/com/projects/blog/entities/User.java
package com.projects.blog.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User implements UserDetails { // Implement UserDetails here

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private Integer userId;

	@Column(nullable = false, length = 100)
	private String name;

	@Column(unique = true, nullable = false) // Good practice to make username unique
	private String username;

	private String password;

	private String email_id;

	@Column(name = "description")
	private String about;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Post> posts = new ArrayList<>();

	@ElementCollection(fetch = FetchType.EAGER) // Fetch roles eagerly for security
	@Enumerated(EnumType.STRING)
	private Set<Role> roles = new HashSet<>();

	// --- UserDetails Methods ---

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// Map your Set<Role> to a List<SimpleGrantedAuthority>
		return this.roles.stream()
				.map(role -> new SimpleGrantedAuthority(role.name()))
				.collect(Collectors.toList());
	}

	@Override
	public String getUsername() {
		// Return the field you use for login
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true; // Or add logic for account expiration
	}

	@Override
	public boolean isAccountNonLocked() {
		return true; // Or add logic for account locking
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true; // Or add logic for credentials expiration
	}

	@Override
	public boolean isEnabled() {
		return true; // Or add logic for disabling users
	}
}
