package com.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	
	@RequestMapping("/")
	public String home() {
		return "index";
	}
	
	/** favicon **/
	@RequestMapping("/favicon.ico")
	public String favicon() {
		return "forward:/resources/img/main/favicon.ico";
	}//favicon()
	
}
