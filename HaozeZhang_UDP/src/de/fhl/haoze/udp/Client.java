package de.fhl.haoze.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * @author David
 * @version 2016-5-8
 * UDP calculator client
 */
public class Client {
	DatagramSocket clientSocket = null;
	DatagramPacket receivedPacket = null;
	Scanner s = new Scanner(System.in);
	byte[] receivedData = null;
	byte[] sendData = null;
	
	Client() {
		try {
			clientSocket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * @return qualified equation as a string, untrim
	 * Prompt user to input equation, with syntax check
	 */
	private String setUserInput() {
		String equ = null;
		while (true) {
			System.out.print("Enter calculation: ");
			equ = s.nextLine();
			if (equ.equals("exit()")) {
				return equ;
			}
			String check = equ.replaceAll("\\s+", "");
			if (!check.matches("\\d*\\.?\\d+[+\\-\\*/]\\d*\\.?\\d+")) {
				System.out.println("Syntax error, please conform the usage");
			} else {
				break;
			}
		}
		return equ;
	}
	
	/**
	 * @return default true, if false, stop command is received]
	 * Send equation through socket
	 */
	public boolean send() {
		DatagramPacket sendPacket = null;
		String inputStr = setUserInput();
		sendData = inputStr.getBytes();
		try {
			sendPacket = new DatagramPacket(sendData, sendData.length, 
					InetAddress.getByName("localhost"), 11900);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		try {
			clientSocket.send(sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (inputStr.equals("exit()")) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Receive result from server
	 */
	public void receive() {
		receivedData = new byte[128];
		try {
			receivedPacket = new DatagramPacket(receivedData, receivedData.length);
			clientSocket.receive(receivedPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.print("Result: ");
		System.out.println(new String(receivedPacket.getData()));
	}
	
	public static void main(String[] args) {
		Client client = new Client();
		System.out.println("Client Start");
		System.out.println("Usage: #.#[+-*/]#.#");
		System.out.println("Stop with 'exit()'");
		
		while (true) {
			if (!client.send()) {
				break;
			}
			client.receive();
		}
		System.out.println("Client stop");
	}
}
