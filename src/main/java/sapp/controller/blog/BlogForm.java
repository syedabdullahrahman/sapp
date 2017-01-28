package sapp.controller.blog;

import javax.validation.constraints.Size;

public class BlogForm {

	private long id; 
	@Size(min = 2, max=25)
	private String title;
	@Size(min = 5)
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
