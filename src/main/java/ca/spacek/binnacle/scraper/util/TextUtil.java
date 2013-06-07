package ca.spacek.binnacle.scraper.util;

import org.joda.money.Money;

public abstract class TextUtil {
	public static Money toMoney(String price) {
		String[] pieces = price.split("\\$");
		return Money.parse(pieces[0] + " " + pieces[1]);
	}
	
	public static String trimTitle(String title) {
		return title
				.replace(" Binnacle.com", "")
				.replace("``", "\"");
	}
}
