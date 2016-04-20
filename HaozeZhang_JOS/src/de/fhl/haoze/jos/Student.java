package de.fhl.haoze.jos;

import java.io.Serializable;

public class Student implements Serializable{
	private static final long serialVersionUID = 1L;
	int studentNumber;
	int degreeCourse;
	
	Student() {
		// Empty
	}
	
	Student(int sn, int dc) {
		studentNumber = sn;
		degreeCourse = dc;
	}
	
	public String toString() {
		return "Student number is " + studentNumber
				+ ", degree course is " + degreeCourse;
	}
}
