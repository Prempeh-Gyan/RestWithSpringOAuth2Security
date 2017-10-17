package com.prempeh.restfulwebservice.service;

import org.springframework.security.oauth2.provider.ClientDetailsService;

import com.prempeh.restfulwebservice.model.Client;

public interface ClientService extends ClientDetailsService {
	
	Client findByClientId(String clientId);

	void save(Client client);
}