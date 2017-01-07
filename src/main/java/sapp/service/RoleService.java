package sapp.service;

import sapp.model.Role;

public interface RoleService extends GenericService<Role, Long>{
	public Role findByName(String roleName);
}
