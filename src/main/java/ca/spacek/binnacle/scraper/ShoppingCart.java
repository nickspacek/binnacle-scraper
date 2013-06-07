package ca.spacek.binnacle.scraper;

import lombok.NonNull;
import lombok.experimental.Value;

import com.google.common.collect.ImmutableList;

@Value
public class ShoppingCart {
	@NonNull
	ImmutableList<ListProduct> items;
}
