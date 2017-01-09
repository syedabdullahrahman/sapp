package sapp.controller.profile;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
	@Autowired 
	MessageSource messageSource;
	
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
		model.addAttribute("form", new ProfileForm(getAuthenticatedUser()));
		model.addAttribute("txt", "txt");
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
	public String saveOrUpdateUser(
			@Valid ProfileForm form,
			BindingResult bindingResult){
			System.out.println("here");
		if(bindingResult.hasErrors()){
			System.out.println("here2");
			return "profileedit";
		}
			System.out.println("SAVE OR UPDATE");
		return "redirect:/profile/show";
	}
	
	
	@RequestMapping(value = "/profiless", params = { "save" }, method = RequestMethod.POST)
	public String saveProfile(@Valid ProfileForm profileForm,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "profile/profilePage";
		}
		 return "redirect:/search/mixed;keywords=";
	}
	
	
	
	

}
