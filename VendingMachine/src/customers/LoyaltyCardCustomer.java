package customers;

import bankapi.BankPaymentHandler;

public class LoyaltyCardCustomer extends Customer {
	private LoyaltyCard card;
	private BankPaymentHandler bank;

	public LoyaltyCardCustomer(LoyaltyCard card) {
		this.card = card;
		this.bank = new BankPaymentHandler();
	}

	/**
	 * Returns the balance available for the card ID associated with the customer.
	 * 
	 * @return double the balance available
	 */
	public double getBalance() {
		return bank.getBalance(this.card.getID());
	}

	@Override
	public boolean makePayment(double p, double amount) {
		return this.bank.handlePayment(card.getID(), amount);
	}

}
