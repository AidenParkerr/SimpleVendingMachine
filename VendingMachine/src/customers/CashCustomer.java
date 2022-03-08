package customers;

import vendingmachine.PaymentHandler;

public class CashCustomer extends Customer {
	private PaymentHandler handler;

	public CashCustomer() {
		this.handler = new PaymentHandler();
	}

	@Override
	public boolean makePayment(double balance, double price) {
		return purchaseProduct(balance, price);
	}

	private boolean purchaseProduct(double balance, double productPrice) {
		if (handler.handleCashPayment(balance, productPrice))
			return true;
		else
			return false;

	}

}
