package de.fhl.haoze.disvec;

/**
 * @author David
 * @version 2016-06-10
 * Class Network
 * A network with multiple routers, routers do algorithm.
 */
public class Network {
	public static final int INF = 0xffff;
	public static final int ROUTER_NUMBER = 6;
	public static final int[][] DISTANCE_MATRIX // Must be a symmetric triangle matrix
	= {{INF,   2, INF, INF, INF,   1},
	   {  2, INF,   3, INF,   2,   3},
	   {INF,   3, INF,   4, INF,   3},
	   {INF, INF,   4, INF,   2, INF},
	   {INF,   2, INF,   2, INF,   2},
	   {  1,   3,   3, INF,   2, INF}};
//	public static final int ROUTER_NUMBER = 3;
//	public static final int[][] DISTANCE_MATRIX
//	={{INF,   2,   3},
//	  {  2, INF,   1},
//	  {  3,   1, INF}};
	
	
	
	static int idleRouters = ROUTER_NUMBER;
	static int msgPassed = 0;
	static Router[] routers = new Router[ROUTER_NUMBER];
	static int lID = 0; // report this router
	
	static void passMsg(int sID, int rID, int[] msg) {
		routers[rID].receiveMsg(sID, msg);
		msgPassed++;
	}
	
	synchronized static void idle() {
		idleRouters++;
	}
	
	synchronized static void work() {
		idleRouters--;
	}
	
	static Router getRouter(int ID) {
		return routers[ID];
	}
	
	
	public static void main(String[] args) {
		System.out.println("Start");
		
		// create all routers
		for (int i = 0; i < ROUTER_NUMBER; i++) {
			routers[i] = new Router(i);
		}
		// start routers
		for (int i = 0; i < ROUTER_NUMBER; i++) {
			Thread t = new Thread(routers[i]);
			t.setName(i + "");
			t.setDaemon(true);
			t.start();
		}
		System.out.println("Wait for convergence...");
		
		// if program cannot reach the end, or there are sync issues, uncomment lines below
//		try {
//			Thread.sleep(300);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		
		// Wait until all routers are idle
		while(idleRouters < ROUTER_NUMBER);
		
		System.out.println("Converged");
		System.out.println("********** Report ********");
		System.out.println("Total traffic: " + msgPassed + " messages");
		System.out.println();
		System.out.println(routers[lID]);
		System.out.println();
		routers[lID].printDistanceVector();
		System.out.println();
		routers[lID].printForwardTable();
	}
}
