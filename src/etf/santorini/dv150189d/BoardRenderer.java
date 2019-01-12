package etf.santorini.dv150189d;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class BoardRenderer extends JPanel implements Observer {
	
	private final int NUMBER_CELLS_PER_SIDE = 5;
	private final int CELL_SIZE = 64; //in px
	private final int BOARD_SIZE = CELL_SIZE*NUMBER_CELLS_PER_SIDE;
	
	TileRenderer[][] tileRenderers = new TileRenderer[NUMBER_CELLS_PER_SIDE][NUMBER_CELLS_PER_SIDE];
	private Icons icons = new Icons();
	
	private ActionListener actionListener;
	
	Board myBoard;

	public BoardRenderer() {
		setPreferredSize(new Dimension(BOARD_SIZE, BOARD_SIZE));
		setLayout(new GridLayout(NUMBER_CELLS_PER_SIDE, NUMBER_CELLS_PER_SIDE));
		setBackground(MainWindow.color3);
		for (int i = 0; i < NUMBER_CELLS_PER_SIDE; i++) {
	 	 	for (int j = 0; j < NUMBER_CELLS_PER_SIDE; j++) {
				tileRenderers[i][j] = new TileRenderer();
				add(tileRenderers[i][j]);
			}
		}
	}
	
	public void setMyBoard(Board myBoard) {
		this.myBoard = myBoard;
	}



	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	/*
	 * update the board display when turn is played
	 */
	public void update(Observable observable, Object object) {
		Board board = (Board) observable;
		if (object==null) {
			for (int i=0; i<NUMBER_CELLS_PER_SIDE; i++) {
		 	 	for (int j=0; j<NUMBER_CELLS_PER_SIDE; j++) {
	 	 			tileRenderers[i][j].update(board);
	 	 		}
	 	 	}
		}
	}
	
	public Icons getIcons() {
		return icons;
	}
	
	public ActionListener getActionListener() {
		return actionListener;
	}
	
	public void setActionListener(ActionListener actionListener) {
		this.actionListener = actionListener;
		for (int i = 0; i < NUMBER_CELLS_PER_SIDE; i++) 
	 	 	for (int j = 0; j < NUMBER_CELLS_PER_SIDE; j++) {
				tileRenderers[i][j].addActionListener(actionListener);
			}
	}

	public TileRenderer getTileRenderers(int i, int j) {
		return tileRenderers[i][j];
	}
}