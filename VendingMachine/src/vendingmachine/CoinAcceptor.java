package vendingmachine;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Represents the CoinAcceptor of the Vending Machine with an added coin bin
 * where coins are stored.
 * 
 * @author Aiden Parker
 *
 */
public class CoinAcceptor {
	private BigDecimal balance;

	public CoinAcceptor() {
		this.balance = new BigDecimal(0);
		this.balance.setScale(2, RoundingMode.HALF_EVEN);
	}

	/**
	 * Sets the balance.
	 * 
	 * @param amount the amount to set the balance to.
	 */
	public void setBalance(double amount) {
		this.balance = BigDecimal.valueOf(amount);
	}

	/**
	 * Returns the balance held in the Coin Acceptor.
	 * 
	 * @return the current balance
	 */
	public double getBalance() {
		return this.balance.doubleValue();
	}

	/**
	 * Adds the amount specified to the balance.
	 * 
	 * @param amount the amount to deposit
	 */
	public void depositCoin(double amount) {
		balance = balance.add(BigDecimal.valueOf(amount));
	}

	/**
	 * Subtracts the specified amount from the balance held in the Coin Acceptor.
	 * 
	 * @param amount the amount to subtract from balance
	 */
	public void subtractAmount(double amount) {
		this.balance = this.balance.subtract(BigDecimal.valueOf(amount));
	}
}
