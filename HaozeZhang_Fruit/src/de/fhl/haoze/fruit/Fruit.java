package de.fhl.haoze.fruit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Fruit {
	public static void main(String[] args) {
		JFrame testFrame = new JFrame();
		testFrame.setSize(200, 150);
		testFrame.setTitle("TestFrame");
		testFrame.setVisible(true);
		
		JCheckBox cb0 = new JCheckBox("Apple");
		JCheckBox cb1 = new JCheckBox("Banana");
		JCheckBox cb2 = new JCheckBox("Pineapple");
		JCheckBox cb3 = new JCheckBox("Pear");
		JButton b = new JButton("Confirm");
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		testFrame.add(cb0);
		testFrame.add(cb1);
		testFrame.add(cb2);
		testFrame.add(cb3);
		testFrame.add(b);	
		
	}


}
