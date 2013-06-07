package ca.spacek.binnacle.scraper.jsoup;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;

import org.joda.money.Money;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import ca.spacek.binnacle.scraper.ProductContent;

public class ProductContentParserTest {
	private ProductContentParser parser = new ProductContentParser();
	
	@Test
	public void testClock() throws IOException {
		ProductContent content = parser.parse(getDocument("product/clock.html"));
		assertThat(content).isNotNull();
		assertThat(content.getProduct()).isNotNull();
		assertThat(content.getProduct().getName()).isEqualTo("Victory Brass Clock Porthole Style 4.5\" Dial");
		assertThat(content.getProduct().getNumber()).isEqualTo("10053");
		assertThat(content.getProduct().getShortDescription()).isEqualTo("Hinge bezel - opens porthole style. Matching barometer available. Clock dial diameter is 4-1/2\". Brass case width is 5-1/2\". Weight 2-1/2 lbs.");
		assertThat(content.getProduct().getLongDescription()).isEqualTo("Quartz mechanism. Beautifully hand polished. Solid cast brass case. Wall or Bulkhead mounting. Hinge bezel - opens porthole style. Clock dial diameter is 4-1/2\". Brass case diameter is 5-1/2\". Weight 2-1/2 lbs. Matching barometer available.");
		assertThat(content.getProduct().isFreeShipping()).isTrue();
		assertThat(content.getProduct().getRegularPrice()).isEqualTo(Money.parse("CAD 189.95"));
		assertThat(content.getProduct().getSalePrice()).isEqualTo(Money.parse("CAD 169.95"));
		assertThat(content.getProduct().getManufacturerNumber()).isNull();
	}
	
	@Test
	public void testTether() throws IOException {
		ProductContent content = parser.parse(getDocument("product/tether.html"));
		assertThat(content).isNotNull();
		assertThat(content.getProduct()).isNotNull();
		assertThat(content.getProduct().getName()).isEqualTo("Trem Tether - Elastic 3Ft. To 6Ft.");
		assertThat(content.getProduct().getNumber()).isEqualTo("36436");
		assertThat(content.getProduct().getShortDescription()).isEqualTo("This Trem Tether is elasticized and has a length of 3` to 6`. Two self tailing stainless steel hooks.");
		assertThat(content.getProduct().getLongDescription()).isEqualTo("This Trem Tether is elasticized and has a length of 3ft. to 6ft.. Two self tailing stainless steel hooks. Made using a high-strength polyester belt. CE approved - passed testing under EN 1095 : 1998 standards and certified under PPE Directive 89/68/EEC requirements.");
		assertThat(content.getProduct().isFreeShipping()).isTrue();
		assertThat(content.getProduct().getRegularPrice()).isEqualTo(Money.parse("CAD 55.95"));
		assertThat(content.getProduct().getSalePrice()).isNull();
		assertThat(content.getProduct().getManufacturerNumber()).isNull();
	}
	
	@Test
	public void testBreaker() throws IOException {
		ProductContent content = parser.parse(getDocument("product/breaker.html"));
		assertThat(content).isNotNull();
		assertThat(content.getProduct()).isNotNull();
		assertThat(content.getProduct().getName()).isEqualTo("Blue Sea Ac/Dc Single Pole C Ser. Breaker - White");
		assertThat(content.getProduct().getNumber()).isEqualTo("25117");
		assertThat(content.getProduct().getShortDescription()).isEqualTo("The industry standard circuit breaker for Blue Sea Systems electrical panels.");
		assertThat(content.getProduct().getLongDescription()).isEqualTo("The World Circuit Breaker meets all American Boat and Yacht Council (ABYC) Standards, is UL 1077 Recognized, TUV Certified, CE marked for Europe, and CSA Certified for Canada.");
		assertThat(content.getProduct().isFreeShipping()).isTrue();
		assertThat(content.getProduct().getRegularPrice()).isEqualTo(Money.parse("CAD 19.95"));
		assertThat(content.getProduct().getSalePrice()).isNull();
		assertThat(content.getProduct().getManufacturerNumber()).isEqualTo("7206");
	}
	
	private Document getDocument(String filename) throws IOException {
		return Jsoup.parse(getClass().getClassLoader().getResourceAsStream(filename), "UTF-8", "https://ca.binnacle.com");
	}
}
