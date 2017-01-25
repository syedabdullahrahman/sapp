package sapp.controller.blog;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import sapp.model.BlogEntry;
import sapp.model.User;
import sapp.service.BlogEntryService;
import sapp.service.UserService;


@Controller
public class BlogController {
	@SuppressWarnings("unused")
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserService userService;
	@Autowired
	private BlogEntryService blogEntryService;
	
	@RequestMapping("/blog")
	@Transactional
	public String showBlog(Model model) {
		model.addAttribute("users", userService.findAll()); 
		model.addAttribute("entries", blogEntryService.findAllDesc());
		 
		return "blog";
	}

	@RequestMapping("/other")
	@Transactional
	public String createTestEntry(Model model){
		User admin = userService.findByUsername("Admin");
		BlogEntry entry = new BlogEntry(); 
		
		entry.setTitle("Test entry");
		entry.setContent("My first blog entry content!");
		entry.setCreationDateTime(new java.util.Date());
		
		admin.getBlogEntries().add(entry);
		entry.setAuthor(admin);

		blogEntryService.save(entry);
		return "blog";
	}
	
}