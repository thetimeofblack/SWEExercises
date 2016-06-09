package de.fhl.haoze.disvec;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Router implements Runnable {

	static final int lID = Network.ROUTER_NUMBER + 1;
	int ID = 0;
	int iterations = 0;
	int msgSent = 0;
	int msgReceived = 0;
	/* <neighborID, cost> */
	Map<Integer, Integer> cost = new HashMap<Integer, Integer>();
	/* <neighborID, distance to each target> */
	Map<Integer, int[]> distanceVector = new HashMap<Integer, int[]>();
	/* 
	 * [0]: shortest distance to each target
	 * [1]: corresponding forward neighbor 
	 * */
	int[][] forwardTable = null;
	volatile BlockingQueue<int[]> msgs = new LinkedBlockingQueue<int[]>();
	volatile BlockingQueue<Integer> sIDs = new LinkedBlockingQueue<Integer>();
	
	Router() {
		//empty
	}
	
	Router(int ID) {
		this.ID = ID;
		
		int[] INFvector = new int[Network.ROUTER_NUMBER];
		for (int i = 0; i < INFvector.length; i++) {
			INFvector[i] = Network.INF;
		}
		
		forwardTable = new int[2][];
		forwardTable[0] = Network.DISTANCE_MATRIX[ID].clone();
		forwardTable[1] = new int[Network.ROUTER_NUMBER];
		forwardTable[1][ID] = ID; // handle itself

		for (int column = 0; column < Network.ROUTER_NUMBER; column++) { // for each target
			if (Network.DISTANCE_MATRIX[ID][column] != Network.INF) { // if connected (neighbor)
				cost.put(column, Network.DISTANCE_MATRIX[ID][column]);
				// create row for each neighbor
				distanceVector.put(column, INFvector.clone());
				// must do these
				distanceVector.get(column)[column] = cost.get(column); // add neighbor cost to distance vector
				forwardTable[1][column] = column; // add neighbor cost to forward table
			}
		}
	}
	
	private boolean calculateForwardTable() {
		int newForwardTable[][] = new int[2][Network.ROUTER_NUMBER];
		
		for (int i = 0; i < Network.ROUTER_NUMBER; i++) { // for every column (target)
			if (i == ID) { // ignore itself
				continue;
			}
			int shortCost = forwardTable[0][i];
			int shortID = forwardTable[1][i]; // shortest value comes from forwardTable & distanceVector
			for (Entry<Integer, int[]> cost: distanceVector.entrySet()) { // for every neighbor
				if (cost.getValue()[i] < shortCost) { // shorter cost found
				shortCost = cost.getValue()[i];
				shortID = cost.getKey();
				}
			}
			newForwardTable[0][i] = shortCost;
			newForwardTable[1][i] = shortID;
		}
		if (isForwardTableConsistant(newForwardTable)) { // unchanged
			return false; // indicates not changed
		} else {
			forwardTable = newForwardTable;
			return true;
		}
	}
	
	private boolean isForwardTableConsistant(int[][] newForwardTable) {
		for (int i = 0; i < Network.ROUTER_NUMBER; i++) {
			for (int j = 0; j < 2.; j++) {
				if (newForwardTable[j][i] != forwardTable[j][i]) {
					return false;
				}
			}
		}
		return true;
	}
	
	private void sendMsg(int[] msg) {
		iterations++;
		for (int rID: distanceVector.keySet()) {
//			if (ID == lID) {
//				System.out.println(ID + " ---> " + rID);
//				printMsg(msg);
//			}
			Network.passMsg(ID, rID, msg.clone());
			msgSent++;
		}
	}

	private void process() {
		int sID = 0;
		int[] msg = null;
		try {
			sID = sIDs.take();
			msg = msgs.take().clone();
//			if (ID == lID) {
//				System.out.println("message from " + sID);
//				printMsg(msg);
//			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			return;
		}
		Network.work();
//		if (ID == lID) {
//			System.out.println(ID + " process msg from " + sID);
//			printMsg(msg);
//		}
		for (int i = 0; i < Network.ROUTER_NUMBER; i++) {
			if (msg[i] == Network.INF) {
				continue;
			} else if (i == ID) {
				msg[i] = Network.INF;
			} else if (i == sID) {
				msg[i] = cost.get(sID);
			} else {
				msg[i] += cost.get(sID);
			}
		}
//		if (ID == lID) {
//			System.out.println("msg after process: ");
//			printMsg(msg);
//		}
		distanceVector.put(sID, msg);
		
		if (calculateForwardTable()) {
			sendMsg(forwardTable[0]);
		}
		Network.idle();
	}

	public void receiveMsg(int sID, int[]msg) {
		msgReceived++;
		msgs.offer(msg);
		sIDs.offer(sID);
//		if (sID == lID) {
//			System.out.println(ID + " <--- " + sID);
//		}
	}
	
	
	private void init() {
		sendMsg(forwardTable[0]);
	}
	
	public void printMsg(int[] msg) {
		String msgString = "";
		for (int i = 0; i < Network.ROUTER_NUMBER; i++) {
			msgString += msg[i] + " ";
		}
		System.out.println("Msg: " + msgString);
	}
	
	public void printForwardTable() {
		System.out.println("Router " + (char)(ID + 'A') + " forward table:");
		for (int i = 0; i < Network.ROUTER_NUMBER; i++) {
			if (i == ID) {
				continue;
			}
			System.out.println("Target: " + (char)('A'+i) + "\tNeighbour: " + (char)('A' + forwardTable[1][i])
					+ "\tCost: " + forwardTable[0][i]);
		}
	}
	
	public void printDistanceVector() {
		System.out.println("Router " + (char)(ID + 'A') + " distance vector:");
		for (Entry<Integer, int[]> e: distanceVector.entrySet()) {
			String costString = "";
			
			int index = 0;
			for (int i: e.getValue()) {
				if (index == ID) {
					costString += "N/a ";
				} else {
					costString += i + " ";
				}
				index++;
			}
			System.out.println("Neighbour: " + (char)('A'+e.getKey()) + "  " + costString);
		}
	}
	
	public void printCost() {
		System.out.println("Router " + (char)(ID + 'A') + " cost to neighbours:");
		for (Entry<Integer, Integer> e: cost.entrySet()) {
			System.out.println("Neighbour: " + (char)('A'+e.getKey()) + "  " + e.getValue());
		}
	}
	@Override
	public String toString() {
		String s = "Router " + (char)(ID + 'A') + ": \n"
				+ "Iteration: " + iterations + "\n"
				+ "Sent: " + msgSent + "\n"
				+ "Received: " + msgReceived + "\n";
		return s;
	}

	@Override
	public void run() {
		Network.work();
		init();
		Network.idle();
//		if (ID == lID) {
//			printForwardTable();
//			printDistanceVector();
//		}
		for (;;) {
			process();
//			if (ID == lID) {
//				printForwardTable();
//				printDistanceVector();
//				printCost();
//			}
		}
	}
}
