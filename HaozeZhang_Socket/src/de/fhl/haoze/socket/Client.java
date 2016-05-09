package de.fhl.haoze.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author David
 *
 */
public class Client {
	Socket clientSocket;
	InputStream in;
	OutputStream out;
	BufferedReader reader;
	PrintWriter writer;
	String userInput;
	
	Client() {
		try {
			clientSocket = new Socket("127.0.0.1", 11900);
			in = clientSocket.getInputStream();
			out = clientSocket.getOutputStream();
			reader = new BufferedReader(new InputStreamReader(in));
			writer = new PrintWriter(out, true);
		} catch (Exception e) {
			System.out.println("Cannot connect, please make sure the server is running");
			System.exit(1);
		}
	}
	
	public void close() {
		try {
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setUserInput(String userInput) {
		this.userInput = userInput;
	}
	
	public void send() {
		writer.println(userInput);
	}
	
	public String receive() {
		String s = null;
		try {
			s = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return s;
	}
	
	public static void main(String[] args) {
		System.out.println("Client start");
		System.out.println("Type in exit() to stop");
		Scanner in = new Scanner(System.in);
		Client client = new Client();
		String input = null;
		
		
		while(true) {
			System.out.print("Send: ");
			input = in.nextLine();
			client.setUserInput(input);
			client.send();
			if (input.equals("exit()")) {
				break;
			}
			System.out.print("Received: ");
			System.out.println(client.receive());
		}

		client.close();
		in.close();
		System.out.println("Client stop");
	}
}
