package ca.spacek.binnacle.scraper;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class ProductContent extends Content {
	@NonNull
	Product product;
}
