package sapp.controller.profile;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProfileController {

	@RequestMapping("/profile")
	public String noAccess(Model model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			User suser = (User)auth.getPrincipal();
			model.addAttribute("sname", suser.getUsername());
			model.addAttribute("sauth", suser.getAuthorities());
			model.addAttribute("name", auth.getName());
			model.addAttribute("authorities", auth.getAuthorities());
		}

		return "profile";
	}

}
