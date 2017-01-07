package sapp.repository;

import sapp.model.Role;

public interface RoleRepository extends GenericRepository<Role,Long> {
	public Role findByName(String roleName);
}

