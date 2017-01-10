package sapp.controller.profile;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sapp.model.User;
import sapp.service.UserService;

@Controller
@RequestMapping("/profile")
public class ProfileController {

	@Autowired
	UserService userService;
	
	private User getAuthenticatedUser(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			User modelUser;
			org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User)auth.getPrincipal();
			modelUser = userService.findByUsername(user.getUsername());
			System.out.println("user: " + user);
			System.out.println("modelUser: " + modelUser);
			return modelUser;
		}
		return null;
	}
	
	/**
	 * show existing profile
	 * @param model
	 * @return
	 */
	@RequestMapping("/show")
	public String showProfile(Model model) {
		model.addAttribute("user", getAuthenticatedUser());
		return "profile";
	}
	
	/**
	 * show registration/edit form
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/edit")
	public String showUserEditForm(Model model) {
		User user = getAuthenticatedUser();
		model.addAttribute("user", user);
		model.addAttribute("profileForm", new ProfileForm(user));
		return "profileedit";
	}
	
	/**
	 * save or update user
	 * @param user 
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String saveOrUpdateUser(@Valid ProfileForm profileForm,BindingResult bindingResult){
		if(bindingResult.hasErrors()){
			System.out.println("errors");
			return "profileedit";
		}
		
		User modelUser = userService.findByUsername(profileForm.getUsername());
		if(modelUser == null){
			modelUser= new User();
		}
		
		modelUser.setName(profileForm.getName());
		modelUser.setEmail(profileForm.getEmail());
		userService.saveOrUpdate(modelUser);
		
		System.out.println("to profile/show");
		
		return "redirect:/profile/show";
	}

}
