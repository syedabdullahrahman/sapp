package sapp.repository;

import sapp.model.User;

public interface UserRepository extends GenericRepository<User,Long>{
	public User findByUsername(String username);
}
