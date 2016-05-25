package de.fhl.haoze.pizza;

import org.junit.Assert;
import junit.framework.TestCase;

public class WindowTest extends TestCase {
	MainWindow frame;	
	protected void setUp() throws Exception {
		super.setUp();
		frame= new MainWindow(); // Launch the GUI
	}
	
	public void testMain() {
		// While GUI is showing
		while (frame.isDisplayable()) {
			try {
				Thread.sleep(100);
				Assert.assertEquals(true,true);
				break;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void testButton() {
		frame.btnOrder.doClick();
		assertNotSame(frame.lblPrice.getText(), "Price");
	}
	
	public void testCheckBox() {
		frame.checkBoxes.get(0).setSelected(true);
		frame.btnOrder.doClick();
		assertEquals(frame.pizza.price, 4.75);
	}
	
	public void testRadioButton() {
		frame.radioButtons.get(1).setSelected(true);
		frame.btnOrder.doClick();
		assertEquals(frame.pizza.price, 5.5);
	}
}
