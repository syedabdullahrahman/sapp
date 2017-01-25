package sapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import sapp.model.BlogEntry;
import sapp.repository.BlogEntryRepository;
import sapp.repository.GenericRepository;

@Service
public class BlogEntryServiceImpl extends GenericServiceAdapter<BlogEntry, Long> implements BlogEntryService {
    
	private BlogEntryRepository blogEntryRepository;
	
    @Autowired
	public BlogEntryServiceImpl(@Qualifier("blogEntryRepositoryImpl") GenericRepository<BlogEntry, Long> genericRepository) {
			super(genericRepository);
			this.blogEntryRepository = (BlogEntryRepository) genericRepository;
	}
    
	@Override
	public List<BlogEntry> findAllDesc() {
		return blogEntryRepository.findAllDesc();
	}
    
}
