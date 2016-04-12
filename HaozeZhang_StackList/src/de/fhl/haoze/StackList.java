/*
 * Author: Haoze Zhang
 * Date: 30.03.2016
 * File: StackList
 * Introduction: Stack implemented by List operations
 */
package de.fhl.haoze;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author david
 *
 */
public class StackList {
	static List<Integer> list = new ArrayList<Integer>(); // Static List for the
	// virtual stack

	/**
	 * Push data into the stack
	 * @param input
	 * @return
	 */
	static int push(int input) {
		list.add(input);
		return 0;
	}

	/**
	 * Pop out one datum out of stack
	 * @return
	 */
	static int pop() {
		if (list.isEmpty()) {
			System.out.println("Stack is empty, cannot pop");
			return 0;
		}
		int r = list.get(list.size() - 1);
		list.remove(list.size() - 1);
		return r;
	}

	/**
	 * @return
	 */
	static int printStack() { // Print data from bottom to top
		System.out.println("**** Stack bottom ****");
		for (Iterator<Integer> i = list.iterator(); i.hasNext();) {
			int n = i.next();
			System.out.println(n);
		}
		System.out.println("***** Stack top *****");
		return 0;
	}
}
// EOF