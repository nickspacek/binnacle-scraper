package ca.spacek.binnacle.scraper;

import java.util.LinkedList;

import lombok.Getter;
import lombok.Setter;

public class BreadCrumbs extends LinkedList<Link> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7691163015949958164L;
	
	@Getter
	@Setter
	String current;
}
