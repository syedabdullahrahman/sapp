package sapp.model.course;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CourseHibernateDao implements CourseDao {
	
	private SessionFactory sessionFactory;
	
	@Autowired
	public CourseHibernateDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	
	@Override
	@Transactional
	public void store(Course course) {
		sessionFactory.getCurrentSession().saveOrUpdate(course);
	}
	
	@Override
	@Transactional
	public void delete(Long courseId) {
			Course courseToDel = new Course();
			courseToDel.setId(courseId);
			sessionFactory.getCurrentSession().delete(courseToDel);
	}
	@Override
	@Transactional
	public Course findById(Long courseId) {
		String hql = "from Course where ID=" + courseId;
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
         
        @SuppressWarnings("unchecked")
        List<Course> list = (List<Course>) query.list();
         
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
         
        return null;
	}
	
	@Override
	@Transactional
	public List<Course> findAll() {
		@SuppressWarnings("unchecked")
		List<Course> list = (List<Course>) sessionFactory.getCurrentSession()
                .createCriteria(Course.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        return list;
		
	}
}
