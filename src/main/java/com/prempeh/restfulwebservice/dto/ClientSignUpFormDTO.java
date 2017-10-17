package com.prempeh.restfulwebservice.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.prempeh.restfulwebservice.model.Client;

import lombok.Data;

@Data
public class ClientSignUpFormDTO {

	@NotNull(message = "ClientId cannot be null")
	@NotEmpty(message = "ClientId cannot be Empty")
	@Size(min = 2, max = 30, message = "ClientId is expected to be at least 2 characters")
	private String clientId;


	public Client createClient(Client client) {
		client.setClientId(clientId);
		client.setCompressedAuthorizedGrantTypes("client_credentials # password");
		client.setCompressedScopes("read # write # trust");
		client.setCompressedResourceIds("oauth2-resource");
		client.setIsSecretRequired(true);
		client.setIsScoped(true);	
		client.setAccessTokenValiditySeconds(5000);	
		return client;
	}
}
