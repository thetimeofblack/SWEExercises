package de.fhl.haoze.concertbookingcallback;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

/**
 * @author David
 * @version 2016-06-01
 * Interface IConcert
 * RMI remote interface for concert booking service
 */
public interface IConcert extends Remote {

	/**
	 * @param bandName
	 * @param concertDate
	 * @param ticketAmount
	 * @param customerName
	 * @return Always true, indicates a successful booking
	 * @throws RemoteException
	 * Client requests a ticket booking routine with necessary information
	 */
	public boolean bookTickets(String bandName, Date concertDate, int ticketAmount, String customerName) throws RemoteException;
	/**
	 * @param customerName
	 * @return	True, canceled; False, no such booking
	 * @throws RemoteException
	 * Client requests to cancel bookings made by a certain customer
	 */
	public boolean cancelBooking(String customerName) throws RemoteException;
}
