package etf.santorini.dv150189d;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import etf.santorini.dv150189d.Tile.BuildLevel;

public class PvP implements Mode, ActionListener {
	
	public enum Stage {START, P1P1, P1P2, P2P1, P2P2, P1MOVE1, P1MOVE2, P1BUILD, P2MOVE1, P2MOVE2, P2BUILD, END };
	Stage stage = Stage.START;
	
	Board board;
	MainWindow gui;
	
	public PvP(Board board, MainWindow gui) {
		this.board = board;
		this.gui = gui;
		
		board.initBoard();
		gui.getBoardRenderer().setMyBoard(board);
		for (int i=0; i<5; i++) 
			for (int j=0; j<5; j++) {
				gui.getBoardRenderer().getTileRenderers(i, j).setMyTile(board.getTile(i, j));
			}
		gui.getBoardRenderer().setActionListener(this);	

		board.addObserver(gui.getBoardRenderer());
		board.addObserver(gui.getInfoPanel());
		
		stage = Stage.P1P1;
		board.setTurn("Player 1");
		board.setStage("Set first pawn");
		gui.showSelectPosition("first pawn.", "Player 1");
	}
	
	private int pawn = -1;
	
	@Override
	public void actionPerformed(ActionEvent e) {
		TileRenderer tr;
		switch(stage) {
		case P1P1:
			tr = (TileRenderer) e.getSource();
			board.setPlayer1(0, new Player(tr.myTile));
			tr.myTile.player = board.player1[0];
			stage = Stage.P1P2;
			board.setStage("Set second pawn");
			gui.showSelectPosition("second pawn.", "Player 1");
			break;
		case P1P2:
			tr = (TileRenderer) e.getSource();
			if (tr.myTile.player == null) {
				board.setPlayer1(1, new Player(tr.myTile));
				tr.myTile.player = board.player1[1];
				stage = Stage.P2P1;
				board.setTurn("Player 2");
				board.setStage("Set first pawn");
				gui.showSelectPosition("first pawn.", "Player 2");
			}
			break;
		case P2P1:
			tr = (TileRenderer) e.getSource();
			if (tr.myTile.player == null) {
				board.setPlayer2(0, new Player(tr.myTile));
				tr.myTile.player = board.player2[0];
				stage = Stage.P2P2;
				board.setStage("Set second pawn");
				gui.showSelectPosition("second pawn.", "Player 2");
			}
			break;
		case P2P2:
			tr = (TileRenderer) e.getSource();
			if (tr.myTile.player == null) {
				board.setPlayer2(1, new Player(tr.myTile));
				tr.myTile.player = board.player2[1];
				stage = Stage.P1MOVE1;
				board.setTurn("Player 1");
				board.setStage("Select pawn to move");
			}
			break;
		case P1MOVE1:
			tr = (TileRenderer) e.getSource();
			if (tr.myTile.player == board.player1[0]) {
				pawn = 0;
				stage = Stage.P1MOVE2;
				board.setStage("Move");
			} else if (tr.myTile.player == board.player1[1]) {
				pawn = 1;
				stage = Stage.P1MOVE2;
				board.setStage("Move");
			} else
				pawn = -1;
			break;
		case P1MOVE2:
			tr = (TileRenderer) e.getSource();
			if (tr.myTile.player == board.player1[0]) {
				pawn = 0;
				stage = Stage.P1MOVE2;
			} else if (tr.myTile.player == board.player1[1]) {
				pawn = 1;
				stage = Stage.P1MOVE2;
			} else if (board.move1(pawn, tr.myTile)) {
				if((!board.player2[0].canMove(board) && !board.player2[1].canMove(board)) || (board.player1[pawn].getTile().getLevel() == BuildLevel.LEVEL3)) {
					stage = Stage.END;
					board.setTurn("");
					board.setStage("Winner is Player 1!");
					gui.showWinner("Player 1.");
				} else {
					stage = Stage.P1BUILD;
					board.setStage("Build");
				}
			}
			break;
		case P1BUILD:
			tr = (TileRenderer) e.getSource();
			if (board.build1(pawn, tr.myTile)) {
				if(!board.player2[0].canMove(board) && !board.player2[1].canMove(board)) {
					stage = Stage.END;
					board.setTurn("");
					board.setStage("Winner is Player 1!");
					gui.showWinner("Player 1.");
				} else {
					stage = Stage.P2MOVE1;
					board.setTurn("Player 2");
					board.setStage("Select pawn to move");
					pawn = -1;
				}
			}
			break;
		case P2MOVE1:
			tr = (TileRenderer) e.getSource();
			if (tr.myTile.player == board.player2[0]) {
				pawn = 0;
				stage = Stage.P2MOVE2;
				board.setStage("Move");
			} else if (tr.myTile.player == board.player2[1]) {
				pawn = 1;
				stage = Stage.P2MOVE2;
				board.setStage("Move");
			} else
				pawn = -1;
			break;
		case P2MOVE2:
			tr = (TileRenderer) e.getSource();
			if (tr.myTile.player == board.player2[0]) {
				pawn = 0;
				stage = Stage.P2MOVE2;
			} else if (tr.myTile.player == board.player2[1]) {
				pawn = 1;
				stage = Stage.P2MOVE2;
			} else if (board.move2(pawn, tr.myTile)) {
				if((!board.player1[0].canMove(board) && !board.player1[1].canMove(board))  || (board.player2[pawn].getTile().getLevel() == BuildLevel.LEVEL3)) {
					stage = Stage.END;
					board.setTurn("");
					board.setStage("Winner is Player 2!");
					gui.showWinner("Player 2.");
				} else {
					stage = Stage.P2BUILD;
					board.setStage("Build");
				}
			}
			break;
		case P2BUILD:
			tr = (TileRenderer) e.getSource();
			if (board.build2(pawn, tr.myTile)) {
				if(!board.player1[0].canMove(board) && !board.player1[1].canMove(board)) {
					stage = Stage.END;
					board.setTurn("");
					board.setStage("Winner is Player 2!");
					gui.showWinner("Player 2.");
				} else {
					stage = Stage.P1MOVE1;
					board.setTurn("Player 1");
					board.setStage("Select pawn to move");
					pawn = -1;
				}
			}
			break;
		case END:
			break;
		default:
			break;
		}
	}
	
