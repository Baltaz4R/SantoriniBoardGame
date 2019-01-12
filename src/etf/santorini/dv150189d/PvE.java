package etf.santorini.dv150189d;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import etf.santorini.dv150189d.Tile.BuildLevel;

@SuppressWarnings("unused")
public class PvE implements Mode, ActionListener {
	
	public enum Stage {START, P1P1, P1P2, P1MOVE1, P1MOVE2, P1BUILD, END };
	Stage stage = Stage.START;
	
	Board board;
	MainWindow gui;
	Robot robot;
	
	int difficultyLevel;
	
	public PvE(Board board, MainWindow gui, String difficultyLevel) {
		this.difficultyLevel = difficulty(difficultyLevel);
		this.board = board;
		this.gui = gui;
		this.robot = new Robot(board);
		
		board.initBoard();
		for (int i=0; i<5; i++) 
			for (int j=0; j<5; j++) {
				gui.getBoardRenderer().getTileRenderers(i, j).setMyTile(board.getTile(i, j));
			}
		gui.getBoardRenderer().setActionListener(this);	

		board.addObserver(gui.getBoardRenderer());
		board.addObserver(gui.getInfoPanel());
		
		stage = Stage.P1P1;
		board.setTurn("Player");
		board.setStage("Set first pawn");
		gui.showSelectPosition("first pawn.", "Player");
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
			robot.numOfMove++;
			stage = Stage.P1P2;
			board.setStage("Set second pawn");
			gui.showSelectPosition("second pawn.", "Player");
			break;
		case P1P2:
			tr = (TileRenderer) e.getSource();
			if (tr.myTile.player == null) {
				board.setPlayer1(1, new Player(tr.myTile));
				tr.myTile.player = board.player1[1];
				robot.numOfMove++;
				stage = Stage.P1MOVE1;
				board.setTurn("Robot");
				board.setStage("Set pawns");
				setRobot();
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
					board.setStage("Winner is Player!");
					gui.showWinner("Player.");
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
					board.setStage("Winner is Player!");
					gui.showWinner("Player.");
				} else {
					board.setTurn("Robot");
					board.setStage("Move");
					pawn = -1;
					robot.numOfMove++;
					robot.whosTurn = 2;
					moveRobot();
				}
			}
			break;
		case END:
			break;
		default:
			break;
		}
	}
	
	private void setRobot() {
		Random rand1 = new Random();
		Random rand2 = new Random();
		int x = rand1.nextInt(4);
		int y = rand2.nextInt(4);
		while((x == board.player1[0].getTile().getPosition().getX() && y == board.player1[0].getTile().getPosition().getY()) ||
				(x == board.player1[1].getTile().getPosition().getX() && y == board.player1[1].getTile().getPosition().getY())) {
			Random rand11 = new Random();
			Random rand22 = new Random();
			x = rand11.nextInt(4);
			y = rand22.nextInt(4);
		}
		robot.move((byte) 2, board.getGrid()[x][y]);
		
		Random rand3 = new Random();
		Random rand4 = new Random();
		x = rand3.nextInt(4);
		y = rand4.nextInt(4);
		while((x == board.player1[0].getTile().getPosition().getX() && y == board.player1[0].getTile().getPosition().getY()) ||
				(x == board.player1[1].getTile().getPosition().getX() && y == board.player1[1].getTile().getPosition().getY()) ||
				(x == board.player2[0].getTile().getPosition().getX() && y == board.player2[0].getTile().getPosition().getY())) {
			Random rand33 = new Random();
			Random rand44 = new Random();
			x = rand33.nextInt(4);
			y = rand44.nextInt(4);
		}
		robot.move((byte) 2, board.getGrid()[x][y]);
		board.setTurn("Player");
		board.setStage("Select pawn to move");
	}
	
	private void moveRobot() {
		int[][] move = new int[3][2];
		move = robot.GenerateMoveAB((byte) 2, difficultyLevel, null); //NESME 0 DA SE SALjE
		robot.move((byte) 2, board.getTile(move[0][0], move[0][1]));
		robot.move((byte) 2, board.getTile(move[1][0], move[1][1]));
		if(robot.endOfGame == 2) {
			stage = Stage.END;
			board.setTurn("");
			board.setStage("Winner is Robot!");
			gui.showWinner("Robot.");
		} else {
			robot.move((byte) 2, board.getTile(move[2][0], move[2][1]));
			if(robot.endOfGame == 2) {
				stage = Stage.END;
				board.setTurn("");
				board.setStage("Winner is Robot!");
				gui.showWinner("Robot.");
			} else {
				stage = Stage.P1MOVE1;
				board.setTurn("Player");
				board.setStage("Select pawn to move");
				pawn = -1;
			}
		}
	}
	
	private int difficulty(String difficultyLevel) { 
		switch(difficultyLevel) {
		case "easy":
			return 1;
		case "normal":
			return 2;
		case "hard":
			return 3;
		default:
			return -1;
		}	
	}
	
	/*@SuppressWarnings("resource")
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
					
					robot.numOfMove = numOfLines + 2;
					stage = Stage.P1MOVE1;
					board.setTurn("Robot");
					board.setStage("Set pawns");
					setRobot();
					
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
						board.setTurn("Robot");
						board.setStage("Move");
						robot.numOfMove++;
						robot.whosTurn = 2;
					} else {
						stage = Stage.P1MOVE1;
						board.setTurn("Player");
						board.setStage("Select pawn to move");
					}
					
				}
				data = d;
				numOfLines++;
			}
			if(numOfLines == 2) {
				setRobot();
			} else if ((numOfLines) > 2 && (stage == Stage.P1BUILD)) {
				moveRobot();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}*/
	
}
