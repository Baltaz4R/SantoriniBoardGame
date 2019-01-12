package etf.santorini.dv150189d;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	
	private final String NAME = "Santorini";
	
	protected final static Color color1 = new Color(51, 204, 255); // DUGMAD
	protected final static Color color2 = new Color(0, 0, 153); // POZADINA
	protected final static Color color3 = new Color(5, 25, 56); // TEKST
	
	private BoardRenderer boardRenderer = new BoardRenderer(); // with map
	private MenuPanel menuPanel = new MenuPanel();
	private InfoPanel infoPanel = new InfoPanel(); //with player info
	
	public MainWindow() {
		super();
		Icons icons = new Icons();
		setIconImage(icons.main.getImage());
		setTitle(NAME);
		setLayout(new BorderLayout());
		add(boardRenderer, BorderLayout.CENTER);
		add(menuPanel, BorderLayout.NORTH);
		add(infoPanel, BorderLayout.SOUTH);
		pack();
        setMinimumSize(getSize());
        setFocusable(true);
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setFocusable(true);
		requestFocus();      	
		setLocationRelativeTo(null); 
		setVisible(true);
		}
	
	/*
	 * show dialog box to ask user for exit confirmation
	 */
	public void askExitConfirmation() {
		int choice = JOptionPane.showConfirmDialog(this, "Do you really want to close ?", "Confirm exit ? ", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (choice==JOptionPane.YES_OPTION) {
			// close and clean everything
			dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}
	}
	
	/*
	 * show dialog box 
	 */
	public void showSelectPosition(String pawn, String player) {
		String message = "Select start position for "
						+ pawn;
		JOptionPane.showMessageDialog(this, message, player, JOptionPane.INFORMATION_MESSAGE);
	}
	
	/*
	 * show dialog box when game is finished
	 */
	public void showWinner(String player) {
		String message = "Winner is "
						+ player;
		JOptionPane.showMessageDialog(this, message, "The End", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/*
	 * show dialog box to ask user for difficulty
	 */
	public String askDifficulty() {
		String[] options = {"easy", "normal", "hard"};
		int buttonPressed;
    	buttonPressed = JOptionPane.showOptionDialog(this, "Please choose the difficulty.", "Welcome to the Santorini", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
    	return options[buttonPressed];
	}
	
	/*
	 * show dialog box to ask user for mode
	 */
	public String askMode() {
		String[] options = {"PvP", "PvE", "EvE"};
		int buttonPressed;
    	buttonPressed = JOptionPane.showOptionDialog(this, "Please choose the mode.", "Welcome to the Santorini", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
    	return options[buttonPressed];
	}
	
	public int askEvEMode() {
		String[] options = {"step by step", "skip to the end"};
		int buttonPressed;
    	buttonPressed = JOptionPane.showOptionDialog(this, "Please choose the mode.", "Welcome to the Santorini EvE", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
    	return buttonPressed;
	}
	
	public BoardRenderer getBoardRenderer() {
		return boardRenderer;
	}
	
	public InfoPanel getInfoPanel() {
		return infoPanel;
	}
	
	public MenuPanel getMenuPanel() {
		return menuPanel;
	}
}
