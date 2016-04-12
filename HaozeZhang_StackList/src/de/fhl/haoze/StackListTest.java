/*
 * Author: Haoze Zhang
 * Date: 30.03.2016
 * File: StackList
 * Introduction: Unit test for StackList
 */
package de.fhl.haoze;

public class StackListTest{
	public static void main(String[] args) {
		StackList.push(1);
		StackList.push(2);
		StackList.push(3);
		
		StackList.printStack();
		
		System.out.println("Poped out " + StackList.pop());
		
		StackList.printStack();		
	}
}
//EOF