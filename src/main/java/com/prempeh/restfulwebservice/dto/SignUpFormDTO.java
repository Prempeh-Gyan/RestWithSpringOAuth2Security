package com.prempeh.restfulwebservice.dto;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.prempeh.restfulwebservice.model.User;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class SignUpFormDTO {

	@NotNull(message = "Firstname cannot be null")
	@NotEmpty(message = "Firstname cannot be Empty")
	@Size(min = 2, max = 30, message = "Firstname is expected to be at least 2 characters")
	private String firstName;

	@NotNull(message = "Lastname cannot be null")
	@NotEmpty(message = "Lastname cannot be Empty")
	@Size(min = 2, max = 30, message = "Lastname is expected to be at least 2 characters")
	private String lastName;

	@NotNull(message = "Email cannot be null")
	@NotEmpty(message = "Email cannot be Empty")
	@Email(message = "Email format is incorrect", regexp = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
	private String email;

	@NotNull(message = "Password cannot be null")
	@NotEmpty(message = "Password cannot be Empty")
	@Size(min = 1, max = 25, message = "Password must be between 6 and 25 characters")
	private String password;

	@NotNull(message = "Confirm Password cannot be null")
	@NotEmpty(message = "Confirm Password cannot be Empty")
	private String confirmPassword;

	@AssertTrue(message = "Passwords do not match")
	public boolean isValid() {
		log.info("Password is : {}  and confirm password is : {}  isValid returns: {}", this.password,
				this.confirmPassword, this.password == null ? false : this.password.equals(this.confirmPassword));
		return this.password == null ? false : this.password.equals(this.confirmPassword);
	}

	public User createUser(User user) {

		user.setEmail(email);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setPassword(new BCryptPasswordEncoder().encode(password));
		user.setIsAccountNonExpired(true);
		user.setIsAccountNonLocked(true);
		user.setIsCredentialsNonExpired(true);
		user.setIsEnabled(true);
		return user;
	}
}
