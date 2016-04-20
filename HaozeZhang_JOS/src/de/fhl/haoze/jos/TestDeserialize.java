package de.fhl.haoze.jos;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class TestDeserialize {
	public static void deserialize() throws IOException {
		System.out.println("Deserialize class Student...");
		FileInputStream fis = new FileInputStream("Student.bin");
		ObjectInputStream ois = new ObjectInputStream(fis);
		Student student = new Student();
		try {
			student = (Student)ois.readObject();
			ois.close();
			fis.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("Deserialization finished, result:");
		System.out.println(student);
		
	}
}
