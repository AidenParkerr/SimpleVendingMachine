package vendingmachine;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import productinventory.Products;

/**
 * This class handles the View part of the Model-View-Controller architecture.
 * This class is responsible for creating and setting up the user interface for
 * the Vending Machine application.
 * 
 * @author Aiden Parker
 * @version 1
 *
 */
public class VMView {
	private CoinAcceptor coinAcceptor;
	private VMController controller;

	private ButtonGroup toggleButtons; // Holds the products JToggleButtons
	private ButtonGroup keypad; // Coin Keypad
	private JTextField balanceTf; // Balance TextField
	private List<Integer> productQTYS; // Holds each of product quantity

	private ImageIcon[] productImages; // Holds each of the product images
	private JTextField[] stockTfs;// Holds each product quantity text-field
	private GridBagConstraints c;

	VMView(VMController controller, CoinAcceptor coinAcceptor) {
		this.controller = controller;
		this.coinAcceptor = coinAcceptor;
		this.toggleButtons = new ButtonGroup();
		this.productQTYS = controller.getStockQTY();
		this.c = new GridBagConstraints();
		this.productImages = new ImageIcon[] { new ImageIcon("./assets//coke.png"),
				new ImageIcon("./assets//tango.png"), new ImageIcon("./assets//lemonade.png"),
				new ImageIcon("./assets//sprite.png"), new ImageIcon("./assets//water.png"),
				new ImageIcon("./assets//pepsi.png") };
	}

	/**
	 * Creates and shows the Graphical User Interface.
	 */
	public void createAndShowGUI() {
		JFrame frame = new JFrame("Vending Machine with a Four Dillabyte Crossfade");
		addComponentsToPane(frame.getContentPane());
		frame.setSize(800, 500);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationByPlatform(true);
		setLookAndFeel();
		frame.setVisible(true);
		frame.pack();
	}

	/**
	 * Sets the look and feel of the program.
	 */
	private void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Constructs the panels to be added to the frame.
	 * 
	 * @param pane the {@code contentpane} of the frame provided
	 */
	private void addComponentsToPane(Container pane) {
		JPanel header = createHeader();
		JPanel body = createBody();
		char copyright = 169; // Copyright symbol
		JLabel signature = new JLabel("Created by Aiden Parker. Copyright " + copyright + " " + 2022);
		signature.setHorizontalAlignment(SwingConstants.CENTER);
		pane.add(header, BorderLayout.NORTH);
		pane.add(body, BorderLayout.CENTER);
		pane.add(signature, BorderLayout.SOUTH);
	}

	/**
	 * Creates the header panel of the user interface with a gradient background.
	 * 
	 * @return {@code JPanel} the populated header panel
	 */
	private JPanel createHeader() {
		JPanel panel = new JPanel() {
			private static final long serialVersionUID = -6335490629016757835L;

			@Override
			protected void paintComponent(Graphics g) {
				// Adds a gradient background to the header panel
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.setPaint(new GradientPaint(0, 0, new Color(0x5180fee), 0, getHeight(), new Color(0x51d7fe)));
				g2d.fillRect(0, 0, getWidth(), getHeight());
			}
		};
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JLabel label = new JLabel("Vending Machine", SwingConstants.CENTER);
		label.setForeground(Color.WHITE);
		label.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		label.setFont(new Font("Helvetica Neue", Font.BOLD, 48));
		panel.add(label);

		label = new JLabel("With a Four Dillabyte Crossfade");
		label.setForeground(new Color(0x2000ff));
		label.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		label.setFont(new Font("Helvetica Neue", Font.BOLD, 20));
		panel.add(label);

		return panel;
	}

	/**
	 * Creates the body panel of the user interface
	 * 
	 * @return {@code JPanel} the populated body panel
	 */
	private JPanel createBody() {
		JPanel panel = new JPanel() {
			private static final long serialVersionUID = 7510917861012513704L;

			@Override
			protected void paintComponent(Graphics g) {
				// Adds a gradient background to the body panel
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				GradientPaint gradientPaint = new GradientPaint(0, 0, new Color(0x51d7fe), 0, getHeight(), Color.white);
				g2d.setPaint(gradientPaint);
				g2d.fillRect(0, 0, getWidth(), getHeight());
			}
		};
		panel.setLayout(new GridBagLayout());
		panel.add(createProductsPanel(), c);
		panel.add(createInputPanel(), c);
		panel.add(coinKeypad(), c);
		panel.add(createStockQTYPanel(), c);

		return panel;
	}

