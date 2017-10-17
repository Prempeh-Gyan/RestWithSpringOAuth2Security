package com.prempeh.restfulwebservice.service;

import com.prempeh.restfulwebservice.model.CustomGrantedAuthority;


public interface PrivilegeService {

	CustomGrantedAuthority fineOneByName(String name);

	void savePrivilege(CustomGrantedAuthority privilege);
}
