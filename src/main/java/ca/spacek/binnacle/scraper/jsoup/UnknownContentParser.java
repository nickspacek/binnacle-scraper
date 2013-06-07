package ca.spacek.binnacle.scraper.jsoup;

import org.jsoup.nodes.Document;

import ca.spacek.binnacle.scraper.Content;

public class UnknownContentParser implements ContentParser {
	private static final Content EMPTY_CONTENT = new Content() {};

	@Override
	public boolean handles(Document document) {
		return true;
	}

	@Override
	public Content parse(Document document) {
		return EMPTY_CONTENT;
	}
}
