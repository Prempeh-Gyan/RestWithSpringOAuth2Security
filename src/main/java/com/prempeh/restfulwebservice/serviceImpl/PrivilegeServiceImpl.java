package com.prempeh.restfulwebservice.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prempeh.restfulwebservice.model.CustomGrantedAuthority;
import com.prempeh.restfulwebservice.repository.PrivilegeRepository;
import com.prempeh.restfulwebservice.service.PrivilegeService;

@Service
@Transactional
public class PrivilegeServiceImpl implements PrivilegeService {

	private final PrivilegeRepository privilegeRepository;

	@Autowired
	public PrivilegeServiceImpl(PrivilegeRepository privilegeRepository) {
		this.privilegeRepository = privilegeRepository;
	}

	@Override
	public void savePrivilege(CustomGrantedAuthority privilege) {
		privilegeRepository.save(privilege);
	}

	@Override
	public CustomGrantedAuthority fineOneByName(String name) {
		return privilegeRepository.fineOneByName(name);
	}
}
