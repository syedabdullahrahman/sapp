package sapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


import sapp.model.Role;
import sapp.repository.GenericRepository;
import sapp.repository.RoleRepository;

@Service
public class RoleServiceImpl extends GenericServiceAdapter<Role, Long> implements RoleService{
	
	private RoleRepository roleRepository;
	
    @Autowired
	public RoleServiceImpl(@Qualifier("roleRepositoryImpl") GenericRepository<Role, Long> genericRepository) {
			super(genericRepository);
			this.roleRepository = (RoleRepository) genericRepository;
	}

	@Override
	public Role findByName(String roleName) {
		roleRepository.findByName(roleName);
		return null;
	}
	

}
