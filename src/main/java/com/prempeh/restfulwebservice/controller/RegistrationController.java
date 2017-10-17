package com.prempeh.restfulwebservice.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.prempeh.restfulwebservice.dto.ClientSignUpFormDTO;
import com.prempeh.restfulwebservice.dto.SignUpFormDTO;
import com.prempeh.restfulwebservice.model.Client;
import com.prempeh.restfulwebservice.model.User;
import com.prempeh.restfulwebservice.service.ClientService;
import com.prempeh.restfulwebservice.service.RoleService;
import com.prempeh.restfulwebservice.service.UserService;
import com.prempeh.restfulwebservice.service.WebScrapingService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor(onConstructor = @__({ @Autowired }))
public class RegistrationController {

	private final @NotNull WebScrapingService webScrapingService;

	private final @NotNull UserService userService;

	private final @NotNull RoleService roleService;

	private final @NotNull ClientService clientService;

	@RequestMapping(value = { "/user-registration" }, method = RequestMethod.GET)
	public String getUserRegistrationForm(Model model) {
		model.addAttribute("signUPFormDTO", new SignUpFormDTO());
		return "userregistration";
	}

	@RequestMapping(value = { "/user-registration" }, method = RequestMethod.POST)
	public String postUserRegistrationForm(@Valid @ModelAttribute("signUPFormDTO") SignUpFormDTO signUpFormDTO,
			BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("signUPFormDTO", signUpFormDTO);
			return "userregistration";
		}

		User user = signUpFormDTO.createUser(new User());

		if (isDuplicateMember(user, userService.findByEmail(user.getEmail()))) {
			model.addAttribute("signUPFormDTO", signUpFormDTO);
			model.addAttribute("duplicate", "User already exist!");
			return "userregistration";
		}

		long roleId = 2l;

		user.setRole(roleService.findOne(roleId));

		userService.saveUser(user);

		redirectAttributes.addFlashAttribute("success", "Success! User created successfully!");

		return "redirect:/user-registration";
	}

	@RequestMapping(value = { "/client-registration" }, method = RequestMethod.GET)
	public String getClientRegistrationForm(Model model) {
		model.addAttribute("clientSignUpFormDTO", new ClientSignUpFormDTO());
		return "clientregistration";
	}

	@RequestMapping(value = { "/client-registration" }, method = RequestMethod.POST)
	public String postClientRegistrationForm(
			@Valid @ModelAttribute("clientSignUpFormDTO") ClientSignUpFormDTO clientSignUpFormDTO,
			BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("clientSignUpFormDTO", clientSignUpFormDTO);
			return "clientregistration";
		}

		Client client = clientSignUpFormDTO.createClient(new Client());

		if (isDuplicateClient(client, clientService.findByClientId(client.getClientId()))) {
			model.addAttribute("clientSignUpFormDTO", clientSignUpFormDTO);
			model.addAttribute("duplicate", "ClientId already exist!");
			return "clientregistration";
		}

		long roleId = 4l;

		client.setRole(roleService.findOne(roleId));

		clientService.save(client);

		redirectAttributes.addFlashAttribute("success", "Success! Client created successfully");

		return "redirect:/client-registration";
	}

	private boolean isDuplicateClient(Client client, Client dbClient) {
		if (client != null && dbClient != null) {
			if (client.getClientId().equalsIgnoreCase(dbClient.getClientId())) {
				return true;
			}
		}
		return false;
	}

	private boolean isDuplicateMember(User user, User dbUser) {
		if (user != null && dbUser != null) {
			if (user.getFirstName().equalsIgnoreCase(dbUser.getFirstName())
					&& user.getLastName().equalsIgnoreCase(dbUser.getLastName())
					&& user.getEmail().equalsIgnoreCase(dbUser.getEmail())) {
				return true;
			}
		}
		return false;
	}
}
