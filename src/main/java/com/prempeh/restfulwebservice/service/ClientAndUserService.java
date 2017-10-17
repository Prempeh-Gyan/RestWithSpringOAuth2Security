package com.prempeh.restfulwebservice.service;

import java.util.List;

import com.prempeh.restfulwebservice.model.Client;
import com.prempeh.restfulwebservice.model.ClientAndUser;
import com.prempeh.restfulwebservice.model.User;

public interface ClientAndUserService {

	List<ClientAndUser> findByUser(User user);

	List<ClientAndUser> findByClient(Client Client);

	List<ClientAndUser> findByClientAndUser(Client Client, User user);

	ClientAndUser findById(Long userClientId);

	void save(ClientAndUser userClient);

	void delete(ClientAndUser userClient);

}