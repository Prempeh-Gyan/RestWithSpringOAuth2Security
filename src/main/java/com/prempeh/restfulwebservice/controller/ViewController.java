package com.prempeh.restfulwebservice.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.prempeh.restfulwebservice.service.WebScrapingService;

@Controller
public class ViewController {

	@Autowired
	private WebScrapingService webScrapingService;

	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	@RequestMapping(value = { "/index" }, method = RequestMethod.GET)
	public String getIndexPage() {
		return "index";
	}

	@RequestMapping(value = { "/index" }, method = RequestMethod.POST)
	public String postIndexPage(@RequestParam(value = "url") String url, Model model) throws IOException {
		model.addAttribute("pageSummary", webScrapingService.getSummaryOfLinksOnPage(url));
		return "index";
	}
}
