package vendingmachine;
import javax.swing.SwingUtilities;

/**
 * A simple program that represents a drinks vending machine using a Java Swing
 * user interface. The user can deposit change, select a drink to purchase, or
 * use a loyalty card to purchase a product.
 * 
 * @author Aiden Parker
 * @version 1
 *
 */
public class VendingMachine {

	/**
	 * Main entry point to the program.
	 * 
	 * @param args an unused {@code String[]} of arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				CoinAcceptor coinAcceptor = new CoinAcceptor();
				VMController controller = new VMController(coinAcceptor);
				VMView view = new VMView(controller, coinAcceptor);
				controller.setView(view);
				view.createAndShowGUI();
			}
		});

	}
}
