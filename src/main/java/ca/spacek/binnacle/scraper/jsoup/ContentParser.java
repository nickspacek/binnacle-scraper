package ca.spacek.binnacle.scraper.jsoup;

import org.jsoup.nodes.Document;

import ca.spacek.binnacle.scraper.Content;

public interface ContentParser {
	boolean handles(Document document);
	Content parse(Document document);
}
