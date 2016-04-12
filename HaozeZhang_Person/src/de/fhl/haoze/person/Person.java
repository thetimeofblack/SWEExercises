/**
* class Person
* @version last changed in 2016-4-12
* @author david
* SWE exercise 2016-4-12
*/

package de.fhl.haoze.person;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public class Person {
	static DateFormat birthDateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
	Date birthDate;
	String firstName;
	String lastName;
	
	/**
	 * Constructor with no parameters
	 */
	Person(){
		firstName = "unknown first name";
		lastName = "unknown second name";
		birthDate = null;
	}
	
	/**
	 * Constructor
	 * @param firstName: String for first name
	 * @param lastName: String for last name
	 * @param birthDate: String for birth date, should conform the format yyyy-MM-dd
	 */
	Person(String firstName, String lastName, String birthDate){
		this.firstName = firstName;
		this.lastName = lastName;
		try {
			this.birthDate = birthDateFormat.parse(birthDate);
		} catch (ParseException e) {
			System.out.println("Error: Cannot parse date " + birthDate);
			//e.printStackTrace();
		}
	}
	
	/**
	 * Copy constructor
	 * @param person: The instance to be copied
	 */
	Person(Person person){
		this.firstName = person.firstName;
		this.lastName = person.lastName;
		this.birthDate = person.birthDate;
	}
	
	public static DateFormat getBirthDateFormat() {
		return birthDateFormat;
	}

	public static void setBirthDateFormat(DateFormat birthDateFormat) {
		Person.birthDateFormat = birthDateFormat;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		try {
			this.birthDate = birthDateFormat.parse(birthDate);
		} catch (ParseException e) {
			System.out.println("Error: Cannot parse date " + birthDate);
			//e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		if (this.birthDate == null) { // Check if birth date is valid
			return firstName + " " + lastName + ", born in " + "unknow date";
		}
		return firstName + " " + lastName + ", born in " + birthDateFormat.format(birthDate);
	}
	
	/**
	 * Destructor
	 * Print the instance (call toString() implicitly) then destruct
	 */
	public void destructPerson(){
		System.out.println("****** Destructor ******");
		System.out.println(this);
		try {
			this.finalize();
		} catch (Throwable e) {
			System.out.println("Error: Cannot destroy instance");
			//e.printStackTrace();
		} finally{
			System.out.println("** Instance destoryed **");
		}
	}	
}
