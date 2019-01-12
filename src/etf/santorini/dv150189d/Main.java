package etf.santorini.dv150189d;

public class Main {

	public static void main(String[] args) {
		Board board = new Board();
		MainWindow gui = new MainWindow();
		@SuppressWarnings("unused")
		Controller controller = new Controller(board, gui);
	}

}