package ca.spacek.binnacle.scraper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.NoSuchElementException;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ca.spacek.binnacle.scraper.jsoup.ContentParser;
import ca.spacek.binnacle.scraper.jsoup.IndexContentParser;
import ca.spacek.binnacle.scraper.jsoup.ProductContentParser;
import ca.spacek.binnacle.scraper.jsoup.ProductListContentParser;
import ca.spacek.binnacle.scraper.jsoup.SubCategoriesContentParser;
import ca.spacek.binnacle.scraper.jsoup.UnknownContentParser;

import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

@Getter
@Setter
public class JsoupPageParser implements PageParser {
	private static final String ID_TOP_LINKS_MENU = "qm0";
	private static final String QUERY_SIDE_BLOCKS = ".Right_infoBoxHeading .Right_infoBoxHeadingCenter";
	
	private static final String DEFAULT_CHARSET = "UTF-8";
	private static final Content EMPTY_CONTENT = new Content() {};
	
	@NonNull
	private String baseUri;
	
	@NonNull
	private String charsetName = DEFAULT_CHARSET;
	
	@NonNull
	private ImmutableList<ContentParser> parsers = ImmutableList.<ContentParser> builder()
			.add(new IndexContentParser())
			.add(new ProductContentParser())
			.add(new ProductListContentParser())
			.add(new SubCategoriesContentParser())
			.add(new UnknownContentParser())
			.build();
	
	public JsoupPageParser(String baseUri) {
		this.baseUri = baseUri;
	}
	
	@Override
	public Page parse(@NonNull InputStream htmlStream) throws IOException {
		validateProperties();
		
		final Document document = Jsoup.parse(htmlStream, charsetName , baseUri);
		
		// Try to parse HTML as a Binnacle page
		Page page = parseDocument(document);
		
		// See if we have a parser to handle the page's content
		Content content;
		try {
			content = Iterables.find(parsers, new Predicate<ContentParser>() {
				public boolean apply(ContentParser parser) {
					return parser.handles(document);				
				}
			}).parse(document);
		}
		catch (NoSuchElementException e) {
			throw new ScraperException("Couldn't find parser for content", e);
		}
		
		return page.withContent(content);
	}

	private void validateProperties() {
		if (baseUri == null) {
			throw new ScraperException("Must set baseUri for the website");
		}
	}

	private Page parseDocument(Document document) {
		// Check if valid
		validateDocument(document);
		
		String title = document.title();
		ImmutableList<Link> topLinks = parseTopLinks(document);
		AccountOptions accountOptions = parseAccountOptions(document);
		BreadCrumbs breadCrumbs = parseBreadCrumbs(document);
		ImmutableList<Link> categoryLinks = parseCategoryLinks(document);
		
		return new Page(title, topLinks, categoryLinks, breadCrumbs, new ShoppingCart(ImmutableList.<ListProduct> of()), accountOptions, EMPTY_CONTENT);
	}
	
	private ImmutableList<Link> parseCategoryLinks(Document document) {
		List<Link> categoryLinks = Lists.newArrayList();
		
		Elements linkElements = document.select("font a[href~=/c\\d+/]");
		if (linkElements.isEmpty()) {
			return null;
		}
		
		for (Element element : linkElements) {
			categoryLinks.add(new Link(element.text(), element.attr("href")));
		}
		
		return ImmutableList.copyOf(categoryLinks);
	}

	private BreadCrumbs parseBreadCrumbs(Document document) {
		BreadCrumbs breadCrumbs = new BreadCrumbs();
		Element breadCrumbElement = document.select(".breadcrumb_new").first();
		Elements crumbElements = breadCrumbElement.select("div a");
		for (Element crumbElement : crumbElements) {
			breadCrumbs.add(new Link(crumbElement.text(), crumbElement.attr("href")));
		}
		Element currentCrumb = breadCrumbElement.select(".breadcrumb_background2").first();
		if (currentCrumb != null) {
			breadCrumbs.setCurrent(currentCrumb.text());
		}
		return breadCrumbs;
	}

	private AccountOptions parseAccountOptions(Document document) {
		Element accountBlock = document
			.select(QUERY_SIDE_BLOCKS)
			.select(":contains(Your Account)")
			.first();
		
		if (!accountBlock.select("form").isEmpty()) {
			return new LoggedInAccountOptions(null, null, null, null, null, null);
		}
		
		return new LoggedOutAccountOptions();
	}

	private ImmutableList<Link> parseTopLinks(Document document) {
		List<Link> topLinks = Lists.newArrayList();
		
		Elements linkElements = document.getElementById(ID_TOP_LINKS_MENU)
				// filter out weird menu options, like the unclickable Products one
				.select("a[href *= binnacle]");
		for (Element element : linkElements) {
			topLinks.add(new Link(element.text(), element.attr("href")));
		}
		
		return ImmutableList.copyOf(topLinks);
	}

	private void validateDocument(Document document) {
		boolean valid = true;
		if (Strings.isNullOrEmpty(document.title())) {
			valid = false;
		}
		
		if (!valid) {
			throw new ScraperException("Couldn't parse page");
		}
	}
}
