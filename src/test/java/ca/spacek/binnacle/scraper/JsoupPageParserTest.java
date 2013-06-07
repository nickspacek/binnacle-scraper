package ca.spacek.binnacle.scraper;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class JsoupPageParserTest {
	private static final ImmutableList<Link> EXPECTED_CATEGORY_LINKS = ImmutableList.<Link> builder()
			.add(new Link("BBQ Stoves and Heaters", "http://ca.binnacle.com/BBQ-Stoves-and-Heaters/c7/index.html"))
			.add(new Link("Books | Calendars", "http://ca.binnacle.com/Books-Calendars/c61/index.html"))
			.add(new Link("Cabin Hardware", "http://ca.binnacle.com/Cabin-Hardware/c180/index.html"))
			.add(new Link("Charts and Publications", "http://ca.binnacle.com/Charts-and-Publications/c67/index.html"))
			.add(new Link("Clocks Barometers & Gifts", "http://ca.binnacle.com/Clocks-Barometers-&-Gifts/c1/index.html"))
			.add(new Link("Clothing and Footwear", "http://ca.binnacle.com/Clothing-and-Footwear/c15/index.html"))
			.add(new Link("Deck Hardware", "http://ca.binnacle.com/Deck-Hardware/c117/index.html"))
			.add(new Link("Docking and Mooring", "http://ca.binnacle.com/Docking-and-Mooring/c33/index.html"))
			.add(new Link("Electrical", "http://ca.binnacle.com/Electrical/c58/index.html"))
			.add(new Link("Electronics", "http://ca.binnacle.com/Electronics/c12/index.html"))
			.add(new Link("Gift cards", "http://ca.binnacle.com/Gift-cards/c452/index.html"))
			.add(new Link("Lights", "http://ca.binnacle.com/Lights/c23/index.html"))
			.add(new Link("Maintenance", "http://ca.binnacle.com/Maintenance/c50/index.html"))
			.add(new Link("Navigation", "http://ca.binnacle.com/Navigation/c9/index.html"))
			.add(new Link("One Design", "http://ca.binnacle.com/One-Design/c496/index.html"))
			.add(new Link("Plumbing & Pumps", "http://ca.binnacle.com/Plumbing-&-Pumps/c31/index.html"))
			.add(new Link("Rigging", "http://ca.binnacle.com/Rigging/c409/index.html"))
			.add(new Link("Rope", "http://ca.binnacle.com/Rope/c26/index.html"))
			.add(new Link("Safety", "http://ca.binnacle.com/Safety/c39/index.html"))
			.add(new Link("Sailboat Hardware", "http://ca.binnacle.com/Sailboat-Hardware/c28/index.html"))
			.add(new Link("Ventilation", "http://ca.binnacle.com/Ventilation/c199/index.html"))
			.build();
	
	private JsoupPageParser parser = new JsoupPageParser("https://ca.binnacle.com");

	@Test
	public void test() throws IOException {
		Page page = parser.parse(getResource("index.html"));
		pageAsserts(page);
		assertThat(page.getContent()).isInstanceOf(PromotionContent.class);
		assertThat(((PromotionContent) page.getContent()).getProducts()).isNotEmpty();
	}

	private void pageAsserts(Page page, boolean expectCategories) {
		assertThat(page).isNotNull();
		assertThat(page.getTitle()).isNotNull().isNotEmpty();
		assertThat(page.getTopLinks()).isNotEmpty();
		assertThat(page.getBreadcrumbs()).isNotEmpty();
		assertThat(page.getShoppingCart()).isNotNull();
		assertThat(page.getShoppingCart().getItems()).isNotNull();
		assertThat(page.getAccountOptions()).isNotNull();
		assertThat(page.getContent()).isNotNull();
		if (expectCategories) {
			assertThat(page.getCategoryLinks()).isNotNull().isEqualTo(EXPECTED_CATEGORY_LINKS);
		}
		else {
			assertThat(page.getCategoryLinks()).isNull();
		}
	}
	
	private void pageAsserts(Page page) {
		pageAsserts(page, true);
	}
	
	@Test
	public void test2() throws IOException {
		Page page = parser.parse(getResource("category/bbq-category.html"));
		pageAsserts(page);
	}
	
	@Test
	public void test3() throws IOException {
		Page page = parser.parse(getResource("category/charts-category.html"));
		pageAsserts(page);
	}
	
	@Test
	public void test4() throws IOException {
		Page page = parser.parse(getResource("category/electronic-charts-category.html"));
		pageAsserts(page);
	}
	
	@Test
	public void test5() throws IOException {
		Page page = parser.parse(getResource("category/sailboat-hardware-category.html"));
		pageAsserts(page);
	}
	
	@Test
	public void test6() throws IOException {
		Page page = parser.parse(getResource("products.html"));
		pageAsserts(page);
	}
	
	@Test
	public void test7() throws IOException {
		Page page = parser.parse(getResource("search.html"));
		pageAsserts(page, false);
	}
	
	@Test
	public void test8() throws IOException {
		Page page = parser.parse(getResource("search-2.html"));
		pageAsserts(page, false);
	}

	private InputStream getResource(String filename) {
		return getClass().getClassLoader().getResourceAsStream(filename);
	}
}
