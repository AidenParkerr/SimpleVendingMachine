package productinventory;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Getter;

/**
 * Represents a sensor stored within the product storage section of the vending
 * machine. For simplicity and due to the lack of a physical sensor reading
 * data, each product and its quantity is stored within a {@code LinkedHashMap}
 * to retain its insertion order which is crucial for accurate readings of the
 * relevant product quantity.
 * 
 * @author Aiden Parker
 * @see Inventory
 * @see Products
 */
@Getter
public class InventorySensor {
	private int cokeQTY = 10;
	private int tangoQTY = 10;
	private int spriteQTY = 10;
	private int waterQTY = 10;
	private int lemonadeQTY = 10;
	private int pepsiQTY = 10;
	private Map<Products, Integer> stockQTY;

	public InventorySensor() {
		this.stockQTY = new LinkedHashMap<Products, Integer>(); // LinkedHashMap - Maintains the order keys are inserted
		this.stockQTY.put(Products.COKE, this.cokeQTY);
		this.stockQTY.put(Products.TANGO, this.tangoQTY);
		this.stockQTY.put(Products.LEMONADE, this.lemonadeQTY);
		this.stockQTY.put(Products.SPRITE, this.spriteQTY);
		this.stockQTY.put(Products.WATER, this.waterQTY);
		this.stockQTY.put(Products.PEPSI, this.pepsiQTY);

	}

	/**
	 * Returns a {@code Map} of the {@code Products} name and its quantity.
	 * 
	 * @return {@code Map} of the name and quantity of the product
	 */
	public Map<Products, Integer> getStockQTY() {
		this.stockQTY = new LinkedHashMap<Products, Integer>(); // LinkedHashMap - Maintains the order keys are inserted
		this.stockQTY.put(Products.COKE, this.cokeQTY);
		this.stockQTY.put(Products.TANGO, this.tangoQTY);
		this.stockQTY.put(Products.LEMONADE, this.lemonadeQTY);
		this.stockQTY.put(Products.SPRITE, this.spriteQTY);
		this.stockQTY.put(Products.WATER, this.waterQTY);
		this.stockQTY.put(Products.PEPSI, this.pepsiQTY);
		return this.stockQTY;
	}

	/**
	 * Decrements a {@code Product}'s quantity by one.
	 * 
	 * @param product the {@code Product} to decrement by one
	 */
	public void decrementQTY(Products product) {
		switch (product) {
		case COKE:
			cokeQTY -= 1;
			break;
		case TANGO:
			tangoQTY -= 1;
			break;
		case SPRITE:
			spriteQTY -= 1;
			break;
		case WATER:
			waterQTY -= 1;
			break;
		case LEMONADE:
			lemonadeQTY -= 1;
			break;
		case PEPSI:
			pepsiQTY -= 1;
			break;
		default:
			return;
		}
	}

	/**
	 * Checks if the specified product quantity is greater than or equal to one.
	 * 
	 * @param product the quantity of the {@code Product} to check
	 * @return true if there is at least one available
	 */
	public boolean isAvailable(Products product) {
		switch (product) {
		case COKE:
			return cokeQTY >= 1;
		case TANGO:
			return tangoQTY >= 1;
		case SPRITE:
			return spriteQTY >= 1;
		case WATER:
			return waterQTY >= 1;
		case LEMONADE:
			return lemonadeQTY >= 1;
		case PEPSI:
			return pepsiQTY >= 1;
		default:
			return false; // If the product provided is not known
		}
	}
}
