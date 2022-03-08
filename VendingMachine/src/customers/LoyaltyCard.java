package customers;

import lombok.Getter;

/**
 * Represents a loyalty card held by a single customer.
 * 
 * @author Aiden Parker
 *
 */
@Getter
public class LoyaltyCard {
	private final int ID;

	public LoyaltyCard() {
		this.ID = 123_456;
	}
}
