package etf.santorini.dv150189d;

import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class InfoPanel extends JPanel implements Observer {
	
	// player state info
	private final JPanel playerInfoPanel = new JPanel();
	private final JLabel tTurn = new JLabel("Turn:");
	private final JLabel tStage = new JLabel("Stage:");
	// creating the label with the actual value of the variables
	private JLabel vTurn = new JLabel("");
	private JLabel vStage= new JLabel("");
	
	public InfoPanel() {
		setLayout(new GridLayout(1,2));
		add(makePlayerInfoPanel());
		add(makeMessagePanel());
	}
	
	public JPanel makeMessagePanel() {
		JPanel panel = new JPanel();
		panel.setBackground(MainWindow.color2);
		panel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, MainWindow.color2));
		return panel;
	}
	
	public JPanel makePlayerInfoPanel() {
		JPanel panel = playerInfoPanel;
		panel.setLayout(new GridLayout(2,2)); // Koliko po duzini i siriini
		panel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, MainWindow.color1));
		panel.setBackground(MainWindow.color1);
		panel.setForeground(MainWindow.color3);
		
		panel.add(tTurn);
		panel.add(vTurn);
		panel.add(tStage);
		panel.add(vStage);
		return panel;
	}
	
	/*
	 * update player info after each turn 
	 */
	@Override
	public void update(Observable observable, Object object) {
		Board board = (Board) observable;
		if (object==null) {
			this.setVTurn(board.Turn);
			this.setVStage(board.Stage);
		}
	}
	
	public void setVTurn(String value) {
		this.vTurn.setText(value);
	}
	
	public void setVStage(String value) {
		this.vStage.setText(value);
	}
	
}
