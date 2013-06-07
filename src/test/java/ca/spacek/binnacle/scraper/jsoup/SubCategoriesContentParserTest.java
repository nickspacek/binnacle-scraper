package ca.spacek.binnacle.scraper.jsoup;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import ca.spacek.binnacle.scraper.Link;
import ca.spacek.binnacle.scraper.SubCategoryContent;

public class SubCategoriesContentParserTest {
	private static final ImmutableList<Link> EXPECTED_BBQ_LINKS = ImmutableList.<Link> builder()
			.add(new Link("Barbeques - BBQ", "http://ca.binnacle.com/BBQ-Stoves-and-Heaters-Barbeques-BBQ/c7_256/index.html"))
			.add(new Link("Cabin Heaters", "http://ca.binnacle.com/BBQ-Stoves-and-Heaters-Cabin-Heaters/c7_266/index.html"))
			.add(new Link("Portable Freezers and Refrigerators", "http://ca.binnacle.com/BBQ-Stoves-and-Heaters-Portable-Freezers-and-Refrigerators/c7_307/index.html"))
			.add(new Link("Stoves", "http://ca.binnacle.com/BBQ-Stoves-and-Heaters-Stoves-/c7_221/index.html"))
			.add(new Link("BBQ Accessories", "http://ca.binnacle.com/BBQ-Stoves-and-Heaters-BBQ-Accessories/c7_203/index.html"))
			.add(new Link("Propane & Gas Accessories", "http://ca.binnacle.com/BBQ-Stoves-and-Heaters-Propane-&-Gas-Accessories/c7_189/index.html"))
			.add(new Link("Chairs & Tables", "http://ca.binnacle.com/BBQ-Stoves-and-Heaters-Chairs-&-Tables/c7_22/index.html"))
			.add(new Link("Drink Holders and Accessories", "http://ca.binnacle.com/BBQ-Stoves-and-Heaters-Drink-Holders-and-Accessories/c7_155/index.html"))
			.add(new Link("Dinnerware", "http://ca.binnacle.com/BBQ-Stoves-and-Heaters-Dinnerware/c7_113/index.html"))
			.build();
	
	private static final ImmutableList<Link> EXPECTED_CHARTS_LINKS = ImmutableList.<Link> builder()
			.add(new Link("Electronic Charts & Cartridges", "http://ca.binnacle.com/Charts-and-Publications-Electronic-Charts-&-Cartridges/c67_381/index.html"))
			.add(new Link("CHS Raster BSB Charts on CD", "http://ca.binnacle.com/Charts-and-Publications-CHS-Raster-BSB-Charts-on-CD/c67_464/index.html"))
			.add(new Link("Paper Charts", "http://ca.binnacle.com/Charts-and-Publications-Paper-Charts/c67_472/index.html"))
			.add(new Link("Maptech Chart Kits - US", "http://ca.binnacle.com/Charts-and-Publications-Maptech-Chart-Kits-US/c67_79/index.html"))
			.add(new Link("Canadian Marine Publications", "http://ca.binnacle.com/Charts-and-Publications-Canadian-Marine-Publications/c67_376/index.html"))
			.add(new Link("IMO Publications", "http://ca.binnacle.com/Charts-and-Publications-IMO-Publications/c67_461/index.html"))
			.add(new Link("Chart Cases & Tubes", "http://ca.binnacle.com/Charts-and-Publications-Chart-Cases-&-Tubes/c67_378/index.html"))
			.build();
	
	private static final ImmutableList<Link> EXPECTED_ELECTRONIC_CHARTS_LINKS = ImmutableList.<Link> builder()
			.add(new Link("C-Map Charts NT & Max", "http://ca.binnacle.com/Charts-and-Publications-Electronic-Charts-&-Cartridges-C-Map-Charts-NT-&-Max/c67_381_289/index.html"))
			.add(new Link("Garmin Charts", "http://ca.binnacle.com/Charts-and-Publications-Electronic-Charts-&-Cartridges-Garmin-Charts/c67_381_429/index.html"))
			.add(new Link("Navionics cards", "http://ca.binnacle.com/Charts-and-Publications-Electronic-Charts-&-Cartridges-Navionics-cards/c67_381_299/index.html"))
			.add(new Link("Us electronic charts", "http://ca.binnacle.com/Charts-and-Publications-Electronic-Charts-&-Cartridges-Us-electronic-charts/c67_381_440/index.html"))
			.build();
	
