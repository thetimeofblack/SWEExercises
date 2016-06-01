package de.fhl.haoze.concertbooking;

import java.rmi.Naming;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * @author David
 * @version 2016-06-01
 * Class ConcertClient
 * Client which call remote server to achieve concert reservation
 */
public class ConcertClient {
	static String customerName;
	
	public static void main(String[] args) throws Exception {
		System.out.println("Client started");
		IConcert service = (IConcert) Naming.lookup("rmi://" + "localhost" + "/ConcertBooking");
		System.out.println("Service looked up");
		
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Your name please:");
		customerName = keyboard.nextLine();
		
		while(true) {
			System.out.println("What do you want to do?");
			System.out.println("Please follow syntax rules if provided");
			System.out.println("1- book");
			System.out.println("2- cancel");
			System.out.println("3- quit");
			
			int input = 0xff;
			try {input = Integer.parseInt(keyboard.nextLine());}
			catch (Exception e) {System.out.println("Sorry we did not provide that service");continue;}

			if (input == 1) {
				System.out.println("What is the band?");
				String bandName = keyboard.nextLine();
				
				System.out.println("What is the date? Use form yyyy-MM-dd");
				String concertDateString = keyboard.nextLine();
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date concertDate;
				try{concertDate = format.parse(concertDateString);}
				catch (Exception e) {System.out.println("Told you, stick to the syntax!");continue;}
				
				System.out.println("How many tickets do you want?");
				int ticketAmount = Integer.parseInt(keyboard.nextLine());
				if (ticketAmount > 5) {
					System.out.println("I wonder how could you need so many tickets...");
				}
				
				if (service.bookTickets(bandName, concertDate, ticketAmount, customerName)) {
					System.out.println("Booked!");
				}
			} else if (input == 2) {
				if (service.cancelBooking(customerName)) {
					System.out.println("All your reservations are canceled!");
				} else {
					System.out.println("Sorry, here is nothing to cancel under your name");
				}
			} else if (input == 3) {
				System.out.println("Bye~");
				break;
			} else {
				System.out.println("Sorry we did not provide that service");
			}
		}
		keyboard.close();
	}
}
