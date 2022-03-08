package productinventory;

import java.util.Collection;
import java.util.EnumMap;

import lombok.Getter;

@Getter
public class Inventory {
	private EnumMap<Products, Double> products = new EnumMap<Products, Double>(Products.class);

	public Inventory() {
		products.put(Products.COKE, 1.50);
		products.put(Products.SPRITE, 1.20);
		products.put(Products.TANGO, 1.40);
		products.put(Products.PEPSI, 1.30);
		products.put(Products.WATER, 1.0);
		products.put(Products.LEMONADE, 1.20);
	}

	public double getPrice(Products product) {
		return this.products.get(product);
	}
	
	public Collection<Double> getAllProductPrices() {
		return this.products.values();
	}
	
	public void updateProductPrice(double[] prices) {
		int i = 0;
		for (Products prod : this.products.keySet()) {
			this.products.replace(prod, prices[i]);
			i +=1;
		}
	}
}
