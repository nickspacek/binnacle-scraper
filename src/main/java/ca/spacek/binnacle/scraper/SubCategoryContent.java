package ca.spacek.binnacle.scraper;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.Value;

import com.google.common.collect.ImmutableList;

/**
 * Describes a page that contains sub-categories
 * @author Nick Spacek
 *
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class SubCategoryContent extends Content {
	@NonNull
	ImmutableList<Link> subCategories;
}
