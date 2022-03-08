package vendingmachine;

import java.math.BigDecimal;

import bankapi.BankPaymentHandler;

public class PaymentHandler {
	private BankPaymentHandler bank;
	
	public PaymentHandler() {
		this.bank = new BankPaymentHandler();
	}

	
	/**
	 * Handles the payment for a cash customer
	 * 
	 * @param balance the customer's balance
	 * @param productPrice price to deduct from balance
	 * @return true if the balance is sufficient
	 */
	public boolean handleCashPayment(double balance, double productPrice) {
		BigDecimal balanceBD = new BigDecimal(balance);
		BigDecimal productPriceBD = new BigDecimal(productPrice);
		if (balanceBD.compareTo(productPriceBD) < 0)
			return false;
		return true;
	}

	public boolean handleCardPayment(int cardID, double price) {
		return bank.handlePayment(cardID, price);
	}
}
