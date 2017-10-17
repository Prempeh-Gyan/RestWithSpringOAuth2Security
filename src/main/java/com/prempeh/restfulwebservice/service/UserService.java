package com.prempeh.restfulwebservice.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.prempeh.restfulwebservice.model.User;

public interface UserService extends UserDetailsService {

	void saveUser(User user);

	User findOne(long id);

	User findByEmail(String email);

	void delete(User user);
}