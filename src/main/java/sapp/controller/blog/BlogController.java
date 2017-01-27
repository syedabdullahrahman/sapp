package sapp.controller.blog;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import sapp.model.BlogEntry;
import sapp.model.User;
import sapp.service.BlogEntryService;
import sapp.service.UserService;


@Controller
@RequestMapping("/blog")
public class BlogController {
	@SuppressWarnings("unused")
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private static final int ENTRIES_PER_PAGE = 10;
	
	@Autowired
	private UserService userService;
	@Autowired
	private BlogEntryService blogEntryService;
	
	@RequestMapping("/read/{page}")
	public String showBlog(Model model, @PathVariable int page) {
		long entriesNo = blogEntryService.countAll();
		int pagesNo = (int)entriesNo/ENTRIES_PER_PAGE;
		if (entriesNo - pagesNo * ENTRIES_PER_PAGE > 0) {
			pagesNo+=1;
		}
		model.addAttribute("currentPage", page);
		model.addAttribute("pages", new byte[pagesNo]);
		model.addAttribute("entries", blogEntryService.findPaginateDesc(page, ENTRIES_PER_PAGE));
		return "blog";
	}

	@RequestMapping("/create")
	public String cre(Model model){
		model.addAttribute("blogForm", new BlogForm());
		return "blogedit";
	}
	
	@RequestMapping("/add")
	@Transactional
	public String createTestEntry(Model model){
		User admin = userService.findByUsername("Admin");
		BlogEntry entry = new BlogEntry(); 
		
		entry.setTitle("I'm learning Spring :)");
		entry.setContent("My first blog entry content! well.. I'll try to make it a bit longer to test the blog layout. I don't think it wolud look professional though... :P My first blog entry content! well.. I'll try to make it a bit longer to test the blog layout. I don't think it wolud look professional though... :P My first blog entry content! well.. I'll try to make it a bit longer to test the blog layout. I don't think it wolud look professional though... :P My first blog entry content! well.. I'll try to make it a bit longer to test the blog layout. I don't think it wolud look professional though... :P My first blog entry content! well.. I'll try to make it a bit longer to test the blog layout. I don't think it wolud look professional though... :P My first blog entry content! well.. I'll try to make it a bit longer to test the blog layout. I don't think it wolud look professional though... :P My first blog entry content! well.. I'll try to make it a bit longer to test the blog layout. I don't think it wolud look professional though... :P My first blog entry content! well.. I'll try to make it a bit longer to test the blog layout. I don't think it wolud look professional though... :P");
		entry.setCreationDateTime(new java.util.Date());
		
		admin.getBlogEntries().add(entry);
		entry.setAuthor(admin);

		blogEntryService.save(entry);
		return "blog";
	}
	
}