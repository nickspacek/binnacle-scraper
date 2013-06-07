package ca.spacek.binnacle.scraper;

import java.io.IOException;
import java.io.InputStream;

public interface PageParser {
	Page parse(InputStream htmlStream) throws IOException;
}
