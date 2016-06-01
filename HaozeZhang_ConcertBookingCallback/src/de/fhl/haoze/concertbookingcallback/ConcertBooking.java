package de.fhl.haoze.concertbookingcallback;

import java.util.Date;

/**
 * @author David
 * @version 2016-06-01
 * Class ConcertBooking
 * A reservation for each booking activity
 */
public class ConcertBooking {

	private String bandName;
	private Date concertDate;
	private int ticketAmount;
	private String customerName;
	
	@Override
	public String toString() {
		return bandName + " " + concertDate + " " + ticketAmount + " " + customerName;
	}
	
	/*
	 * getters
	 */
	public String getBandName() {
		return bandName;
	}
	public Date getConcertDate() {
		return concertDate;
	}
	public int getTicketAmount() {
		return ticketAmount;
	}
	public String getCustomerName() {
		return customerName;
	}
	/*
	 * setters
	 */
	public void setBandName(String bandName) {
		this.bandName = bandName;
	}
	public void setConcertDate(Date concertDate) {
		this.concertDate = concertDate;
	}
	public void setTicketAmount(int ticketAmount) {
		this.ticketAmount = ticketAmount;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
}
