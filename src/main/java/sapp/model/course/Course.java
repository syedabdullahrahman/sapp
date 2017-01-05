package sapp.model.course;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "COURSE")
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "TITLE", length = 100, nullable = false)
	private String title;

	@Column(name = "BEGIN_DATE")
	private Date beginDate;

	@Column(name = "END_DATE")
	private Date endDate;

	@Column(name = "FEE")
	private int fee;

	public Course() {
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the beginDate
	 */
	public Date getBeginDate() {
		return beginDate;
	}

	/**
	 * @param beginDate the beginDate to set
	 */
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the fee
	 */
	public int getFee() {
		return fee;
	}

	/**
	 * @param fee the fee to set
	 */
	public void setFee(int fee) {
		this.fee = fee;
	}

	public Course(Long id, String title, Date beginDate, Date endDate, int fee) {
		super();
		this.id = id;
		this.title = title;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.fee = fee;
	}
	
	@Override
	public String toString() {
		return "Course [id=" + id + ", title=" + title + ", beginDate=" + beginDate + ", endDate=" + endDate + ", fee="
				+ fee + "]";
	}
	  
}
