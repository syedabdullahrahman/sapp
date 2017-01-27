package sapp.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
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

	@Override
	public long countAll() {
		return (long)currentSession().createCriteria(BlogEntry.class).setProjection(Projections.rowCount()).uniqueResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<BlogEntry> findPaginateDesc(int page, int quantityPerPage) {
		Criteria c = currentSession().createCriteria(BlogEntry.class);
		c.addOrder(Order.desc("id"));
		c.setFirstResult((page-1)*quantityPerPage);
		c.setMaxResults(quantityPerPage);
		return c.list();
	}


}
