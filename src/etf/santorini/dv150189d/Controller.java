package etf.santorini.dv150189d;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {

	Board board;
	MainWindow gui;
	Mode mode;
	
	/*
	 * link view and model when constructing controller
	 */
	
	public Controller(Board board, MainWindow gui) {
		this.board = board;
		this.gui = gui;
		String askmode = gui.askMode();
		String difficultyLevel;
		switch (askmode) {
		case "PvP":
			difficultyLevel = "/";
			mode = new PvP(board, gui);
			break;
		case "PvE":
			difficultyLevel = gui.askDifficulty();
			mode = new PvE(board, gui, difficultyLevel);
			break;
		case "EvE":
			difficultyLevel = gui.askDifficulty();
			int m = gui.askEvEMode();
			mode = new EvE(board, gui, difficultyLevel, m);
			break;
		default:
			break;
		}
		gui.getMenuPanel().setActionListener(this);
		
	}
	
	/*
	 * handle pressed button from the view
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		
		switch(action) {
		case "New Game":
			String askmode = gui.askMode();
			String difficultyLevel;
			switch (askmode) {
			case "PvP":
				difficultyLevel = "/";
				mode = new PvP(board, gui);
				break;
			case "PvE":
				difficultyLevel = gui.askDifficulty();
				mode = new PvE(board, gui, difficultyLevel);
				break;
			case "EvE":
				difficultyLevel = gui.askDifficulty();
				int m = gui.askEvEMode();
				mode = new EvE(board, gui, difficultyLevel, m);
				break;
			default:
				break;
			}
			break;
		case "Load":
			String askmode1 = gui.askMode();
			String difficultyLevel1;
			switch (askmode1) {
			case "PvP":
				difficultyLevel1 = "/";
				mode = new PvP(board, gui);
				((PvP) mode).load();
				break;
			case "PvE":
				difficultyLevel1 = gui.askDifficulty();
				mode = new PvE(board, gui, difficultyLevel1);
				/*((PvE) mode).load();*/
				break;
			case "EvE":
				difficultyLevel1 = gui.askDifficulty();
				int m = gui.askEvEMode();
				mode = new EvE(board, gui, difficultyLevel1, m);
				break;
			default:
				break;
			}
			break;
		case "Exit": 	
			gui.askExitConfirmation();
			break;
		default:
			break;
		}
		gui.requestFocus(); 
	}
    

}