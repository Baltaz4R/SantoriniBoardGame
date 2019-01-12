package etf.santorini.dv150189d;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MenuPanel extends JPanel {
	
	private final MyButton newGame = new MyButton("New Game");
	private final MyButton load = new MyButton("Load");
	private final MyButton exit = new MyButton("Exit");
	
	private ActionListener actionListener;
	
	public MenuPanel() {
		setLayout(new FlowLayout());
		setBackground(MainWindow.color2);
		add(newGame);
		add(load);
		add(exit);
	}
	
	public ActionListener getActionListener() {
		return actionListener;
	}
	
	public void setActionListener(ActionListener actionListener) {
		this.actionListener = actionListener;
		newGame.addActionListener(actionListener);
		load.addActionListener(actionListener);
		exit.addActionListener(actionListener);
	}
	
}
