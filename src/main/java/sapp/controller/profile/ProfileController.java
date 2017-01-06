package sapp.controller.profile;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProfileController {

	@RequestMapping("/profile")
	public String noAccess(Model model) {
		
		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		 model.addAttribute("name",auth.getName());
		 model.addAttribute("authorities",auth.getAuthorities());
		
		return "profile";
	}
	
}
