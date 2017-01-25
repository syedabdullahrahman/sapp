package sapp.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import sapp.model.BlogEntry;

@Repository
@Transactional
public class BlogEntryRepositoryImpl extends GenericRepositoryAdapter<BlogEntry, Long> implements BlogEntryRepository {

	
	@Override
	@SuppressWarnings("unchecked")
    public List<BlogEntry> findAllDesc() {
		Criteria c = currentSession().createCriteria(BlogEntry.class);
		c.addOrder(Order.desc("id"));
		return c.list();
    }
	
}
