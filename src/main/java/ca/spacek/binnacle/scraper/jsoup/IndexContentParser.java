package ca.spacek.binnacle.scraper.jsoup;

import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ca.spacek.binnacle.scraper.Link;
import ca.spacek.binnacle.scraper.PromotionContent;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class IndexContentParser implements ContentParser {
	private static final String INDEX_TITLE = "The Binnacle Boating Supplies - Canada - Binnacle.com";

	@Override
	public boolean handles(Document document) {
		return document.title().equals(INDEX_TITLE);
	}

	@Override
	public PromotionContent parse(Document document) {
		Element promotionElement = document.select("td.main").first();
		Elements productElements = promotionElement.select("table tr table td[align=center] a.Product_name");
		
		List<Link> productLinks = Lists.newArrayList();
		for (Element productElement : productElements) {
			productLinks.add(new Link(productElement.text(), productElement.attr("href")));
		}
		
		return new PromotionContent(ImmutableList.copyOf(productLinks));
	}
}
