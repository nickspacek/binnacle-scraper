package ca.spacek.binnacle.scraper;

import lombok.Getter;
import lombok.experimental.Value;

@Value
public class Paging {
	int currentPage;
	int perPage;
	int total;
	
	@Getter(lazy = true)
	int lastPage = calculateLastPage();
	
	private int calculateLastPage() {
		return (int) Math.ceil((double) total / (double) perPage);
	}
	
	public int firstNumber() {
		return currentPage * perPage + 1;
	}
	
	public boolean hasNext() {
		return currentPage < getLastPage();
	}
	
	public boolean hasPrev() {
		return currentPage > 0;
	}
	
	public int next() {
		return Math.min(currentPage + 1, getLastPage());
	}
	
	public int prev() {
		return Math.max(0, currentPage - 1);
	}
}
