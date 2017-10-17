package com.prempeh.restfulwebservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.jboss.aerogear.security.otp.api.Base32;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(unique = true, nullable = false)
	private String clientId;

	private Boolean isSecretRequired;

	private Boolean isScoped;

	private Integer accessTokenValiditySeconds;

	private Integer refreshTokenValiditySeconds;

	private String compressedResourceIds;

	private String clientSecret = Base32.random();
	
	private String compressedScopes;

	private String compressedAuthorizedGrantTypes;

	private String compressedRegisteredRedirectUris;

	public Client(Client client) {
		this.id = client.getId();
	}

	public boolean isAutoApprove(String scope) {
		return false;
	}
	
	@ManyToOne
	private Role role;
}
