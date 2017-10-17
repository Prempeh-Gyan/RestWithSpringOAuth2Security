package com.prempeh.restfulwebservice.serviceImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prempeh.restfulwebservice.model.Client;
import com.prempeh.restfulwebservice.model.CustomGrantedAuthority;
import com.prempeh.restfulwebservice.repository.ClientRepository;
import com.prempeh.restfulwebservice.service.ClientService;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

	private final ClientRepository clientRepository;

	@Autowired
	public ClientServiceImpl(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {

		Client client = clientRepository.findByClientId(clientId);

		if (client == null) {
			throw new UsernameNotFoundException("Could not find client with id " + clientId);
		} else {
			return new CustomClientDetails(client);
		}
	}

	private final static class CustomClientDetails extends Client implements ClientDetails {

		private static final long serialVersionUID = 8250904223326796879L;

		public CustomClientDetails(Client client) {
			super(client);
		}

		@Override
		public Set<String> getResourceIds() {

			return extractedElementsOfThisString(super.getCompressedResourceIds());
		}

		@Override
		public boolean isSecretRequired() {
			return super.getIsSecretRequired();
		}

		@Override
		public String getClientSecret() {
			return super.getClientSecret();
		}

		@Override
		public boolean isScoped() {
			return super.getIsScoped();
		}

		@Override
		public Set<String> getScope() {

			return extractedElementsOfThisString(super.getCompressedScopes());
		}

		@Override
		public Set<String> getAuthorizedGrantTypes() {

			return extractedElementsOfThisString(super.getCompressedAuthorizedGrantTypes());
		}

		@Override
		public Set<String> getRegisteredRedirectUri() {

			return extractedElementsOfThisString(super.getCompressedRegisteredRedirectUris());
		}

		@Override
		public Collection<GrantedAuthority> getAuthorities() {

			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

			Set<CustomGrantedAuthority> privileges = new HashSet<>(super.getRole().getPrivileges());

			privileges.forEach((privilege -> {
				authorities.add(new SimpleGrantedAuthority("ROLE_" + privilege.getName()));
			}));

			return authorities;
		}

		@Override
		public Integer getAccessTokenValiditySeconds() {
			return super.getAccessTokenValiditySeconds();
		}

		@Override
		public Integer getRefreshTokenValiditySeconds() {
			return super.getRefreshTokenValiditySeconds();
		}

		@Override
		public boolean isAutoApprove(String scope) {
			return super.isAutoApprove(scope);
		}

		private Set<String> extractedElementsOfThisString(String data) {

			Set<String> setOfElements = new HashSet<>();

			String compressedData = data.replaceAll("\\s+", "");

			StringTokenizer stringTokenizer = new StringTokenizer(compressedData, "#");

			while (stringTokenizer.hasMoreTokens()) {
				String currentToken = stringTokenizer.nextToken().toLowerCase();
				setOfElements.add(currentToken);
			}

			return setOfElements;
		}

		@Override
		public Map<String, Object> getAdditionalInformation() {
			return null;
		}
	}

	@Override
	public Client findByClientId(String clientId) {
		return clientRepository.findByClientId(clientId);
	}

	@Override
	public void save(Client client) {
		clientRepository.save(client);
	}

}
