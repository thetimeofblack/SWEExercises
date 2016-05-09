package de.fhl.haoze.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * @author David
 * @version 2016-5-8
 * UDP calculator server
 */
public class Server {
	DatagramSocket serverSocket = null;
	DatagramPacket receivedPacket = null;
	byte[] receivedData = null;
	byte[] sendData = null;
	
	Server() {
		try {
			serverSocket = new DatagramSocket(11900);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return default true, if false, then stop command is received
	 */
	public boolean receive() {
		try {
			receivedData = new byte[1024];
			receivedPacket = new DatagramPacket(receivedData, receivedData.length);
			serverSocket.receive(receivedPacket);
			if (new String(receivedPacket.getData()).matches("(exit\\(\\)).+")) {
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * Send result back to client
	 */
	public void send() {
		if ((sendData = calculate().getBytes()) == null) {
			System.out.println("Not send");
		}
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, 
									receivedPacket.getAddress(), receivedPacket.getPort());
		try {
			serverSocket.send(sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return result of the calculation
	 * Calculate the received equation
	 */
	private String calculate() {
		String equ = new String(receivedPacket.getData());
		equ = equ.replaceAll("[^\\d\\.+\\-\\*/]", "");
		char oprd = (char) equ.replaceAll("[\\s\\d\\.]", "").getBytes()[0];
		String num = equ.replaceAll("[+\\-\\*/]", " ");
		Scanner s = new Scanner(num);
		double opr1 = 0.0;
		double opr2 = 0.0;
		double result = 0.0;
		String out = null;

		opr1 = s.nextDouble();
		opr2 = s.nextDouble();
		
		switch (oprd) {
		case '+':
			result = opr1 + opr2;
			break;
		case '-':
			result = opr1 - opr2;
			break;
		case '*':
			result = opr1 * opr2;
			break;
		case '/':
			if (opr2 == 0) {
				out = (equ + "=" + "NaN");
				s.close();
				System.out.println(out);
				return out;
			}
			result = opr1 / opr2;
			break;
		default:
			System.out.println("unknown operand");
			s.close();
			return null;
		}
		s.close();
		out = (equ + "=" + new DecimalFormat("#0.00").format(result));
		System.out.println(out);
		return out;
	}
	
	public static void main(String[] args) {
		System.out.println("Server start");
		Server server = new Server();
		while (true) {
			if (!server.receive()) {
				break;
			}
			server.send();
		}
		System.out.println("Server ends");
	}

}
