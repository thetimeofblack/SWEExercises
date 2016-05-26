package de.fhl.haoze.banking;

import java.rmi.Naming;
import java.util.Scanner;


public class Client {
	
	public static void main(String[] args) throws Exception {
		BankingTransfer service = (BankingTransfer) Naming.lookup
				("rmi://" + "localhost" + "/BankingTransfer");
		
		Scanner keyboard = new Scanner(System.in);
		while(true) {
			System.out.println("1- deposit");
			System.out.println("2- withdraw");
			System.out.println("3- quit");
			int input = keyboard.nextInt();
			double balance = 0;
			if (input == 1) {
				double amount = keyboard.nextDouble();
				balance = service.deposit(amount);
				System.out.println("You have $" + balance + " left");
			} else if (input == 2) {
				double amount = keyboard.nextDouble();
				balance = service.withdraw(amount);
				System.out.println("You have $" + balance + " left");
			} else if (input == 3) {
				System.out.println("Bye~");
				break;
			}
		}
		keyboard.close();
	}

}
