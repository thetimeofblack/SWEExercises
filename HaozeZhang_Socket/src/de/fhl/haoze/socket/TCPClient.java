/*adapted from https://docs.oracle.com/javase/tutorial/networking/sockets/readingWriting.html*/
package de.fhl.haoze.socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author LH Simple TCP Client which sends user input data to server
 */
public class TCPClient {

	public static void main(String[] args) throws IOException {

		PrintWriter out = null;
		BufferedReader in = null;
		Socket clientSocket = null;

		try {
			clientSocket = new Socket("127.0.0.1", 11900);
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// user input
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		String userInput;

		System.out.print("input: ");
		while ((userInput = stdIn.readLine()) != null) {
			out.println(userInput);
			System.out.println("from server: " + in.readLine());
			System.out.print("input: ");
		}

		out.close();
		in.close();
		stdIn.close();
		clientSocket.close();

	}

}
