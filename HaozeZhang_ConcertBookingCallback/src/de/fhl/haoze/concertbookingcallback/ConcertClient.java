package de.fhl.haoze.concertbookingcallback;

import java.io.Serializable;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * @author David
 * @version 2016-06-01
 * Class ConcertClient
 * Client application which can make reservation of concert tickets.
 * Called back every 5 times of reservation to print out existing reservations
 */
public class ConcertClient extends UnicastRemoteObject implements ICallback {

	protected ConcertClient() throws RemoteException {
		super();
	}

	private static final long serialVersionUID = 5890474262948586679L;
	String customerName;
	
	public static void main(String[] args) throws Exception {
		
		ConcertClient client = new ConcertClient();
		ICallback callback = client;
		
		// look up server service
		System.out.println("Client started");
		IConcert service = (IConcert) Naming.lookup("rmi://" + "localhost" + "/ConcertBooking");
		System.out.println("Service looked up");
		
        // prepare keyboard input
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Your name please:");
		client.customerName = keyboard.nextLine();
		
		// Circulate here
		while(true) {
			System.out.println("What do you want to do?");
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
				
				if (service.bookTickets(bandName, concertDate, ticketAmount, client.customerName, callback)) {
					System.out.println("Booked!");
				}
			} else if (input == 2) {
				if (service.cancelBooking(client.customerName)) {
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

	/* (non-Javadoc)
	 * @see de.fhl.haoze.concertbookingcallback.ICallback#printBookingInfo(java.util.List)
	 */
	@Override
	public boolean printBookingInfo(List<String> bookingInfo) throws RemoteException {
		System.out.println("You have done " + ICallback.CALLBACK_FREQ +
				" times of booking, let's have a look");
		for (String s: bookingInfo) {
			System.out.println(s);
		}
		return true;
	}
}
