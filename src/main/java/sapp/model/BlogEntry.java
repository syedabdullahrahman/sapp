package sapp.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "blog_entries")
public class BlogEntry {
	private long id; 
	private Date creationDateTime;
	private User author;
	private String title;
    private String content;

    public BlogEntry() {

	}
    
    /*
     * ID
     */
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	/*
	 * CREATION DATE/TIME
	 */
	@Column(name="creation_date_time")
    @Temporal(TemporalType.TIMESTAMP)
	public Date getCreationDateTime() {
		return creationDateTime;
	}

	public void setCreationDateTime(Date creationDateTime) {
		this.creationDateTime = creationDateTime;
	}
	
	/*
	 * AUTHOR
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = false)
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	
	/*
	 * TITLE
	 */
	@Column(nullable=false)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	/*
	 * CONTENT
	 */
	@Column(nullable=false)
	@Type(type="text")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
    
    

}