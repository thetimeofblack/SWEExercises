/*adapted from https://docs.oracle.com/javase/tutorial/networking/sockets/readingWriting.html*/
package de.fhl.haoze.socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author LH
 * Simple TCPServer for replying to the client with the same message as received
 */
public class TCPServer {

	public static void main(String[] args) throws IOException {

		ServerSocket serverSocket = null; 

		try { 
			serverSocket = new ServerSocket(11900); 
		} catch (IOException e) { 
			e.printStackTrace();
		} 

		Socket listeningSocket = null; 
		System.out.println ("Waiting for connection");

		try { 
			listeningSocket = serverSocket.accept(); 
		} catch (IOException e) { 
			e.printStackTrace();
		} 

		System.out.println ("Connection successful");

		PrintWriter out = new PrintWriter(listeningSocket.getOutputStream(), true); 
		BufferedReader in = new BufferedReader(new InputStreamReader(listeningSocket.getInputStream())); 

		String inputLine; 

		while ((inputLine = in.readLine()) != null) { 
			System.out.println ("Message from client: " + inputLine); 
			out.println(inputLine); 
		} 

		out.close(); 
		in.close(); 
		listeningSocket.close(); 
		serverSocket.close(); 
	}

}
