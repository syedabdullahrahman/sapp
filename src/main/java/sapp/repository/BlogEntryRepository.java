package sapp.repository;

import java.util.List;

import sapp.model.BlogEntry;

public interface BlogEntryRepository extends GenericRepository<BlogEntry,Long>{
	
	public List<BlogEntry> findAllDesc() ;
	
}
