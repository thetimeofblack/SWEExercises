package de.fhl.haoze.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author David
 *
 */
public class Server {
	ServerSocket serverSocket;
	Socket listeningSocket;
	InputStream in;
	OutputStream out;
	BufferedReader reader;
	PrintWriter writer;
	int echoCounter = 0;
	
	Server() {
		try {
			serverSocket = new ServerSocket(11900);
			listeningSocket = serverSocket.accept();
			System.out.println("Connected");
			in = listeningSocket.getInputStream();
			out = listeningSocket.getOutputStream();
			reader = new BufferedReader(new InputStreamReader(in));
			writer = new PrintWriter(out, true);
		} catch (Exception e) {
			System.out.println("Construct error");
		}
	}
	
	public void close() {
		try {
			in.close();
			out.close();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	private String receive() {
		String s = null;
		try {
			s = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return s;
	}
	
	public void echo() {
		while (true) {
			String s = receive();
			if (s != null) {
				if (s.equals("exit()")) {
					break;
				}
				writer.println(s);
				echoCounter ++;
				System.out.println("echo time: " + echoCounter);
				
			}
		}
	}
	
	public static void main(String[] args) {
		System.out.println("Server start");
		Server server = new Server();
		server.echo();
		server.close();
		System.out.println("Server stop");
	}

}
