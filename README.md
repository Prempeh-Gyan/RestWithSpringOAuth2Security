# Securing Your REST API With OAuth2.0

## Quick Start
This section contains details on how to set up and run the application for testing.

### Deploy on Heroku
To deploy this project on Heroku, click the button below:

[![Deploy](https://www.herokucdn.com/deploy/button.png)](https://heroku.com/deploy?template=https://github.com/Prempeh-Gyan/RestWithSpringOAuth2Security)

### Getting Started

*Required*
* [`Maven`](https://maven.apache.org/) 3.3+
* [`JDK`](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) 8+ 

*Optional*
* [`Postman`](https://www.getpostman.com/) - for testing the api endpoint

Get the project from the source repository
>`git clone https://github.com/Prempeh-Gyan/RestWithSpringOAuth2Security.git`

### Running the Project
To run the project, first navigate into the source directory `cd RestWithSpringOAuth2Security` and execute the following command:

* `mvn spring-boot:run:`

The application starts the server instance on port `8080`.
> [`http://localhost:8080`](http://localhost:8080)

### Application Details
The idea behind this project is to demonstrate how to secure API endpoints using Oauth2.0
In this project you will be shown how to define security parameters for your endpoints. You will see an example of an endpoint that is secured and one that is not. Resources that are protected need clients and or users to be authenticated and in some cases authorized before having access.

In this project I tweak the > [`https://github.com/Prempeh-Gyan/WebScraper`](WebScraper API) to secure some of the endpoints with Oauth2.
When you first run the application you will hit the index page where you can enter a `url` and receive a summary of all `urls` defined in anchor tags `<a>` on the provided `url`. 

On the index page the endpoint > [`http://localhost:8080/restfulService/no-security/summarizeLinksOnPage?url=http://www.someActualUrl`](http://localhost:8080/restfulService/no-security/summarizeLinksOnPage?url=http://www.someActualUrl) with which you call the WebScrapingService is not protected hence you receive the summary without having to be authenticated.

On the other hand, should you try to access the same resource through the > [`http://localhost:8080/restfulService/secured/summarizeLinksOnPage?url=http://www.someActualUrl`](http://localhost:8080/restfulService/secured/summarizeLinksOnPage?url=http://www.someActualUrl) endpoint without being authenticated, you will receive an error message to that effect.

The flow of the Authentication and Authorization process is as follows:

* `First A User registers with the Authorization Server to have access to some resources on the Resource Server`

* `Then a Client (could be an application, script, etc) also registers with the Authorization Server to be known as a client that may want to access some resources on the Resource Server either on behalf of a User or by itself, depending on how the Authorization Server and Resource Server are configured`

* `The Client can then request for authorization to access some resources based on a registered user's approval`
* `The url to do is: http://localhost:8080/oauth/token?... the ellipses is for filling in the grant_type as well as other parameters depending on how the servers are configured.`

* `When the authorization goes through, the client receives an access token with which he can then attache to the url of the secured resource to notify the Resource Server that he has been authorized with this access token to view that particular resource. There are other layers of this implementation where an access code is returned before it is exchanged for the access token. But again it all depends on the configurations set up`

* `The Resource Server confirms the authenticity of the access token and then responds accordingly with either the requested resource in the case of a valid access token, or an access denied response in the case of an invalid access token`


For the purposes of this project, the Authorization Server which authenticates users and clients, reside in the same physical machine as the Resource Server which contains resources that may or may not be protected. They could exist as well in independent environments, nonetheless configurations for both servers are provided.

#### Configuring the Authorization Server
The purpose of the Authorization server is to authenticate all users and clients that try to access any protected resource on the Resource server.
file: `src/main/java/com/prempeh/restfulwebservice/config/AuthorizationServerConfig.java`

```java
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

		clients.inMemory().withClient("ClientID").secret("3LERGMQNBRKUVPEU")
				.authorizedGrantTypes("client_credentials", "password")
				.authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT").scopes("read", "write", "trust")
				.resourceIds("oauth2-resource").accessTokenValiditySeconds(5000);
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

```

#### Configuring the Resource Server
The Resource server provides security definitions for all endpoints. This is where you indicate which resources should or should not be protected and with what authority a user can access such resource.
file: `src/main/java/com/prempeh/restfulwebservice/config/ResourceServerConfig.java`

```java
package com.prempeh.restfulwebservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * This Configuration class is for setting up the Resource server which serves
 * resources that are protected by the Oauth2 security mechanism. Note that both
 * the resource server and the authorization server can be on the same physical
 * machine. This class is used to define with resources can be accessed and what
 * authorization level is required for accessing that particular resource
 *
 * @author Prince Prempeh Gyan
 * @version 1.0 <br/>
 *          Date: 17/10/2017
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	/**
	 * The HttpSecurity is for defining the urls that should be protected and need
	 * authentication and authorization for accessing resources from that endpoint.
	 * The configuration below has been set up to allow access to the index page and
	 * the user and client registration pages without the need for the client or
	 * user to be authenticated. Any other request to other urls requires that the
	 * user or client is authenticated and authorized to access that endpoint or
	 * resource.
	 */
	@Override
	public void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
			.antMatchers("/", "/index", "/user-registration", "/client-registration","/restfulService/no-security/summarizeLinksOnPage")
				.permitAll()
				.and()
			.authorizeRequests()
				.anyRequest()
				.fullyAuthenticated()
				.and()
			.formLogin()
				.permitAll()
				.loginPage("/login");
	}

	/**
	 * A custom UserDetailsService requires a user to have an account with the
	 * authorization/resource server. The account details are then persisted into a
	 * database and retrieved later for authentication and authorization purposes.
	 * 
	 * @return this bean returns the custom userDetails implementation created
	 */
	@Autowired
	public void authenticationManager(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Bean
	@Autowired
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
}

```

