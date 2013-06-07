package ca.spacek.binnacle.scraper;

public class ScraperException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6032922995196423224L;

	public ScraperException() {
		super();
	}

	public ScraperException(String message, Throwable cause) {
		super(message, cause);
	}

	public ScraperException(String message) {
		super(message);
	}

	public ScraperException(Throwable cause) {
		super(cause);
	}
}
