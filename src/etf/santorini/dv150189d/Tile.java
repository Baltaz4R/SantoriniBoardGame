package etf.santorini.dv150189d;

public class Tile {
	
	public enum BuildLevel {GROUND, LEVEL1, LEVEL2, LEVEL3, ROOF};
	
	BuildLevel Level;
	Position position;
	Player player;
	
	public Tile(int X, int Y) {
		this.position = new Position(X, Y);
		this.Level = BuildLevel.GROUND;
		this.player = null;
	}
	
	public Tile(Tile t) {
		this.Level = BuildLevel.values()[t.Level.ordinal()];
		if(t.position != null) {
			this.position = new Position(t.position);
		} else {
			this.position=null;
			}
		this.player = null;
		
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Position getPosition() {
		return position;
	}
	
	public BuildLevel getLevel() {
		return Level;
	}

	public boolean build() {
		if (this.player != null) {
			//System.out.println("Ima igrac na toj poziciji, pa je gradnja nedozvoljena!");
			return false;
		}
		if (Level == BuildLevel.ROOF) {
			System.out.println("Nedozvoljena gradnja na krovu!");
			return false;
		} else {
			Level = BuildLevel.values()[Level.ordinal() + 1];
			if (Level == BuildLevel.ROOF) {
				this.position = null;
			} else {
				this.position.setZ(Level.ordinal());
			}
			return true;
		}
		
	}
}
