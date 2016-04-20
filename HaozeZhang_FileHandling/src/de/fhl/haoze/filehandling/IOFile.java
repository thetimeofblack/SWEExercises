/**
 * @author david
 * @class IOFile
 * @version last modification 2016-4-20
 * @Description Provides limited file IO methods
 */

package de.fhl.haoze.filehandling;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.LinkedList;
import java.util.List;

public class IOFile {
	String fileName;
	File f;
	FileInputStream fis;
	InputStreamReader isr;
	
	/**
	 * Constructor: construct a IOFile instance
	 * @param fn: File directory of which shall be manipulated
	 */
	IOFile(String fn) {
		fileName = fn;
		f = new File(fn);
		
		// Check existence of the file
		if(f.exists()) { 
			// Empty
		} else {
			System.out.println(fn + " not found");
			System.exit(0); // Exit if file not found
		}
		
		// Create FileInputStream
		try {
			fis = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		// Create InputStreamReader
		isr = new InputStreamReader(fis);
	}
	
	/**
	 * Reopen streams and readers
	 * Must be called when state of stream and reader has to change
	 */
	private void start() {
		try { // Reopen
			fis = new FileInputStream(f);
			isr = new InputStreamReader(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Close streams and readers
	 * Must be called when state of stream and reader is changed
	 */
	private void reset() {
		try { // Close all
			fis.close();
			isr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Count line number in a text file
	 * @return line number
	 */
	public int countLines() {
		start();
		int line = 0;
		LineNumberReader lnr = new LineNumberReader(new BufferedReader(isr));
		try {
			while ((lnr.readLine()) != null); // Locate to last line
		} catch (IOException e) {
			e.printStackTrace();
		}
		line = lnr.getLineNumber();
		reset();
		
		return line;
	}
	
	/**
	 * Write to file with given output stream
	 * @param os: output stream that wants to write to the file
	 */
	public void write(OutputStream os) {
		start();
		try {
			BufferedReader br = new BufferedReader(isr);
			while (br.ready()) { // Write byte to byte
				os.write(br.read());
			}
		} catch (IOException e) {
			System.out.println("Error: cannot write");
		} finally {
			reset();
		}
	}
	
	/**
	 * Print file on command line
	 */
	public void print() {
		start();
		BufferedReader br = new BufferedReader(isr);
	    try {
			for (String line; (line = br.readLine()) != null;) { // Print line to line
			    System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			reset();
		}
	}
	
	/**
	 * Copy file content to another file
	 * @param fileName: target file of copying
	 * @throws IOException 
	 */
	public void copy(String fileName) {
		start();
		try {
			Files.copy(f.toPath(), new File(fileName).toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		reset();
	}
	
	/**
	 * Delete the file
	 */
	public void delete() {
		try { // Close all file related resources
			fis.close();
			isr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (f.delete()) {
			System.out.println(f.getName() + " deleted");
		}
		else {
			System.out.println("Error: cannot delete" + f.getName());
		}
	}
	
	/**
	 * Prints the file directory
	 */
	public void printDirectory() {
		System.out.println(f.getAbsolutePath());
	}
	
	/**
	 * Return list of other files in the same directory
	 * @return 
	 */
	List<String> getOtherFile() {
		List<String> dir = new LinkedList<String>();
		for (File tempf : f.getParentFile().listFiles()) {
			dir.add(tempf.getName());
		}
		return dir;
	}
}
