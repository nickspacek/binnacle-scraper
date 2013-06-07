package ca.spacek.binnacle.scraper;

import lombok.EqualsAndHashCode;
import lombok.experimental.Value;

import com.google.common.collect.ImmutableList;

@Value
@EqualsAndHashCode(callSuper = true)
public class PromotionContent extends Content {
	ImmutableList<Link> products;
}
