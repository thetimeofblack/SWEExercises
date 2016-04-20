package de.fhl.haoze.jos;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class TestSerializing {	
	public static void serialization() throws IOException {
		System.out.println("Serialize class Student...");
		FileOutputStream fos = new FileOutputStream("Student.bin");
		Student student = new Student(1234, 1234);
		try {
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(student);
			oos.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Serialization finished.");
	}
}

