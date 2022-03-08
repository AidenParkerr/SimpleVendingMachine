package vendingmachine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import javax.swing.JOptionPane;

import customers.CashCustomer;
import customers.Customer;
import customers.LoyaltyCard;
import customers.LoyaltyCardCustomer;
import lombok.Setter;
import productinventory.Inventory;
import productinventory.InventorySensor;
import productinventory.Products;

/**
 * The class handles the Controller part of the Model-View-Controller
 * architecture. This class is responsible for acting as a "rule them all"
 * approach to a system controller. While this is not the best practice approach
 * to achieving the results needed, it was the simplest at the time to a
 * solution to the problem provided.
 * 
 * Handles the {@code Actions}'s performed by each of the buttons within the
 * {@code VMView} class and carries out the necessary actions.
 * 
 * The Customer is assumed to be a {@code CashCustomer} upon first launch, once
 * the {@code LoyaltyCard} has been presented, the customer is changed to a
 * {@code LoyaltyCardCustomer}
 * 
 * Implements the {@code ActionListener} interface to handle
 * {@code ActionEvent}'s
 * 
 * @author aiden
 * @see ActionListener
 * @see VMView
 * @see CoinAcceptor
 * @see Inventory
 * @see InventorySensor
 * @see Customer
 * @see Products
 */
public class VMController implements ActionListener {
	@Setter
	private VMView view;
	private CoinAcceptor coinAcceptor;
	private InventorySensor sensor;
	private Inventory stock;
	private Customer customer;
	private Products product;
	private boolean cardScanned;

	public VMController(CoinAcceptor coinAcceptor) {
		this.coinAcceptor = coinAcceptor;
		this.sensor = new InventorySensor();
		this.stock = new Inventory();
		this.customer = new CashCustomer();
		this.cardScanned = false;
	}

	
	/**
	 * Returns an {@code EnumMap} of the {@code Products} 
	 * @return
	 */
	public EnumMap<Products, Double> getProductsWithPrice() {
		return stock.getProducts();
	}

	public List<Integer> getStockQTY() {
		return new ArrayList<Integer>(this.sensor.getStockQTY().values());
	}

	/**
	 * Applies a 10% discount to all products for Loyalty card customers.
	 */
	private void applyDiscount() {
		double[] newPrices = new double[Products.values().length];
		int i = 0;
		for (double price : stock.getAllProductPrices()) {
			newPrices[i] = price - price / 10;
			i++;
		}
		stock.updateProductPrice(newPrices);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		switch (action) {
		case "COKE":
			product = Products.COKE;
			break;
		case "LEMONADE":
			product = Products.LEMONADE;
			break;
		case "TANGO":
			product = Products.TANGO;
			break;
		case "WATER":
			product = Products.WATER;
			break;
		case "PEPSI":
			product = Products.PEPSI;
			break;
		case "SPRITE":
			product = Products.SPRITE;
			break;
		case "PURCHASE":
			if (product == null) {
				JOptionPane.showMessageDialog(null, "Choose a product to purchase");
				return;
			}
			if (!sensor.isAvailable(product)) {
				JOptionPane.showMessageDialog(null, "Product Not Available\nChoose another product");
				return;
			}
			if (customer instanceof LoyaltyCardCustomer) {
				// Loyalty Card Customer
				double balance = ((LoyaltyCardCustomer) customer).getBalance();
				if (customer.makePayment(balance, stock.getPrice(product)))
					handleSuccessfulPurchase(((LoyaltyCardCustomer) customer).getBalance());
				else
					JOptionPane.showMessageDialog(null, "Balance is insufficient for product purchase.",
							"Insufficient Funds", JOptionPane.ERROR_MESSAGE);
			} else {
				// Cash Customer
				if (customer.makePayment(coinAcceptor.getBalance(), stock.getPrice(product))) {
					this.coinAcceptor.subtractAmount(stock.getPrice(this.product));
  					handleSuccessfulPurchase(coinAcceptor.getBalance());
				} else
					JOptionPane.showMessageDialog(null, "Balance is insufficient for product purchase.",
							"Insufficient Funds", JOptionPane.ERROR_MESSAGE);
			}
			break;
		case "CANCEL":
			cancelAction();
			break;
		case "CLEAR":
			// Resets all fields and balance of Coin Acceptor.
			view.clearAllFields();
			coinAcceptor.setBalance(0);
			this.product = null;
			JOptionPane.showMessageDialog(null, "All fields cleared.");
			break;
		case "SCAN-CARD":
			// Set customer to a LoyaltyCardCustomer when card is scanned.
			if (!this.cardScanned) {
				this.customer = new LoyaltyCardCustomer(new LoyaltyCard());
				this.cardScanned = true;
				double balance = ((LoyaltyCardCustomer) customer).getBalance();
				view.setBalanceTFText("£" + String.format("%.2f", balance));
				view.disableKeypad();
				applyDiscount();
				JOptionPane.showMessageDialog(null, "10% discount applied to products");
			}
			break;
		default:
			System.out.println("Action command not defined - \"" + action + "\"");
			break;
		}
	}

	private void cancelAction() {
		double balance = coinAcceptor.getBalance();
		if (balance > 0)
			JOptionPane.showMessageDialog(null,
					"Order Cancelled.\nChange dispensed.\n£" + String.format("%.2f", balance));
		else
			JOptionPane.showMessageDialog(null, "Order Cancelled.");
		System.exit(0);
	}

	/**
	 * Updates the required fields upon successful purchase of a product.
	 * 
	 * Decrements the product chosen by 1, Sets the balance to the amount provided,
	 * updates the quantity labels to the new stock level and displays a "Purchase
	 * Completed" message.
	 * 
	 * @param balance the amount to set the balance {@code JTextField}
	 * @see VMView#setBalanceTFText(String)
	 * @see VMView#setProductQtyTFs(List)
	 */
	private void handleSuccessfulPurchase(double balance) {
		this.sensor.decrementQTY(this.product);
		this.view.setBalanceTFText("£" + String.format("%.2f", balance));
		this.view.setProductQtyTFs(List.copyOf(this.sensor.getStockQTY().values()));
		JOptionPane.showMessageDialog(null, "Purchase Completed - " + this.product);
	}
}
