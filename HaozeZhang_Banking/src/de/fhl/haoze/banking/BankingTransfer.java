package de.fhl.haoze.banking;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BankingTransfer extends Remote {
	public double deposit(double dep) throws RemoteException;
	public double withdraw(double with) throws RemoteException;
}
