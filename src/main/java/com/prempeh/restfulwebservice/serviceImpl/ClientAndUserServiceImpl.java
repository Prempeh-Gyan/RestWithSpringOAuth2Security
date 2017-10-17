package com.prempeh.restfulwebservice.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prempeh.restfulwebservice.model.Client;
import com.prempeh.restfulwebservice.model.ClientAndUser;
import com.prempeh.restfulwebservice.model.User;
import com.prempeh.restfulwebservice.repository.ClientAndUserRepository;
import com.prempeh.restfulwebservice.service.ClientAndUserService;

@Service
@Transactional
public class ClientAndUserServiceImpl implements ClientAndUserService {

	private final ClientAndUserRepository clientAndUserRepository;

	@Autowired
	public ClientAndUserServiceImpl(ClientAndUserRepository clientAndUserRepository) {
		this.clientAndUserRepository = clientAndUserRepository;
	}

	@Override
	public List<ClientAndUser> findByUser(User user) {
		return clientAndUserRepository.findByUser(user);
	}

	@Override
	public List<ClientAndUser> findByClient(Client Client) {
		return clientAndUserRepository.findByClient(Client);
	}

	@Override
	public ClientAndUser findById(Long ClientAndUserId) {
		return clientAndUserRepository.findOne(ClientAndUserId);
	}

	@Override
	public void save(ClientAndUser ClientAndUser) {
		clientAndUserRepository.save(ClientAndUser);
	}

	@Override
	public void delete(ClientAndUser ClientAndUser) {
		clientAndUserRepository.delete(ClientAndUser);
	}

	@Override
	public List<ClientAndUser> findByClientAndUser(Client Client, User user) {
		return clientAndUserRepository.findByClientAndUser(Client, user);
	}
}