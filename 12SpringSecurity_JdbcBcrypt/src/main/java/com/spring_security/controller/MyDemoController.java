package com.spring_security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyDemoController {

	@GetMapping("/")
	public String showHome() {
		return "home";
	}
	
	// Add Mapping to Access /leaders
	@GetMapping("/leaders")
	public String showLeaders() {
		return "leaders";
	}

	// Add Mapping to Access /leaders
	@GetMapping("/systems")
	public String showSystems() {
		return "systems";
	}
}
