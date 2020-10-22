package com.spring_security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	//@ResponseBody // To show string response
	@PreAuthorize("hasRole('ADMIN')")
	public String showSystems() {
		return "systems";
	}
}
