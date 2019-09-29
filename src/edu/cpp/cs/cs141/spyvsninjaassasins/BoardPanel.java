package edu.cpp.cs.cs141.spyvsninjaassasins;

import java.awt.*;
import javax.swing.*;

public class BoardPanel extends JPanel {
	private static final long serialVersionUID = 4273194517553828174L;
	private JLabel[][] board = new JLabel[11][11];

	public BoardPanel(GameEngine game) {
		setLayout(new GridLayout(11, 11));
		setOpaque(false);
		createBoard();
		setBoard(game);

		for (int i = 0; i < 11; ++i) {
			for (int j = 0; j < 11; ++j) {
				add(board[i][j]);
			}
		}
	}

	public void createBoard() {
		for (int i = 0; i < 11; ++i) {
			for (int j = 0; j < 11; ++j) {
				board[i][j] = new JLabel();
			}
		}
	}

	public void setBoard(GameEngine game) {
		ImageIcon empty = new ImageIcon("res/empty.png");

		for (int i = 0; i < 11; ++i) {
			for (int j = 0; j < 11; ++j) {
				if (i == 0 || i == 10) {
					board[i][j].setIcon(empty);
				} else if (j == 0 || j == 10) {
					board[i][j].setIcon(empty);
				} else if (!game.checkExists(i - 1, j - 1)) {
					board[i][j].setIcon(getSprite(i - 1, j - 1, 'N', game));
				} else if (game.isVisible(i - 1, j - 1)) {
					board[i][j].setIcon(getSprite(i - 1, j - 1, game.getType(i - 1, j - 1), game));
				} else {
					board[i][j].setIcon(getSprite(i - 1, j - 1, 'N', game));
				}
				// System.out.print("[" + game.getType(i-1, j-1) + "]");
			}
			// System.out.println("");
		}
	}

	private Icon getSprite(int row, int col, char type, GameEngine game) {
		ImageIcon sprite = new ImageIcon("res/empty.png");
		switch(type) {
		case 'P':
			if((row == 1 || row == 4 || row == 7) && (col == 1 || col == 4 || col == 7))
				sprite = new ImageIcon("res/room_occupied.png");
			else {
				switch(game.getDirection(row, col)) {
				case 'u':
					sprite = new ImageIcon("res/frisk_up.gif");
					break;
				case 'd':
					sprite = new ImageIcon("res/frisk_down.gif");
					break;
				case 'l':
					sprite = new ImageIcon("res/frisk_left.gif");
					break;
				case 'r':
					sprite = new ImageIcon("res/frisk_right.gif");
					break;
				}
			}
			break;
		case 'E':
			switch(game.getDirection(row, col)) {
			case 'u':
				sprite = new ImageIcon("res/sans_up.gif");
				break;
			case 'd':
				sprite = new ImageIcon("res/sans_down.gif");
				break;
			case 'l':
				sprite = new ImageIcon("res/sans_left.gif");
				break;
			case 'r':
				sprite = new ImageIcon("res/sans_right.gif");
				break;
			}
			break;
		case 'A':
			sprite = new ImageIcon("res/bullets.png");
			break;
		case 'L':
			sprite = new ImageIcon("res/map.png");
			break;
		case 'S':
			sprite = new ImageIcon("res/artifact.png");
			break;
		case 'R':
			sprite = new ImageIcon("res/empty.png");
			break;
		case 'B':
			sprite = new ImageIcon("res/room_located.png");
			break;
		}
		return sprite;	
	}
}