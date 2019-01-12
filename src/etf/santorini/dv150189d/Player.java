package etf.santorini.dv150189d;

public class Player {
	
	private Tile myTile;
	
	public Player(Tile myTile) {
		this.myTile = myTile;
		this.myTile.setPlayer(this);
	}

	//Sta ako ostane na istom polju?
	public boolean move(Tile t) {
		if((t != null) && (t.getPosition() != null) && (t.getPlayer() == null) && 
			((t.getPosition().getX() == myTile.getPosition().getX() - 1) || (t.getPosition().getX() == myTile.getPosition().getX() + 1) || (t.getPosition().getX() == myTile.getPosition().getX())) && 
			((t.getPosition().getY() == myTile.getPosition().getY() - 1) || (t.getPosition().getY() == myTile.getPosition().getY() + 1) || (t.getPosition().getY() == myTile.getPosition().getY())) && 
			((t.getPosition().getZ() == myTile.getPosition().getZ() - 1) || (t.getPosition().getZ() == myTile.getPosition().getZ() + 1) || (t.getPosition().getZ() == myTile.getPosition().getZ()))) {
			myTile.setPlayer(null);
			myTile = t;
			myTile.setPlayer(this);
			return true;
		} else {
			//System.out.println("Nedozvoljen potez!");
			return false;
		}
	}
	
	public boolean build(Tile t) {
		if((t != null) && (t.getPosition() != null) &&
			((t.getPosition().getX() == myTile.getPosition().getX() - 1) || (t.getPosition().getX() == myTile.getPosition().getX() + 1) || (t.getPosition().getX() == myTile.getPosition().getX())) && 
			((t.getPosition().getY() == myTile.getPosition().getY() - 1) || (t.getPosition().getY() == myTile.getPosition().getY() + 1) || (t.getPosition().getY() == myTile.getPosition().getY()))) {
			return t.build();
		} else {
			//System.out.println("Nedozvoljena gradnja!");
			return false;
		}
	}
	
	public boolean canMove(Board b) {
		boolean canMove = false;
		int x = this.myTile.getPosition().getX();
		int y = this.myTile.getPosition().getY();
		Tile nw = b.getTile(x + 1, y + 1);
		Tile n = b.getTile(x, y + 1);
		Tile ne = b.getTile(x - 1, y + 1);
		Tile w = b.getTile(x + 1, y);
		Tile e = b.getTile(x - 1, y);
		Tile sw = b.getTile(x + 1, y - 1);
		Tile s = b.getTile(x, y - 1);
		Tile se = b.getTile(x - 1, y - 1);
		if ((nw != null) && (nw.getPosition() != null) && (nw.getPlayer() == null) && 
			((nw.getPosition().getZ() == myTile.getPosition().getZ() - 1)|| (nw.getPosition().getZ() == myTile.getPosition().getZ() + 1) || (nw.getPosition().getZ() == myTile.getPosition().getZ()))) {
			canMove = true;
		} else if ((n != null) && (n.getPosition() != null) && (n.getPlayer() == null) && 
				((n.getPosition().getZ() == myTile.getPosition().getZ() - 1)|| (n.getPosition().getZ() == myTile.getPosition().getZ() + 1) || (n.getPosition().getZ() == myTile.getPosition().getZ()))) {
			canMove = true;
		} else if ((ne != null) && (ne.getPosition() != null) && (ne.getPlayer() == null) && 
				((ne.getPosition().getZ() == myTile.getPosition().getZ() - 1)|| (ne.getPosition().getZ() == myTile.getPosition().getZ() + 1) || (ne.getPosition().getZ() == myTile.getPosition().getZ()))) {
			canMove = true;
		} else if ((w != null) && (w.getPosition() != null) && (w.getPlayer() == null) && 
				((w.getPosition().getZ() == myTile.getPosition().getZ() - 1)|| (w.getPosition().getZ() == myTile.getPosition().getZ() + 1) || (w.getPosition().getZ() == myTile.getPosition().getZ()))) {
			canMove = true;
		} else if ((e != null) && (e.getPosition() != null) && (e.getPlayer() == null) && 
				((e.getPosition().getZ() == myTile.getPosition().getZ() - 1)|| (e.getPosition().getZ() == myTile.getPosition().getZ() + 1) || (e.getPosition().getZ() == myTile.getPosition().getZ()))) {
			canMove = true;
		} else if ((sw != null) && (sw.getPosition() != null) && (sw.getPlayer() == null) && 
				((sw.getPosition().getZ() == myTile.getPosition().getZ() - 1)|| (sw.getPosition().getZ() == myTile.getPosition().getZ() + 1) || (sw.getPosition().getZ() == myTile.getPosition().getZ()))) {
			canMove = true;
		} else if ((s != null) && (s.getPosition() != null) && (s.getPlayer() == null) && 
				((s.getPosition().getZ() == myTile.getPosition().getZ() - 1)|| (s.getPosition().getZ() == myTile.getPosition().getZ() + 1) || (s.getPosition().getZ() == myTile.getPosition().getZ()))) {
			canMove = true;
		} else if ((se != null) && (se.getPosition() != null) && (se.getPlayer() == null) && 
				((se.getPosition().getZ() == myTile.getPosition().getZ() - 1)|| (se.getPosition().getZ() == myTile.getPosition().getZ() + 1) || (se.getPosition().getZ() == myTile.getPosition().getZ()))) {
			canMove = true;
		}
		return canMove;
	}
	
	public Tile getTile() {
		return myTile;
	}
}

