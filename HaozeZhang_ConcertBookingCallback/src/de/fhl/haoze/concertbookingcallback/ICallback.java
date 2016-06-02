package de.fhl.haoze.concertbookingcallback;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * @author David
 * @version 2016-06-01
 * Interface ICallback
 * Callback interface to achieve server call back
 */
public interface ICallback extends Remote {

	static final int CALLBACK_FREQ = 5;
	/**
	 * @param bookingInfo Server fill the list up with info strings
	 * @throws RemoteException
	 * Client print out the booking info every CALLBACK_FREQ times of booking
	 */
	public boolean printBookingInfo(List<String> bookingInfo) throws RemoteException;
}
