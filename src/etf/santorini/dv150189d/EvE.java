package etf.santorini.dv150189d;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;

public class EvE implements Mode, ActionListener {
	
	public enum Stage {START, P1, P2, MOVE1, MOVE2, END };
	Stage stage = Stage.START;
	
	Board board;
	MainWindow gui;
	Robot robot;
	
	int difficultyLevel;
	
	public EvE(Board board, MainWindow gui, String difficultyLevel, int mode) {
		this.difficultyLevel = difficulty(difficultyLevel);
		this.board = board;
		this.gui = gui;
		this.robot = new Robot(board);
		
		board.initBoard();
		for (int i=0; i<5; i++) 
			for (int j=0; j<5; j++) {
				gui.getBoardRenderer().getTileRenderers(i, j).setMyTile(board.getTile(i, j));
			}

		board.addObserver(gui.getBoardRenderer());
		board.addObserver(gui.getInfoPanel());


		stage = Stage.P1;
		board.setTurn("Robot 1");
		board.setStage("Set pawns");
		
		if(mode==0) {
			gui.getBoardRenderer().setActionListener(this);	
			try {
				writer = new PrintWriter("save.txt", "UTF-8");
			} catch (FileNotFoundException | UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} else if(mode == 1) {
			start();
		} else {
			System.out.println("Nepostojeci mod za EvE igru!");
		}
	}
	
	private PrintWriter writer;
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(stage) {
		case P1:
			Random rand1 = new Random();
			Random rand2 = new Random();
			int x = rand1.nextInt(4);
			int y = rand2.nextInt(4);
			
			robot.move((byte) 1, board.getGrid()[x][y]);
			
			writer.print(print(x) + (y+1) + " ");
			
			Random rand3 = new Random();
			Random rand4 = new Random();
			x = rand3.nextInt(4);
			y = rand4.nextInt(4);
			while(x == board.player1[0].getTile().getPosition().getX() && y == board.player1[0].getTile().getPosition().getY()) {
				Random rand33 = new Random();
				Random rand44 = new Random();
				x = rand33.nextInt(4);
				y = rand44.nextInt(4);
			}
			robot.move((byte) 1, board.getGrid()[x][y]);
			
			writer.println(print(x) + (y+1));
			
			board.setTurn("Robot 2");
			board.setStage("Set pawns");
			stage = Stage.P2;
			writer.flush();
			break;
		case P2:
			Random rand5 = new Random();
			Random rand6 = new Random();
			x = rand5.nextInt(4);
			y = rand6.nextInt(4);
			while((x == board.player1[0].getTile().getPosition().getX() && y == board.player1[0].getTile().getPosition().getY()) ||
					(x == board.player1[1].getTile().getPosition().getX() && y == board.player1[1].getTile().getPosition().getY())) {
				Random rand55 = new Random();
				Random rand66 = new Random();
				x = rand55.nextInt(4);
				y = rand66.nextInt(4);
			}
			robot.move((byte) 2, board.getGrid()[x][y]);
			
			writer.print(print(x) + (y+1) + " ");
			
			Random rand7 = new Random();
			Random rand8 = new Random();
			x = rand7.nextInt(4);
			y = rand8.nextInt(4);
			while((x == board.player1[0].getTile().getPosition().getX() && y == board.player1[0].getTile().getPosition().getY()) ||
					(x == board.player1[1].getTile().getPosition().getX() && y == board.player1[1].getTile().getPosition().getY()) ||
					(x == board.player2[0].getTile().getPosition().getX() && y == board.player2[0].getTile().getPosition().getY())) {
				Random rand77 = new Random();
				Random rand88 = new Random();
				x = rand77.nextInt(4);
				y = rand88.nextInt(4);
			}
			robot.move((byte) 2, board.getGrid()[x][y]);
			
			writer.println(print(x) + (y+1));
			
			board.setTurn("Robot 1");
			board.setStage("Select pawn to move");
			stage = Stage.MOVE1;
			writer.flush();
			break;
		case MOVE1:
			int[][] move = new int[3][2];
			move = robot.GenerateMoveAB((byte) 1, difficultyLevel, gui); //NESME 0 DA SE SALjE
			robot.move((byte) 1, board.getTile(move[0][0], move[0][1]));
			
			writer.print(print(move[0][0]) + (move[0][1] + 1) + " ");
			
			robot.move((byte) 1, board.getTile(move[1][0], move[1][1]));
			
			writer.print(print(move[1][0]) + (move[1][1] + 1) + " ");
			
			if(robot.endOfGame == 1) {
				stage = Stage.END;
				board.setTurn("");
				board.setStage("Winner is Robot 1!");
				gui.showWinner("Robot 1.");
			} else {
				robot.move((byte) 1, board.getTile(move[2][0], move[2][1]));
				
				writer.println(print(move[2][0]) + (move[2][1] + 1));
				
				if(robot.endOfGame == 1) {
					stage = Stage.END;
					board.setTurn("");
					board.setStage("Winner is Robot 1!");
					gui.showWinner("Robot 1.");
				} else {
					stage = Stage.MOVE2;
					board.setTurn("Robot 2");
					board.setStage("Select pawn to move");
				}
			}
			writer.flush();
			break;
		case MOVE2:
			move = new int[3][2];
			move = robot.GenerateMoveAB((byte) 2, difficultyLevel, gui); //NESME 0 DA SE SALjE
			robot.move((byte) 2, board.getTile(move[0][0], move[0][1]));
			
			writer.print(print(move[0][0]) + (move[0][1] + 1) + " ");
			
			robot.move((byte) 2, board.getTile(move[1][0], move[1][1]));
			
			writer.print(print(move[1][0]) + (move[1][1] + 1) + " ");
			
			if(robot.endOfGame == 2) {
				stage = Stage.END;
				board.setTurn("");
				board.setStage("Winner is Robot 2!");
				gui.showWinner("Robot 2.");
			} else {
				robot.move((byte) 2, board.getTile(move[2][0], move[2][1]));
				
				writer.println(print(move[2][0]) + (move[2][1] + 1));
				
				if(robot.endOfGame == 2) {
					stage = Stage.END;
					board.setTurn("");
					board.setStage("Winner is Robot 2!");
					gui.showWinner("Robot 2.");
				} else {
					stage = Stage.MOVE1;
					board.setTurn("Robot 1");
					board.setStage("Select pawn to move");
				}
			}
			writer.flush();
			break;
		case END:
			writer.close();
			break;
		default:
			break;
		}
	}
	