	@SuppressWarnings("resource")
	public void load() {
		File file = new File("save.txt");
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			char data[] = new char[8];
			char d[] = new char[8];
			int numOfLines = 0;
			String s;
			while ((s = br.readLine()) != null) {
				d = s.toCharArray();
				if(numOfLines == 1) {
					int x = data[0] - 'A';
					int y = data[1] - '0'-1;
					board.setPlayer1(0, new Player(board.getTile(x, y)));
					x = data[3] - 'A';
					y = data[4] - '0'-1;
					board.setPlayer1(1, new Player(board.getTile(x, y)));
					
					stage = Stage.P2P1;
					board.setTurn("Player 2");
					board.setStage("Set first pawn");
					
				} else if (numOfLines == 2) {
					int x = data[0] - 'A';
					int y = data[1] - '0'-1;
					board.setPlayer2(0, new Player(board.getTile(x, y)));
					x = data[3] - 'A';
					y = data[4] - '0'-1;
					board.setPlayer2(1, new Player(board.getTile(x, y)));

					stage = Stage.P1MOVE1;
					board.setTurn("Player 1");
					board.setStage("Select pawn to move");
					
				} else if (numOfLines > 2) {
					int x = data[6] - 'A';
					int y = data[7] - '0'-1;
					board.getTile(x, y).build();
					x = data[0] - 'A';
					y = data[1] - '0'-1;
					int xx = data[3] - 'A';
					int yy = data[4] - '0'-1;
					board.getTile(x, y).getPlayer().move(board.getTile(xx, yy));
					
					if((board.getTile(xx, yy).getPlayer() == board.getPlayer1()[0]) || (board.getTile(xx, yy).getPlayer() == board.getPlayer1()[1])) {
						stage = Stage.P2MOVE1;
						board.setTurn("Player 2");
						board.setStage("Select pawn to move");
					} else {
						stage = Stage.P1MOVE1;
						board.setTurn("Player 1");
						board.setStage("Select pawn to move");
					}
					
				}
				data = d;
				numOfLines++;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
}
