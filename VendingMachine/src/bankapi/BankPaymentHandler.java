package bankapi;

import java.math.BigDecimal;

public class BankPaymentHandler {
	private int[] exisitingCustomers = { 123_456, 456_789 }; // Represents a simple int[] of the different customers
																// the bank may have.
	private BigDecimal balance;

	public BankPaymentHandler() {
		this.balance = new BigDecimal("10");
	}

	public double getBalance(int cardID) {
		if (cardID == this.exisitingCustomers[0])
			return this.balance.doubleValue();
		return 0;
	}

	public boolean handlePayment(int cardID, double price) {
		if (cardID == this.exisitingCustomers[0]) // Statically select customer
			if (this.balance.compareTo(BigDecimal.valueOf(price)) >= 0) {
				balance = balance.subtract(BigDecimal.valueOf(price));
				return true;
			}
		return false; // Insufficient funds or Card not known
	}
}
