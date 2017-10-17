package com.prempeh.restfulwebservice.service;

import com.prempeh.restfulwebservice.model.Role;


public interface RoleService {

	Role findOne(Long roleId);

	void saveRole(Role role);
}
