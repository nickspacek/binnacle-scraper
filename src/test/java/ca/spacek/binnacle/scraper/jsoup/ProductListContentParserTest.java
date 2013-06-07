package ca.spacek.binnacle.scraper.jsoup;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import ca.spacek.binnacle.scraper.ProductListContent;

public class ProductListContentParserTest {
	private ProductListContentParser parser = new ProductListContentParser();

	@Test
	public void test() throws IOException {
		Document document = getDocument("products.html");
		ProductListContent content = parser.parse(document);
		assertThat(content).isNotNull();
		assertThat(content.getPaging()).isNotNull();
		assertThat(content.getPaging().getCurrentPage()).isEqualTo(1);
		assertThat(content.getPaging().getLastPage()).isEqualTo(2);
		assertThat(content.getPaging().getPerPage()).isEqualTo(20);
		assertThat(content.getPaging().getTotal()).isEqualTo(24);
		assertThat(content.getProducts()).isNotEmpty();
	}
	
	@Test
	public void test2() throws IOException {
		Document document = getDocument("search.html");
		ProductListContent content = parser.parse(document);
		assertThat(content).isNotNull();
		assertThat(content.getPaging()).isNotNull();
		assertThat(content.getPaging().getCurrentPage()).isEqualTo(1);
		assertThat(content.getPaging().getLastPage()).isEqualTo(3);
		assertThat(content.getPaging().getPerPage()).isEqualTo(20);
		assertThat(content.getPaging().getTotal()).isEqualTo(42);
		assertThat(content.getProducts()).isNotEmpty();
	}
	
	@Test
	public void testSearch2() throws IOException {
		Document document = getDocument("search-2.html");
		ProductListContent content = parser.parse(document);
		assertThat(content).isNotNull();
		assertThat(content.getPaging()).isNotNull();
		assertThat(content.getPaging().getCurrentPage()).isEqualTo(1);
		assertThat(content.getPaging().getLastPage()).isEqualTo(278);
		assertThat(content.getPaging().getPerPage()).isEqualTo(20);
		assertThat(content.getPaging().getTotal()).isEqualTo(5542);
		assertThat(content.getProducts()).isNotEmpty();
	}
	
	private Document getDocument(String filename) throws IOException {
		return Jsoup.parse(getClass().getClassLoader().getResourceAsStream(filename), "UTF-8", "https://ca.binnacle.com");
	}
}
