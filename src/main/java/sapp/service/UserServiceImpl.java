package sapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sapp.model.User;
import sapp.repository.GenericRepository;
import sapp.repository.UserRepository;

@Service
public class UserServiceImpl extends GenericServiceAdapter<User, Long> implements UserService{

    private UserRepository userRepository;
    
    @Autowired
	public UserServiceImpl(@Qualifier("userRepositoryImpl") GenericRepository<User, Long> genericRepository) {
			super(genericRepository);
			this.userRepository = (UserRepository) genericRepository;
	}
	
	@Override
	@Transactional
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

}
