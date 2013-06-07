package ca.spacek.binnacle.scraper.jsoup;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.money.Money;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ca.spacek.binnacle.scraper.Paging;
import ca.spacek.binnacle.scraper.ListProduct;
import ca.spacek.binnacle.scraper.ProductListContent;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class ProductListContentParser implements ContentParser {
	@Override
	public boolean handles(Document document) {
		return getProductTable(document) != null;
	}

	private Element getProductTable(Document document) {
		return document.select("td.main_table_heading_inner > table").first();
	}

	@Override
	public ProductListContent parse(Document document) {
		Element productTable = getProductTable(document);
		Element header = productTable.select("tbody > tr > td > table:eq(0)").first();
		Elements counts = header.select("td.smallText:eq(0) b");
		
		int totalProducts = Integer.parseInt(counts.get(2).text());
		int perPage = Integer.parseInt(counts.get(1).text()) - Integer.parseInt(counts.get(0).text()) + 1;
		int currentPage = Integer.parseInt(header.select("td.smallText:eq(1) b").first().text());
		
		Paging paging = new Paging(currentPage, perPage, totalProducts);
		
		ImmutableList<ListProduct> products = parseProducts(productTable);
		
		return new ProductListContent(products, paging);
	}

	private ImmutableList<ListProduct> parseProducts(Element productTable) {
		Elements productElements = productTable.select(".productListing-even, .productListing-odd");
		
		List<ListProduct> products = Lists.newArrayList();
		for (Element productLinkElement : productElements) {
			products.add(parseProduct(productLinkElement));
		}
		
		return ImmutableList.copyOf(products);
	}

	private ListProduct parseProduct(Element productElement) {
		Element productLinkElement = productElement.select("font b a").first();
		Element priceElement = productElement.select("td > b").first();
		
		Money price = null;
		Matcher matcher = Pattern.compile(":.(\\w+)\\$(.+).").matcher(priceElement.text());
		if (matcher.find()) {
			price = Money.parse(matcher.group(1) + " " + matcher.group(2));
		}
		
		return new ListProduct(productLinkElement.text(), productLinkElement.attr("href"), null, price);
	}
}
