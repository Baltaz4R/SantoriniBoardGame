package etf.santorini.dv150189d;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class TileRenderer extends JButton {
	
	public final int TILE_SIZE = 64; //in px
	public final Color background = new Color(0, 255, 51);
	public final Color level1 = new Color(204, 204, 204);
	public final Color level2 = new Color(153, 153, 153);
	public final Color level3 = new Color(102, 102, 102);
	public final Color roof = new Color(51, 153, 255);
	private ImageIcon icon = new ImageIcon(""); // image of player or else
	Tile myTile;
	
	public TileRenderer() {
		myTile = null;
		setBackground(background);
		setFocusable(false);
	}
	
	
	public void setMyTile(Tile myTile) {
		this.myTile = myTile;
	}


	public TileRenderer(String terrain) {
		switch(terrain) {
		case "level1":
			setBackground(level1);
			break;
		case "level2":
			setBackground(level2);
			break;
		case "level3":
			setBackground(level3);
			break;
		case "roof":
			setBackground(roof);
			break;
		default:
			break;
		}
		this.setFocusable(false);
	}
	
	public void draw(Color color) {
		this.setBackground(color);
	}

	/*
	 * update tile display when needed
	 */
	public void update(Board board) {
		if (myTile == null) {
			System.out.println("Nisi postavio Tile!");
			return;
		}
		Icons icons = new Icons();
		if (myTile.getPlayer()==null) {
			setIcon(icon);
		} else {
			if((myTile.getPlayer()==board.getPlayer1()[0]) || (myTile.getPlayer()==board.getPlayer1()[1])) {
				setIcon(icons.player1);
			} else {
				setIcon(icons.player2);
			}
		}
		switch(myTile.getLevel()) {
		case GROUND: 
			this.setBackground(background);
			break;
		case LEVEL1:
			setBackground(level1);
			break;
		case LEVEL2:
			setBackground(level2);
			break;
		case LEVEL3:
			setBackground(level3);
			break;
		case ROOF:
			setBackground(roof);
			break;
		default:
			break;			
		}
	}
}
