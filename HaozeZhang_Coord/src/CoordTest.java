import java.io.*;

public class CoordTest {
	public static void main(String[] args) {
		Coord c = new Coord();
		try {
			FileOutputStream fos = new FileOutputStream("coords.txt");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			FileInputStream fis = new FileInputStream("coords.txt");
			ObjectInputStream ois = new ObjectInputStream(fis);
			oos.writeObject(c);
			c = (Coord)ois.readObject();
			ois.close();
			oos.close();
		} catch (Exception e) {
			System.err.println(e);
		}
		System.out.println(c.getX() + c.getY() + c.getZ());
	}
	
	
}

class Coord implements Serializable {
	private static final long serialVersionUID = 1L;
	public int x = 3;
	transient long y = 4;
	private short z = 5;
	
	int getX() {
		return x;
	}
	
	long getY() {
		return y;
	}
	
	short getZ() {
		return z;
	}
}
