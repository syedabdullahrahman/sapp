package sapp.service;

import java.util.List;

import sapp.model.BlogEntry;

public interface BlogEntryService extends GenericService<BlogEntry, Long>{
	
	public List<BlogEntry> findAllDesc();
	
}
