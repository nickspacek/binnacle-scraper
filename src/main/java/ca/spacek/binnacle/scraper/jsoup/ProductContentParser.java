package ca.spacek.binnacle.scraper.jsoup;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.money.Money;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import ca.spacek.binnacle.scraper.Product;
import ca.spacek.binnacle.scraper.ProductContent;
import ca.spacek.binnacle.scraper.ScraperException;
import ca.spacek.binnacle.scraper.util.TextUtil;

public class ProductContentParser implements ContentParser {
	private static final String STRING_REGULAR_PRICE_PREFIX = " ";
	private static final String STRING_SALE_PREFIX = "Sale: ";
	private static final Pattern PATTERN_PRODUCT_NUMBER = Pattern.compile("#:[\\s\u00A0]+(\\S+)");

	@Override
	public boolean handles(Document document) {
		return !document.select("td.products_model_info").isEmpty();
	}

	@Override
	public ProductContent parse(Document document) {
		String name = TextUtil.trimTitle(document.title());

		// This is the tbody
		Element productElement = document.select("table[cellpadding=5] tbody").first();

		// This is a fairly brittle approach to getting the "productHeaderElement"
		// if specials table present
		// tbody -> tr(0) -> td(0) -> table(1)
		// if specials table not present
		// tbody -> tr(0) -> td(0) -> table(0) 
		int productHeaderIndex = document.getElementById("specials-header-container") == null ? 0 : 1;
		Element productHeaderElement = productElement.child(0).child(0).child(productHeaderIndex);

		Matcher matcher = PATTERN_PRODUCT_NUMBER.matcher(productHeaderElement.select("td.products_model_info").text());
		if (!matcher.find()) {
			// TODO come up with a more robust, best-effort strategy and fallback plan
			throw new ScraperException("Every product should have a product number");
		}
		String number = matcher.group(1);
		boolean freeShipping = !productHeaderElement.select("img[alt=Free Shipping]").isEmpty();
		
		Money regularPrice;
		Money salePrice = null;
		Elements productSaleInformation = productHeaderElement.select("td.padding_products_info");
		if (!productSaleInformation.isEmpty()) {
			regularPrice = TextUtil.toMoney(productSaleInformation.get(0).select("s").first().text());
			salePrice = TextUtil.toMoney(productSaleInformation.get(1).text().substring(STRING_SALE_PREFIX.length()));
		}
		else {
			regularPrice = TextUtil.toMoney(productHeaderElement.select("td.pageHeading_product_price b").first().text().substring(STRING_REGULAR_PRICE_PREFIX.length()));
		}
		
		Elements infoBoxElements = productElement.child(1).select("div.shortDescriptionText");
		String shortDescription = ((TextNode) infoBoxElements.get(0).childNode(0)).text().trim();
		String manufacturerNumber = null;
		if (infoBoxElements.size() > 1) {
			manufacturerNumber = infoBoxElements.get(1).select("td").get(1).text();
		}
		String longDescription = document.getElementById("product_info").select("td.main p").text().trim();
		
		Product product = new Product(name, number, shortDescription, longDescription, regularPrice, salePrice, freeShipping, manufacturerNumber);
		return new ProductContent(product);
	}
}