	private void start() {
		try {
			PrintWriter writer = new PrintWriter("save.txt", "UTF-8");
		while(stage != Stage.END) {
			switch(stage) {
			case P1:
				Random rand1 = new Random();
				Random rand2 = new Random();
				int x = rand1.nextInt(4);
				int y = rand2.nextInt(4);
				
				robot.move((byte) 1, board.getGrid()[x][y]);
				
				writer.print(print(x) + (y+1) + " ");
				
				Random rand3 = new Random();
				Random rand4 = new Random();
				x = rand3.nextInt(4);
				y = rand4.nextInt(4);
				while(x == board.player1[0].getTile().getPosition().getX() && y == board.player1[0].getTile().getPosition().getY()) {
					Random rand33 = new Random();
					Random rand44 = new Random();
					x = rand33.nextInt(4);
					y = rand44.nextInt(4);
				}
				robot.move((byte) 1, board.getGrid()[x][y]);
				
				writer.println(print(x) + (y+1));
				
				board.setTurn("Robot 2");
				board.setStage("Set pawns");
				stage = Stage.P2;
				writer.flush();
				break;
			case P2:
				Random rand5 = new Random();
				Random rand6 = new Random();
				x = rand5.nextInt(4);
				y = rand6.nextInt(4);
				while((x == board.player1[0].getTile().getPosition().getX() && y == board.player1[0].getTile().getPosition().getY()) ||
						(x == board.player1[1].getTile().getPosition().getX() && y == board.player1[1].getTile().getPosition().getY())) {
					Random rand55 = new Random();
					Random rand66 = new Random();
					x = rand55.nextInt(4);
					y = rand66.nextInt(4);
				}
				robot.move((byte) 2, board.getGrid()[x][y]);
				
				writer.print(print(x) + (y+1) + " ");
				
				Random rand7 = new Random();
				Random rand8 = new Random();
				x = rand7.nextInt(4);
				y = rand8.nextInt(4);
				while((x == board.player1[0].getTile().getPosition().getX() && y == board.player1[0].getTile().getPosition().getY()) ||
						(x == board.player1[1].getTile().getPosition().getX() && y == board.player1[1].getTile().getPosition().getY()) ||
						(x == board.player2[0].getTile().getPosition().getX() && y == board.player2[0].getTile().getPosition().getY())) {
					Random rand77 = new Random();
					Random rand88 = new Random();
					x = rand77.nextInt(4);
					y = rand88.nextInt(4);
				}
				robot.move((byte) 2, board.getGrid()[x][y]);
				
				writer.println(print(x) + (y+1));
				
				board.setTurn("Robot 1");
				board.setStage("Select pawn to move");
				stage = Stage.MOVE1;
				writer.flush();
				break;
			case MOVE1:
				int[][] move = new int[3][2];
				move = robot.GenerateMoveAB((byte) 1, difficultyLevel, null); //NESME 0 DA SE SALjE
				robot.move((byte) 1, board.getTile(move[0][0], move[0][1]));
				
				writer.print(print(move[0][0]) + (move[0][1] + 1) + " ");
				
				robot.move((byte) 1, board.getTile(move[1][0], move[1][1]));
				
				writer.print(print(move[1][0]) + (move[1][1] + 1) + " ");
				
				if(robot.endOfGame == 1) {
					stage = Stage.END;
					board.setTurn("");
					board.setStage("Winner is Robot 1!");
					gui.showWinner("Robot 1.");
				} else {
					robot.move((byte) 1, board.getTile(move[2][0], move[2][1]));
					
					writer.println(print(move[2][0]) + (move[2][1] + 1));
					
					if(robot.endOfGame == 1) {
						stage = Stage.END;
						board.setTurn("");
						board.setStage("Winner is Robot 1!");
						gui.showWinner("Robot 1.");
					} else {
						stage = Stage.MOVE2;
						board.setTurn("Robot 2");
						board.setStage("Select pawn to move");
					}
				}
				writer.flush();
				break;
			case MOVE2:
				move = new int[3][2];
				move = robot.GenerateMoveAB((byte) 2, difficultyLevel, null); //NESME 0 DA SE SALjE
				robot.move((byte) 2, board.getTile(move[0][0], move[0][1]));
				
				writer.print(print(move[0][0]) + (move[0][1] + 1) + " ");
				
				robot.move((byte) 2, board.getTile(move[1][0], move[1][1]));
				
				writer.print(print(move[1][0]) + (move[1][1] + 1) + " ");
				
				if(robot.endOfGame == 2) {
					stage = Stage.END;
					board.setTurn("");
					board.setStage("Winner is Robot 2!");
					gui.showWinner("Robot 2.");
				} else {
					robot.move((byte) 2, board.getTile(move[2][0], move[2][1]));
					
					writer.println(print(move[2][0]) + (move[2][1] + 1));
					
					if(robot.endOfGame == 2) {
						stage = Stage.END;
						board.setTurn("");
						board.setStage("Winner is Robot 2!");
						gui.showWinner("Robot 2.");
					} else {
						stage = Stage.MOVE1;
						board.setTurn("Robot 2");
						board.setStage("Select pawn to move");
					}
				}
				writer.flush();
				break;
			default:
				break;
			}
		}
		
		writer.close();
		
		} catch (FileNotFoundException | UnsupportedEncodingException e1) {
			e1.printStackTrace();
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
	
	private String print(int x) {
		switch(x) {
		case 0:
			return "A";
		case 1:
			return "B";
		case 2:
			return "C";
		case 3:
			return "D";
		case 4:
			return "E";
		default:
			return "default";
		}
	}
	
}
