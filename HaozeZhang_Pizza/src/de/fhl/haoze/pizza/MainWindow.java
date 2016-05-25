package de.fhl.haoze.pizza;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;
import java.awt.Rectangle;
import java.awt.Color;

public class MainWindow extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	List<JRadioButton> radioButtons = null;
	List<JCheckBox> checkBoxes = null;
	JButton btnOrder = null;
	JLabel lblPrice = null;
	Pizza pizza = null;
	
	MainWindow() {
		setBounds(new Rectangle(0, 23, 450, 300));
		setResizable(false);
		checkBoxes = new ArrayList<JCheckBox>();
		radioButtons = new ArrayList<JRadioButton>();
		initialize();
	}
	
	private void initialize() {
		ImageIcon pizzaIcon = new ImageIcon(getClass().getClassLoader().getResource("pizza-page.png"));
		this.setTitle("Pizza Pricing");
		this.setIconImage(pizzaIcon.getImage());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JPanel iconPanel = new JPanel();
		iconPanel.setBounds(23, 64, 170, 192);
		getContentPane().add(iconPanel);
		
		JLabel lblPizzaicon = new JLabel(pizzaIcon);
		iconPanel.add(lblPizzaicon);
		
		JPanel toppingPanel = new JPanel();
		toppingPanel.setBounds(215, 64, 214, 99);
		getContentPane().add(toppingPanel);
		toppingPanel.setLayout(new GridLayout(4, 2, 0, 0));
		
		JCheckBox chckbxBrocoli = new JCheckBox("brocoli");
		toppingPanel.add(chckbxBrocoli);
		checkBoxes.add(chckbxBrocoli);
		
		JCheckBox chckbxSalami = new JCheckBox("salami");
		toppingPanel.add(chckbxSalami);
		checkBoxes.add(chckbxSalami);
		
		JCheckBox chckbxSalat = new JCheckBox("salat");
		toppingPanel.add(chckbxSalat);
		checkBoxes.add(chckbxSalat);
		
		JCheckBox chckbxSausage = new JCheckBox("sausage");
		toppingPanel.add(chckbxSausage);
		checkBoxes.add(chckbxSausage);
		
		JCheckBox chckbxTomato = new JCheckBox("tomato");
		toppingPanel.add(chckbxTomato);
		checkBoxes.add(chckbxTomato);
		
		JCheckBox chckbxCheese = new JCheckBox("cheese");
		toppingPanel.add(chckbxCheese);
		checkBoxes.add(chckbxCheese);
		
		JPanel sizePanel = new JPanel();
		sizePanel.setBounds(23, 6, 406, 42);
		getContentPane().add(sizePanel);
		
		JRadioButton rdbtnSmall = new JRadioButton("SMALL");
		sizePanel.add(rdbtnSmall);
		radioButtons.add(rdbtnSmall);
		rdbtnSmall.setSelected(true);
		
		JRadioButton rdbtnMedium = new JRadioButton("MEDIUM");
		sizePanel.add(rdbtnMedium);
		radioButtons.add(rdbtnMedium);
		
		JRadioButton rdbtnLarge = new JRadioButton("LARGE");
		sizePanel.add(rdbtnLarge);
		radioButtons.add(rdbtnLarge);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(rdbtnLarge);
		bg.add(rdbtnMedium);
		bg.add(rdbtnSmall);
		
		JPanel controlPanel = new JPanel();
		controlPanel.setBounds(216, 175, 213, 81);
		getContentPane().add(controlPanel);
		controlPanel.setLayout(new GridLayout(2, 2, 0, 0));
		
		lblPrice = new JLabel("Price");
		lblPrice.setHorizontalAlignment(SwingConstants.CENTER);
		controlPanel.add(lblPrice);
		
		btnOrder = new JButton("Order");
		btnOrder.setForeground(Color.ORANGE);
		btnOrder.setBackground(Color.RED);
		lblPrice.setLabelFor(btnOrder);
		controlPanel.add(btnOrder);
		btnOrder.addActionListener(this);
		
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnOrder)) {
			Pizza pizza = null;
			int toppingNumber = 0;
			Pizza.Size size = null;
			
			for (JCheckBox cb: checkBoxes) {
				if (cb.isSelected()) {
					toppingNumber++;
				}
			}
			lblPrice.setText(toppingNumber + " toppings are selected");
			
			for (JRadioButton rb: radioButtons) {
				if (rb.isSelected()) {
					switch (rb.getText()) {
						case "SMALL":
							size = Pizza.Size.SMALL;
							break;
						case "MEDIUM":
							size = Pizza.Size.MEDIUM;
							break;
						case "LARGE":
							size = Pizza.Size.LARGE;
							break;
					}
				}
			}
			pizza = new Pizza(size, toppingNumber);
			lblPrice.setText("The price is: $" + pizza.calculate());
		}
	}

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		MainWindow mainWindow = new MainWindow();
	}
}
