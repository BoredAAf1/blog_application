// src/main/java/com/projects/blog/payloads/JwtAuthRequest.java
package com.projects.blog.payloads;

import lombok.Data;

@Data
public class JwtAuthRequest {
    private String username;
    private String password;
}
