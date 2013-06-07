package ca.spacek.binnacle.scraper.jsoup;

import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ca.spacek.binnacle.scraper.Link;
import ca.spacek.binnacle.scraper.SubCategoryContent;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class SubCategoriesContentParser implements ContentParser {
	@Override
	public boolean handles(Document document) {
		return !getCategoryElements(document).isEmpty();
	}

	private Elements getCategoryElements(Document document) {
		return document.select("td[width=33%] td.smallText_1 a[href~=/c\\d+(_\\d+)+/]");
	}

	@Override
	public SubCategoryContent parse(Document document) {
		Elements categoryElements = getCategoryElements(document);
		
		List<Link> categoryLinks = Lists.newArrayList();
		for (Element categoryElement : categoryElements) {
			categoryLinks.add(new Link(categoryElement.text(), categoryElement.attr("href")));
		}
		
		return new SubCategoryContent(ImmutableList.copyOf(categoryLinks));
	}
}
