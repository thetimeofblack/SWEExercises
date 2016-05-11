package de.fhl.haoze.multiserver;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author David
 * @version 20160511
 * Client who sends time stamp periodically every 500ms
 * for 50 times to the server, multiple clients can be 
 * executed at the same time
 * In the test, client shall be invoked three times
 */
public class Client {
	Socket clientSocket;
	OutputStream out;
	PrintWriter writer;
	String userInput;
	
	Client() {
		try {
			clientSocket = new Socket("127.0.0.1", 11900);
			out = clientSocket.getOutputStream();
			writer = new PrintWriter(out, true);
		} catch (Exception e) {
			System.out.println("Cannot connect, please make sure the server is running");
			System.exit(1);
		}
	}
	
	/**
	 * To kill transmission by sending "END" command and closing socket
	 */
	public void close() {
		writer.println("END");
		try {
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Send the time stamp
	 */
	public void send() {
		writer.println(System.currentTimeMillis());
	}
	
	public static void main(String[] args) {
		System.out.println("Client start");
		Client client = new Client();
		
		for (int i = 0; i < 50; i++) { // send 50 time stamps in 500ms intervals
			client.send();
			System.out.println("Send " + (i+1) + " data");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		client.close();
		System.out.println("Client stop");
	}
}

