package ca.spacek.binnacle.scraper;

import lombok.NonNull;
import lombok.experimental.Value;

import org.joda.money.Money;

@Value
public class Product {
	@NonNull
	String name;
	@NonNull
	String number;
	@NonNull
	String shortDescription;
	@NonNull
	String longDescription;
	@NonNull
	Money regularPrice;
	Money salePrice;
	boolean freeShipping;
	String manufacturerNumber;
}
