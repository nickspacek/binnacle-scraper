package ca.spacek.binnacle.scraper;

import com.google.common.collect.ImmutableList;

import lombok.EqualsAndHashCode;
import lombok.experimental.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class ProductListContent extends Content {
	ImmutableList<ListProduct> products;
	Paging paging;
}
