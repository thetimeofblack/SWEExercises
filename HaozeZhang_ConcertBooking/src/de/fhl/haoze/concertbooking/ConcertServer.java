package de.fhl.haoze.concertbooking;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author David
 * @version 2016-06-01
 * Class ConcertServer
 * The server who provides concert tickets booking RMI service
 * registers as "ConcertBooking"
 */
public class ConcertServer extends UnicastRemoteObject implements IConcert {

	private static final long serialVersionUID = -8638606130209725007L;
	List<ConcertBooking> concertBookings = new LinkedList<ConcertBooking>();
	
	public ConcertServer() throws RemoteException {
		super();
	}

	/* (non-Javadoc)
	 * @see de.fhl.haoze.concertbooking.IConcert#cancelBooking(java.lang.String)
	 */
	@Override
	public boolean cancelBooking(String customerName) throws RemoteException {
		boolean isCanceled = false;
		System.out.println("Cancel " + customerName + "'s reservation");
		for (ConcertBooking cb: concertBookings) {
			if (cb.getCustomerName().equals(customerName)) {
				concertBookings.remove(cb);
				isCanceled = true;
			}
		}
		System.out.println("Cancelation result: " + isCanceled);
		return isCanceled;
	}

	/* (non-Javadoc)
	 * @see de.fhl.haoze.concertbooking.IConcert#bookTickets(java.lang.String, java.util.Date, int, java.lang.String)
	 */
	@Override
	public boolean bookTickets(String bandName, Date concertDate, int ticketAmount, String customerName) throws RemoteException {
		ConcertBooking cb = new ConcertBooking();
		cb.setBandName(bandName);
		cb.setConcertDate(concertDate);
		cb.setTicketAmount(ticketAmount);
		cb.setCustomerName(customerName);
		concertBookings.add(cb);
		System.out.println("Reservation:");
		System.out.println(bandName + " " + concertDate + " " + ticketAmount + " " + customerName);
		return true;
	}
	
	public static void main(String[] args) throws Exception {
		ConcertServer server = new ConcertServer();
		
		System.out.println("Server started");
		Registry r = LocateRegistry.getRegistry();
        r.bind("ConcertBooking", server);
        System.out.println("Service bonud");
        System.out.println("Waiting for requests...");
	}

}
