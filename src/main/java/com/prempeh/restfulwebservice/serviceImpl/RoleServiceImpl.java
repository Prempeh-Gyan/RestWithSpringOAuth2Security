package com.prempeh.restfulwebservice.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prempeh.restfulwebservice.model.Role;
import com.prempeh.restfulwebservice.repository.RoleRepository;
import com.prempeh.restfulwebservice.service.RoleService;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

	private final RoleRepository roleRepository;

	@Autowired
	public RoleServiceImpl(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Override
	public void saveRole(Role role) {
		roleRepository.save(role);
	}

	@Override
	public Role findOne(Long roleId) {
		return roleRepository.findOne(roleId);
	}

}
