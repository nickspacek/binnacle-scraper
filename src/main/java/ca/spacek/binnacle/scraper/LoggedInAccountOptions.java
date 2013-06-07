package ca.spacek.binnacle.scraper;

import lombok.EqualsAndHashCode;
import lombok.experimental.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class LoggedInAccountOptions extends AccountOptions {
	Link accountOverview;
	Link editAccountInformation;
	Link orderHistory;
	Link editAddressBook;
	Link productNotifications;
	Link logoff;
}