	private static final ImmutableList<Link> EXPECTED_SAILBOAT_HARDWARE_LINKS = ImmutableList.<Link> builder()
        	.add(new Link("Blocks", "http://ca.binnacle.com/Sailboat-Hardware-Blocks/c28_425/index.html"))
        	.add(new Link("Rope Clutches & Deck Organizers", "http://ca.binnacle.com/Sailboat-Hardware-Rope-Clutches-&-Deck-Organizers/c28_349/index.html"))
        	.add(new Link("Winches & Winch Handles", "http://ca.binnacle.com/Sailboat-Hardware-Winches-&-Winch-Handles/c28_30/index.html"))
        	.add(new Link("Cable Covers", "http://ca.binnacle.com/Sailboat-Hardware-Cable-Covers/c28_148/index.html"))
        	.add(new Link("Cam Cleats", "http://ca.binnacle.com/Sailboat-Hardware-Cam-Cleats/c28_392/index.html"))
        	.add(new Link("Clamcleats", "http://ca.binnacle.com/Sailboat-Hardware-Clamcleats/c28_128/index.html"))
        	.add(new Link("Clevis Pins", "http://ca.binnacle.com/Sailboat-Hardware-Clevis-Pins/c28_404/index.html"))
        	.add(new Link("Eyestraps & Fairleads", "http://ca.binnacle.com/Sailboat-Hardware-Eyestraps-&-Fairleads/c28_120/index.html"))
        	.add(new Link("Furling & Sail Accessories", "http://ca.binnacle.com/Sailboat-Hardware-Furling-&-Sail-Accessories/c28_132/index.html"))
        	.add(new Link("Hooks", "http://ca.binnacle.com/Sailboat-Hardware-Hooks/c28_212/index.html"))
        	.add(new Link("Mast & Boom Hardware", "http://ca.binnacle.com/Sailboat-Hardware-Mast-&-Boom-Hardware/c28_122/index.html"))
        	.add(new Link("Quick Release Pins", "http://ca.binnacle.com/Sailboat-Hardware-Quick-Release-Pins/c28_302/index.html"))
        	.add(new Link("Quicklinks", "http://ca.binnacle.com/Sailboat-Hardware-Quicklinks/c28_242/index.html"))
        	.add(new Link("Rigid Boom Vangs", "http://ca.binnacle.com/Sailboat-Hardware-Rigid-Boom-Vangs/c28_441/index.html"))
        	.add(new Link("Rings - O D and Split", "http://ca.binnacle.com/Sailboat-Hardware-Rings-O-D-and-Split/c28_239/index.html"))
        	.add(new Link("Rudder Hardware", "http://ca.binnacle.com/Sailboat-Hardware-Rudder-Hardware/c28_251/index.html"))
        	.add(new Link("Sailmakers Tools", "http://ca.binnacle.com/Sailboat-Hardware-Sailmakers-Tools/c28_252/index.html"))
        	.add(new Link("Shackles", "http://ca.binnacle.com/Sailboat-Hardware-Shackles/c28_100/index.html"))
        	.add(new Link("Snap Shackles", "http://ca.binnacle.com/Sailboat-Hardware-Snap-Shackles/c28_99/index.html"))
        	.add(new Link("Snaps & Clips", "http://ca.binnacle.com/Sailboat-Hardware-Snaps-&-Clips/c28_240/index.html"))
        	.add(new Link("Spinnaker Hardware", "http://ca.binnacle.com/Sailboat-Hardware-Spinnaker-Hardware/c28_123/index.html"))
        	.add(new Link("Thimbles & Nico Press Sleeves", "http://ca.binnacle.com/Sailboat-Hardware-Thimbles-&-Nico-Press-Sleeves/c28_352/index.html"))
        	.add(new Link("Tillers & Steering Wheels", "http://ca.binnacle.com/Sailboat-Hardware-Tillers-&-Steering-Wheels/c28_121/index.html"))
        	.add(new Link("Traveler Cars & Track", "http://ca.binnacle.com/Sailboat-Hardware-Traveler-Cars-&-Track/c28_126/index.html"))
        	.add(new Link("Tuff Luff Headstay Systems", "http://ca.binnacle.com/Sailboat-Hardware-Tuff-Luff-Headstay-Systems/c28_495/index.html"))
        	.add(new Link("Turnbuckles & Accessories", "http://ca.binnacle.com/Sailboat-Hardware-Turnbuckles-&-Accessories/c28_147/index.html"))
        	.add(new Link("Whisker Poles & Hardware", "http://ca.binnacle.com/Sailboat-Hardware-Whisker-Poles-&-Hardware/c28_232/index.html"))
        	.build();
	
	private SubCategoriesContentParser parser = new SubCategoriesContentParser();
	
	@Test
	public void wontParseIndexPage() throws IOException {
		assertThat(parser.handles(getDocument("index.html"))).isFalse();
	}
	
	@Test
	public void canParsePageWithSubCategories() throws IOException {
		assertThat(parser.handles(getDocument("category/bbq-category.html"))).isTrue();
	}
	
	@Test
	public void canRetrieveSubCategoriesForBBQ() throws IOException {
		SubCategoryContent content = parser.parse(getDocument("category/bbq-category.html"));
		assertThat(content.getSubCategories())
			.isNotNull()
			.isNotEmpty()
			.isEqualTo(EXPECTED_BBQ_LINKS);
	}
	
	@Test
	public void canRetrieveSubCategoriesForCharts() throws IOException {
		SubCategoryContent content = parser.parse(getDocument("category/charts-category.html"));
		assertThat(content.getSubCategories())
			.isNotNull()
			.isNotEmpty()
			.isEqualTo(EXPECTED_CHARTS_LINKS);
	}
	
	@Test
	public void canRetrieveSubCategoriesForElectronicCharts() throws IOException {
		SubCategoryContent content = parser.parse(getDocument("category/electronic-charts-category.html"));
		assertThat(content.getSubCategories())
			.isNotNull()
			.isNotEmpty()
			.isEqualTo(EXPECTED_ELECTRONIC_CHARTS_LINKS);
	}
	
	@Test
	public void canRetrieveSubCategoriesForSailboatHardware() throws IOException {
		SubCategoryContent content = parser.parse(getDocument("category/sailboat-hardware-category.html"));
		assertThat(content.getSubCategories())
			.isNotNull()
			.isNotEmpty()
			.isEqualTo(EXPECTED_SAILBOAT_HARDWARE_LINKS);
	}
	
	private Document getDocument(String filename) throws IOException {
		return Jsoup.parse(getClass().getClassLoader().getResourceAsStream(filename), "UTF-8", "https://ca.binnacle.com");
	}
}
