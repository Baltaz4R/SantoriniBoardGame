package etf.santorini.dv150189d;

public class Position {
	int x;
	int y;
	int z;
	
	public Position(int x, int y, int z) {
		if ((x >= 0 && x < 5)) {
			this.x = x;
		} else {
			System.out.println("Nedozvoljena pozicija x kordinate!");
		}
		
		if ((y >= 0 && y < 5)) {
			this.y = y;
		} else {
			System.out.println("Nedozvoljena pozicija y kordinate!");
		}
		
		if ((z >= 0 && z < 4)) {
			this.z = z;
		} else {
			System.out.println("Nedozvoljena pozicija z kordinate!");
		}
	}
	
	public Position(int x, int y) {
		if ((x >= 0 && x < 5)) {
			this.x = x;
		} else {
			System.out.println("Nedozvoljena pozicija x kordinate!");
		}
		
		if ((y >= 0 && y < 5)) {
			this.y = y;
		} else {
			System.out.println("Nedozvoljena pozicija y kordinate!");
		}
		this.z = 0;
	}
	
	public Position(Position p) {
		this.x = p.x;
		this.y = p.y;
		this.z = p.z;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getZ() {
		return z;
	}
	
	public void setPosition(int newX, int newY, int newZ) {
		x = newX;
		y = newY;
		z = newZ;
	}
	
	public void setX(int newX) {
		x = newX;
	}
	
	public void setY(int newY) {
		y = newY;
	}
	
	public void setZ(int newZ) {
		z = newZ;
	}
}
