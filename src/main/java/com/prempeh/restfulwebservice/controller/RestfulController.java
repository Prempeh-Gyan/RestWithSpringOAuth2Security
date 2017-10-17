package com.prempeh.restfulwebservice.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prempeh.restfulwebservice.service.WebScrapingService;

@RestController
@RequestMapping("/restfulService")
public class RestfulController {

	@Autowired
	private WebScrapingService webScrapingService;

	@RequestMapping(value = { "/no-security/summarizeLinksOnPage" }, method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Long> getUnSecuredLinksOnPage(@RequestParam(value = "url") String url) throws IOException {

		return webScrapingService.getSummaryOfLinksOnPage();
	}
	
	@RequestMapping(value = { "/secured/summarizeLinksOnPage" }, method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Long> getSecuredLinksOnPage(@RequestParam(value = "url") String url) throws IOException {

		return webScrapingService.getSummaryOfLinksOnPage(url);
	}
}