	/**
	 * 
	 * @return {@code JPanel}
	 */
	private JPanel createStockQTYPanel() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Stock Quantity"));
		int i = 0;
		this.stockTfs = new JTextField[6];
		Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 15);
		for (Products product : controller.getProductsWithPrice().keySet()) {
			// For each Product name
			String name = product.toString();
			JLabel label = new JLabel(name + " - ");
			stockTfs[i] = new JTextField(2);
			stockTfs[i].setText(this.productQTYS.get(i).toString());
			stockTfs[i].setEditable(false);
			stockTfs[i].setBorder(null);
			stockTfs[i].setFont(font);
			label.setFont(font);
			panel.add(label);
			panel.add(stockTfs[i]); // Add stock quantity text-field
			i++;
		}

		c.gridx = 0;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy = 0;

		return panel;
	}

	/**
	 * Responsible for creating the panel that holds the products available for
	 * selection alongside the Purchase, Clear, Cancel buttons
	 * 
	 * @return the products panel
	 * @see #controller
	 */
	private JPanel createProductsPanel() {
		JPanel panel = new JPanel();

		panel.setLayout(new GridLayout(3, 3, 5, 5));
		panel.setBorder(BorderFactory.createTitledBorder("Product Selection"));
		int i = 0;
		for (Map.Entry<Products, Double> product : controller.getProductsWithPrice().entrySet()) {
			// For each product entry of Map<ProductName, ProductPrice> in productsWithPrice
			// entry set
			String name = product.getKey().toString();
			String price = String.format("%.2f", product.getValue());
			// ImageIcon icon = scaleImgIcon(this.productImages[i]);
//////////// create toggle button with product image and relevant price
			JToggleButton button = new JToggleButton(name + " : £" + price, this.productImages[i]);
			button.setIconTextGap(0);
			button.setVerticalTextPosition(JToggleButton.BOTTOM);
			button.setHorizontalTextPosition(JToggleButton.CENTER);

			button.addActionListener(controller);
			button.setActionCommand(name);
			toggleButtons.add(button);
			panel.add(button);
			i++;
		}

		String[] buttonNames = new String[] { "PURCHASE", "CLEAR", "CANCEL" };
		for (String name : buttonNames) {
			// Creates a new button with relevant text and adds to panel
			JButton button = new JButton(name);
			button.addActionListener(controller);
			panel.add(button);
		}

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.weightx = 0.8;
		c.insets = new Insets(10, 10, 10, 10);
		c.fill = GridBagConstraints.BOTH;
		return panel;
	}

	/**
	 * Scales an image icon provided to half its original size.
	 * 
	 * @param icon the {@code ImageIcon} to scale
	 * @return scaled {@code ImageIcon}
	 */
//	private ImageIcon scaleImgIcon(ImageIcon icon) {
//		Image image = icon.getImage();
//		Image scaledImg = image.getScaledInstance(icon.getIconWidth() / 2, icon.getIconHeight() / 2,
//				Image.SCALE_SMOOTH);
//		return new ImageIcon(scaledImg);
//	}

	private JPanel createInputPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		balanceTf = new JTextField("£0.00");
		balanceTf.setBorder(BorderFactory.createTitledBorder("Balance"));
		balanceTf.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		balanceTf.setHorizontalAlignment(SwingConstants.CENTER);
		balanceTf.setEditable(false);
		panel.add(balanceTf);

		JButton loyaltyCardBTN = new JButton("Scan Loyalty Card");
		loyaltyCardBTN.addActionListener(controller);
		loyaltyCardBTN.setActionCommand("SCAN-CARD");
		loyaltyCardBTN.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(loyaltyCardBTN);

		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 0.2;
		c.fill = GridBagConstraints.BOTH;

		return panel;
	}

	/**
	 * Creates the Coin Keypad panel populated with the different coins available as
	 * {@code JButtons}. Each button has an anonymous {@code ActionListener} class
	 * to add the coin value to the balance.
	 * 
	 * 
	 * @return the keypad {@code JPanel}
	 * @see CoinAcceptor#depositCoin(double)
	 */
	private JPanel coinKeypad() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Coins"));
		panel.setLayout(new GridLayout(3, 2, 5, 5));
		String[] coinNames = new String[] { "5p", "10p", "20p", "50p", "£1", "£2" };
		double[] coins = new double[] { 0.05, 0.10, 0.20, 0.50, 1, 2 };
		int i = 0;
		this.keypad = new ButtonGroup();
		for (double coin : coins) {
			// for each coin value in coins
			JButton button = new JButton(coinNames[i]); // Set text to relative string value in coinNames
			button.addActionListener(e -> {
				coinAcceptor.depositCoin(coin); // Add the value of the coin clicked to the Coin Acceptor balance.
				balanceTf.setText("£" + String.format("%.2f", coinAcceptor.getBalance())); // Show balance to 2 decimal
																							// place
			});
			this.keypad.add(button);
			panel.add(button);
			i++;
		}

		c.gridx = 0;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy = 2;

		return panel;
	}

	/**
	 * Sets the balance text field to the text provided.
	 * 
	 * @param text the text to display
	 */
	public void setBalanceTFText(String text) {
		this.balanceTf.setText(text);
	}

	/**
	 * Deselects all product toggle buttons and sets the balance text field to 0.
	 */
	public void clearAllFields() {
		this.toggleButtons.clearSelection();
		balanceTf.setText("£0.00");
	}

	/**
	 * Disables each button on the coin keypad.
	 */
	public void disableKeypad() {
		Enumeration<AbstractButton> buttons = this.keypad.getElements();
		while (buttons.hasMoreElements()) {
			// while there are still buttons
			buttons.nextElement().setEnabled(false); // disable button
		}
	}

	/**
	 * Sets the product quantity text fields to the values stored in the list
	 * provided.
	 * 
	 * @param stockQuantity a {@code List<Integer>} of product quantities
	 */
	public void setProductQtyTFs(List<Integer> stockQuantity) {
		int i = 0;
		for (int qty : stockQuantity) {
			// for each quantity in stock quantity
			this.stockTfs[i].setText(String.valueOf(qty)); // Set text of each stock text field to product quantity
			i++;
		}
	}

}
