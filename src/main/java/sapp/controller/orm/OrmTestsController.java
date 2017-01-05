package sapp.controller.orm;

import java.util.GregorianCalendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import sapp.model.course.Course;
import sapp.model.course.CourseDao;

@Controller
public class OrmTestsController {
	@SuppressWarnings("unused")
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	 
	private CourseDao courseDao;
	@Autowired
	public OrmTestsController(CourseDao courseDao) {
		this.courseDao = courseDao;
	}

	@RequestMapping("/i")
	public String insert() {

		Course course1 = new Course();
		course1.setTitle("Podstawy Javy");
		course1.setBeginDate(new GregorianCalendar(2007, 8, 1).getTime());
		course1.setEndDate(new GregorianCalendar(2007, 9, 1).getTime());
		course1.setFee(1000);
		courseDao.store(course1);
		
		Course course2 = new Course();
		course2.setTitle("Podstawy Springa");
		course2.setBeginDate(new GregorianCalendar(2007, 8, 1).getTime());
		course2.setEndDate(new GregorianCalendar(2007, 9, 1).getTime());
		course2.setFee(1000);
		courseDao.store(course2);
		
		Course course3 = new Course();
		course3.setTitle("Podstawy Hibernate");
		course3.setBeginDate(new GregorianCalendar(2007, 8, 1).getTime());
		course3.setEndDate(new GregorianCalendar(2007, 9, 1).getTime());
		course3.setFee(1000);
		courseDao.store(course3);
		
		System.out.println(course1);
		System.out.println(course2);
		System.out.println(course3);
		System.out.println("============ REV ===========");
		List<Course> courses = courseDao.findAll();
		Long c1 = courses.get(0).getId();
		Long c2 = courses.get(1).getId();
		Long c3 = courses.get(2).getId();
		
		course1 = courseDao.findById(c3);
		course2 = courseDao.findById(c2);
		course3 = courseDao.findById(c1);
		
		System.out.println(course1);
		System.out.println(course2);
		System.out.println(course3);
		
		System.out.println("============ DEL ===========");
		courseDao.delete(c1);

		courses = courseDao.findAll();
		System.out.println(courses.size());
		return "orm";
	}

	@RequestMapping("/r")
	public String result(Model model) {

		return "orm";
	}
	
}