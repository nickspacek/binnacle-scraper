package ca.spacek.binnacle.scraper;

import org.joda.money.Money;

import lombok.NonNull;
import lombok.experimental.Value;

@Value
public class ListProduct {
	@NonNull
	String name;
	@NonNull
	String url;
	String imageUrl;
	Money price;
}
