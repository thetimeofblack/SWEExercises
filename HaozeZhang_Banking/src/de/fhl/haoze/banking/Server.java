package de.fhl.haoze.banking;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Server extends UnicastRemoteObject implements BankingTransfer{

	private static final long serialVersionUID = 1L;
	User user = null;
	
	protected Server() throws RemoteException {
		super();
		user = new User();
	}

	@Override
	public double deposit(double amount) throws RemoteException{
		return user.deposit(amount);
	}

	@Override
	public double withdraw(double amount) throws RemoteException{
		return user.withdraw(amount);
	}
	
	public static void main(String[] args) throws Exception {
		Server server = new Server();
		
        Naming.bind ("BankingTransfer", server);

        System.out.println ("Service bind....");
	}

}
