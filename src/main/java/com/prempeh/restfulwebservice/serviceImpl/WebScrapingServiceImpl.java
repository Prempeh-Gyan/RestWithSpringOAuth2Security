package com.prempeh.restfulwebservice.serviceImpl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.prempeh.restfulwebservice.service.WebScrapingService;

@Service
public class WebScrapingServiceImpl implements WebScrapingService {

	@Override
	public Map<String, Long> getSummaryOfLinksOnPage(String url) throws IOException {

		List<String> linksExtractedOnPage = new ArrayList<>();

		Document webPage = Jsoup.connect(url).get();
		Elements linksOnPage = webPage.select("a[href]");
		linksOnPage.parallelStream().forEach(linkOnPage -> {

			try {

				URI uri = new URI(linkOnPage.attr("abs:href"));

				String link = uri.getHost();

				linksExtractedOnPage.add(link);

			} catch (URISyntaxException e) {

				System.err.println("URISyntaxException : " + "url = " + linkOnPage.attr("abs:href") + "\nMessage = "
						+ e.getMessage());
				System.out.println("URISyntaxException : " + "url = " + linkOnPage.attr("abs:href") + "\nMessage = "
						+ e.getMessage());

			}
		});

		return getSummary(linksExtractedOnPage);
	}

	@Override
	public Map<String, Long> getSummaryOfLinksOnPage() {

		Map<String, Long> dummyData = new HashMap<>();

		dummyData.put("www.SomeDummyUrlForUnAuthenticatedUsers.com", 10l);
		dummyData.put("www.AnotherDummyUrlForUnAthenticatedUsers.com", 2l);

		return dummyData;

	}

	private Map<String, Long> getSummary(List<String> linksOnPage) {

		Map<String, Long> SummaryOfLinksOnPage = linksOnPage.parallelStream()
				.filter(link -> (link != null && link != ""))
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		return SummaryOfLinksOnPage;
	}

}
