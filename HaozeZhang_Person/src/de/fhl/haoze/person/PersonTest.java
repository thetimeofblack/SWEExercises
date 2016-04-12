package de.fhl.haoze.person;

import java.util.Date;

/**
 * @author david
 * Unit test for Class Person
 */
public class PersonTest {
	/**
	 * Test class Person, using three constructors and the destructor
	 * @param args
	 */
	public static void main(String[] args) {
		Person[] persons =  new Person[3];
		
		// To show the format of date
		System.out.println("Birth date for person is in this format:");
		System.out.println(Person.getBirthDateFormat().format(new Date()));
		System.out.println();
		
		persons[0] = new Person("Bill", "Gates", "1955-10-28");
		persons[1] = new Person(persons[0]);
		persons[2] = new Person();
		
		// Print each instance of Person
		for (Person p: persons) {
			System.out.println("Printing a person's information");
			System.out.println(p); // implicitly call p.toSting()
			System.out.println();
		}
		
		// Destroy each instance of Person
		for (Person p: persons) {
			p.destructPerson();
			System.out.println();
		}
	}
}
