package sapp.controller.blog;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class BlogForm {

	private long id; 
	@Size(min = 5, max=47)
	private String title;
	@NotEmpty
	private String content;

	public String getTitle() {
		return title;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	
}
