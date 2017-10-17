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
				.authenticated()
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
