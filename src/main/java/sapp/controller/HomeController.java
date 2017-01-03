package sapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	/**
	 * show welcome view
	 * @return welcome view
	 */
	@RequestMapping("/")
	public String home() {
		return "welcome";
	}
	
}