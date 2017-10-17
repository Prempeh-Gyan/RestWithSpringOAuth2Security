/**
 * 
 */
package com.prempeh.restfulwebservice.service;

import java.io.IOException;
import java.util.Map;

public interface WebScrapingService {

	Map<String, Long> getSummaryOfLinksOnPage(String url) throws IOException;

	Map<String, Long> getSummaryOfLinksOnPage();

}
