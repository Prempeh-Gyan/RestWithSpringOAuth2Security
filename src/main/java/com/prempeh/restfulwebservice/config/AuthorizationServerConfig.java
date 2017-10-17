package com.prempeh.restfulwebservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

/**
 * This Configuration class is for setting up the Authorization server which
 * will authenticate clients that want to access protected resources from the
 * Resource Server
 * 
 * @author Prince Prempeh Gyan
 * @version 1.0 <br/>
 *          Date: 17/10/2017
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;

	// @Autowired
	// ClientDetailsService clientDetailsService;

	/**
	 * The AuthorizationServerSecurityConfigurer defines the security constrains on
	 * the token endpoint which is the endpoint that serves the protected resource.
	 * In this application the endpoint to be protected is the
	 * "http://localhost:8080/restfulService/summarizeLinksOnPage" url. By setting
	 * the security constraint to "isAuthenticated()" we are ensuring that any
	 * client application that hit this endpoint must be authenticated before
	 * accessing any resource.
	 */
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.checkTokenAccess("isAuthenticated()");
	}

	/**
	 * The ClientDetailsServiceConfigurer defines the client details, it is
	 * important to note that the Authorization server must know about the client
	 * before it tries to access any resource. Normally the client application will
	 * need to create some sort of an account with the Authorization server for this
	 * purpose. But for simplicity sake I will just hard code some values here for
	 * one client so we can test the system. Note also that the details we provide
	 * here for the client will be needed on the client side when we attempt to
	 * retrieve any protected resource on the resource server
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		/*
		 * This is an implementation of a custom ClientDetailsService which is injected
		 * into the ClientDetailsServiceConfigurer, but for now this is out of scope
		 */
		// clients.withClientDetails(clientDetailsService);

		clients.inMemory()
				.withClient("SomeUniqueClientName")
				.secret("3LERGMQNBRKUVPEU")
				.authorizedGrantTypes("client_credentials", "password")
				.authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
				.scopes("read", "write", "trust")
				.resourceIds("oauth2-resource")
				.accessTokenValiditySeconds(5000);
	}

	/**
	 * The AuthorizationServerEndpointsConfigurer defines the authorization needed
	 * for the client to access any resource. Since a custom authentication manager
	 * has been implemented, its just plugged into this and that's it
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager);
	}

}
