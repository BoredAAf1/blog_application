package com.projects.blog.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@PostMapping("/ping")
	public String ping() {
		System.out.println(">> Pinged");
		return "pong";
	}

}
