/**
 * @author david
 * @class IOFileTest
 * @version last modification 2016-4-20
 * @Description Unit tests of public methods for class IOFile
 */

package de.fhl.haoze.filehandling;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Scanner;

public class IOFileTest {
	public static void main(String[] args) {
		System.out.println("****** IOFile test *******");
		
		String fileName = "Test\\text0.txt";
		IOFile iof = new IOFile(fileName);

		System.out.println("Operating file: " + fileName);
		
		// Test countLines()
		System.out.println("**************************");
		System.out.println("This file has " + iof.countLines() + " lines");
		
		// Test print()
		System.out.println("**************************");
		System.out.println("File content is:");
		System.out.println();
		iof.print();
		
		// Test write()
		System.out.println("**************************");
		String fileDest = "Test\\output.txt";
		System.out.println("Now write to file " + fileDest);
		try {
			iof.write(new FileOutputStream(fileDest));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("File content of " + fileDest + " is:");
		System.out.println();
		new IOFile(fileDest).print();
		
		// Test Copy()
		System.out.println("**************************");
		String fileCopy = "Test\\copy.txt";
		System.out.println("Now we copy the file to " + fileCopy);
		iof.copy(fileCopy);
		System.out.println("And the content of " + fileCopy + " is:");
		System.out.println();
		new IOFile(fileCopy).print();
		
		// Test printDirectory()
		System.out.println("**************************");
		System.out.println("Present working directory: ");
		iof.printDirectory();
		System.out.println("We have following files in this directory:");
		System.out.println(iof.getOtherFile());
		
		// Test delete()
		System.out.println("**************************");
		System.out.println("Now we delete the file");
		System.out.println("Warning: If you delete the file, the test cannot run again");
		System.out.println("Are you sure to delete? (y/n)");
		Scanner sc = new Scanner(System.in);
		String confirm = sc.nextLine();
		if (confirm == "y" || confirm == "Y") { // keyboard input for confirmation
			iof.delete();
		}
		sc.close();
		System.out.println("Is file still exists? " + new File(fileName).exists());
		
		System.out.println("******** Test over ********");
	}
}
