package de.fhl.haoze.multiserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author david
 * @version 20160511
 * Multi-thread server handling multiple clients who send time stamps
 * The server receive time stamp and print it out
 * Run the server prior to the clients
 */
public class Server {
	ServerSocket serverSocket = null;
	ClientHandler clientHandler = null;
	
	Server() {
		try {
			serverSocket = new ServerSocket(11900);
			clientHandler = new ClientHandler();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return Listening socket for a certain connected client
	 * Server accept a connection of a client and return the socket
	 */
	private Socket getClientConnection() {
		Socket listeningSocket = null;
		try {
			listeningSocket = serverSocket.accept();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return listeningSocket;
	}
	
	/**
	 * @return Default true, if false, cannot create a thread for the client,
	 * 		   it is likely that max number of clients is reached
	 * Create socket connection and handler thread for each potential client
	 */
	boolean serverService() {
		return clientHandler.accpetClient(getClientConnection());
	}
	
	public static void main(String[] args) {
		
		Server server = new Server();
		System.out.println("Server start");
		System.out.println("Server ready");
		
		for (int i = 0; i < 3; i++) { // assume three clients are to connect
			if (!server.serverService()) {
				System.out.println("Reached MAX supported clients");
			}
			if (Thread.activeCount() <= 1) { // stop waiting for connection 
											 // if all other connections are closed
				break;
			}
		}
	}
}

class ClientHandler implements Runnable{
	static final int MAX_CLIENTS = 3;
	int clientNumber = 0;
	Thread[] clientThreads = new Thread[MAX_CLIENTS];
	Socket[] clientSockets = new Socket[MAX_CLIENTS];
	
	/**
	 * @param listeningSocket, the socket for a client
	 * @return default true, if false, client number limit is reached, cannot accept current client
	 * Create a thread to handle client messages
	 */
	boolean accpetClient(Socket listeningSocket) {
		if (clientNumber < MAX_CLIENTS) { // check client number
			// create the thread for the client
			Thread clientThread = new Thread(this);
			clientThread.setName(clientNumber + "");
			//log the socket and the thread
			clientSockets[clientNumber] = listeningSocket;
			clientThreads[clientNumber] = clientThread;
			
			clientThread.start();
			
			clientNumber ++;
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void run() {
		int clientID = Integer.parseInt(Thread.currentThread().getName());
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(clientSockets[clientID].getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String str = null;
		int counter = 0;
		while (true) {
			try {
				str = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (str.equals("END")) { // check for "END" command
				break;
			} else {
				System.out.println("Client" + clientID + ": " + str);
				counter ++;
			}
		}
		System.out.println("Client" + clientID + " has ended, "
				 + counter + " time stamps have been sent");
		
		// Close socket
		try {
			clientSockets[clientID].close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
