package de.fhl.haoze.banking;

public class User {
	int ID;
	double balance;
	
	User() {
		ID = 0;
		balance = 10000;
	}
	
	public double withdraw(double amount) {
		if (amount > balance) {
			balance = 0;
		} else {
			balance -= amount;
		}
		return balance;
	}
	
	public double deposit(double amount) {
		balance += amount;
		return balance;
	}
	
}
