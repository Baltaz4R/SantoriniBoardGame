package etf.santorini.dv150189d;

import java.util.Observable;

public class Board extends Observable {

	Player[] player1 = new Player[2];
	Player[] player2 = new Player[2];
	private Tile[][] grid = new Tile[5][5];
	
	/*
	 * InfoPanel info
	 */
	String Turn = "";
	String Stage = "";


	public void initBoard() {
		
		for (int i=0; i<5; i++) {
			for (int j=0; j<5; j++) {
				grid[i][j] = new Tile(i,j);
			}
		}

		player1 = new Player[2];
		player2 = new Player[2];
		
		setChanged();
		notifyObservers();
	}
	
	public Board() {
	}
	
	public Board(Board b) {
		player1 = new Player[2];
		player2 = new Player[2];
		for (int i=0; i<5; i++) 
			for (int j=0; j<5; j++) {
				grid[i][j] = new Tile(b.grid[i][j]);
			}
		int x = b.player1[0].getTile().getPosition().getX();
		int y = b.player1[0].getTile().getPosition().getY();
		player1[0] = new Player(grid[x][y]);
		x = b.player1[1].getTile().getPosition().getX();
		y = b.player1[1].getTile().getPosition().getY();
		player1[1] = new Player(grid[x][y]);
		x = b.player2[0].getTile().getPosition().getX();
		y = b.player2[0].getTile().getPosition().getY();
		player2[0] = new Player(grid[x][y]);
		x = b.player2[1].getTile().getPosition().getX();
		y = b.player2[1].getTile().getPosition().getY();
		player2[1] = new Player(grid[x][y]);
	}
	
	public boolean build1(int i, Tile t) {
		boolean b = player1[i].build(t);
		setChanged(); 
		notifyObservers();
		return b;
	}
	
	public boolean build2(int i, Tile t) {
		boolean b = player2[i].build(t);
		setChanged(); 
		notifyObservers();
		return b;
	}
	
	public void setPlayer1(int i, Player p) {
		player1[i] = p;
		setChanged(); 
		notifyObservers();
	}
	
	public void setPlayer2(int i, Player p) {
		player2[i] = p;
		setChanged(); 
		notifyObservers();
	}
	
	public Player[] getPlayer1() {
		return player1;
	}
	
	public Player[] getPlayer2() {
		return player2;
	}
	
	public Tile[][] getGrid() {
		return grid;
	}
	
	public Tile getTile(int i, int j) {
		if ((i >= 0 && i < 5) && (j >= 0 && j < 5)) {
			return grid[i][j];
		} else 
			return null;
	}
	
	public boolean move1(int i, Tile t) {
		boolean b = player1[i].move(t);
		setChanged(); 
		notifyObservers();
		return b;
	}
	
	public boolean move2(int i, Tile t) {
		boolean b = player2[i].move(t);
		setChanged(); 
		notifyObservers();
		return b;
	}

	public void setTurn(String turn) {
		Turn = turn;
		setChanged(); 
		notifyObservers();
	}

	public void setStage(String stage) {
		Stage = stage;
		setChanged(); 
		notifyObservers();
	}
	
}