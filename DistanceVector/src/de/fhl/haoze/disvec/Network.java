package de.fhl.haoze.disvec;

public class Network {
	public static final int INF = 0xffff;
	public static final int ROUTER_NUMBER = 6;
	public static final int[][] DISTANCE_MATRIX
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
	static int lID = 0;
	
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
		for (int i = 0; i < ROUTER_NUMBER; i++) {
			routers[i] = new Router(i);
		}
		for (int i = 0; i < ROUTER_NUMBER; i++) {
			Thread t = new Thread(routers[i]);
			t.setName(i + "");
			t.setDaemon(true);
			t.start();
		}
		System.out.println("Wait for convergence...");
		System.out.println();
//		try {
////			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		while(idleRouters < ROUTER_NUMBER);
		System.out.println("Converged");
		System.out.println("********** Report ********");
		System.out.println("Total traffic: " + msgPassed + " messages");
		System.out.println(routers[lID]);
		routers[lID].printForwardTable();
	}
}
