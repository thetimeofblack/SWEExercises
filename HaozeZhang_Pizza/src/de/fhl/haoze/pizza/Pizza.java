package de.fhl.haoze.pizza;

import java.util.ArrayList;
import java.util.List;

public class Pizza {
	public static enum Topping {
		CHEESE, SALAT, SALAMI, SAUSAGE, BROCOLI, TOMATO
	}
	
	public static enum Size {
		SMALL, MEDIUM, LARGE
	}
	
	double price = 0;
	Size size = null;
	List<Topping> toppings = null;
	int toppingNumber = 0;
	
	Pizza() {
		this.toppings = new ArrayList<Topping>();
	}
	
	Pizza(Size size) {
		this.toppings = new ArrayList<Topping>();
		this.size = size;
	}
	
	Pizza(Size size, List<Topping> toppings) {
		this.toppings = toppings;
		this.toppingNumber = toppings.size();
		this.size = size;
	}
	
	Pizza(Size size, int toppingNumber) {
		this.toppingNumber = toppingNumber;
		this.size = size;
	}
	
	void setSize(Size size) {
		this.size = size;
	}
	
	void setToppingNumber(int toppingNumber) {
		this.toppingNumber = toppingNumber;
	}
	
	double calculate() {
		double price = 0;
		double toppingPrice = 0;
		switch(size) {
			case SMALL:
				price += 4;
				toppingPrice = 0.75;
				break;
			case MEDIUM:
				price += 5.5;
				toppingPrice = 1;
				break;
			case LARGE:
				price += 7.00;
				toppingPrice = 1.45;
				break;
		}
		price += toppingNumber * toppingPrice;
		return price;
	}
	
	void addTopping(Topping topping) {
		toppings.add(topping);
	}
}
