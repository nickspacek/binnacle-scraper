package ca.spacek.binnacle.scraper;

import lombok.NonNull;
import lombok.experimental.Value;

import com.google.common.collect.ImmutableList;

@Value
public class Page {
	@NonNull
	String title;
	
	@NonNull
	ImmutableList<Link> topLinks;
	
	ImmutableList<Link> categoryLinks;
	
	@NonNull
	BreadCrumbs breadcrumbs;
	
	@NonNull
	ShoppingCart shoppingCart;
	
	@NonNull
	AccountOptions accountOptions;

	@NonNull
	Content content;
}
