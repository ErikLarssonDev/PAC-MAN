package logics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.lang.Math;
import java.util.ArrayList;

import static constants.Constants.SCREEN_HEIGHT;
import static constants.Constants.SCREEN_WIDTH;

/**
 * This class is responsible for the gameboard. In this class we create the
 * gameboard layout, open/close the jail and you can check if there are any dots
 * left on the gameboard. The gameboard is essentially an 2d array that stores
 * integers. A 0 represents a wall, 1 represents food/dot, 2 represents empty
 * path, 3 represents our powerup.
 *
 */
public class GameBoard {
	private int[][] board = new int[30][30];
	private int jailOpen = 0;

	public GameBoard() {
		reset();
	}

	public Integer getPoint(double x, double y) {
		return board[(int) Math.round(y / SCREEN_HEIGHT * 30.0)][(int) Math.round(x / SCREEN_WIDTH * 30.0)];
	}

	public void setPoint(double x, double y, int value) {
		board[(int) Math.round(y / SCREEN_HEIGHT * 30.0)][(int) Math.round(x / SCREEN_WIDTH * 30.0)] = value;
	}

	public void setBox(int posX, int posY, int sizeX, int sizeY, int type) {
		for (int row = posY; row < sizeY; row++) {
			for (int column = posX; column < sizeX; column++) {
				board[row][column] = type;
			}
		}
	}

	public void update(ArrayList<Ghosts> ghosts) {
		jailOpen += 1;
		if (jailOpen == 150) {
			this.openJail();
		}
		for (int i = 0; i < ghosts.size(); i++) {
			if (ghosts.get(i).getPos().getX() / SCREEN_WIDTH * 30 <= 24
					&& ghosts.get(i).getPos().getX() / SCREEN_WIDTH * 30 >= 22
					&& ghosts.get(i).getPos().getY() / SCREEN_HEIGHT * 30 == 10) {
				this.closeJail();
				jailOpen = 0;
			}
		}

	}

	public void drawGameBoard() {
		for (int row = 0; row < 30; row++) {
			for (int column = 0; column < 30; column++) {
				System.out.print(" " + board[row][column]);
			}
			System.out.println();
		}
	}

	public void drawYourself(GraphicsContext g) {

		for (int row = 0; row < 30; row++) {
			for (int column = 0; column < 30; column++) {
				if (board[row][column] == 0) {
					g.setFill(Color.BLUE);
					g.fillRect(SCREEN_WIDTH / 30 * column - SCREEN_WIDTH / 30 / 2,
							SCREEN_HEIGHT / 30 * row - SCREEN_HEIGHT / 30 / 2, SCREEN_WIDTH / 30, SCREEN_HEIGHT / 30);
				} else if (board[row][column] == 1) {
					g.setFill(Color.GOLD);
					g.fillOval(SCREEN_WIDTH / 30 * column - SCREEN_WIDTH / 30 / 4,
							SCREEN_HEIGHT / 30 * row - SCREEN_HEIGHT / 30 / 4, SCREEN_WIDTH / 30 / 2,
							SCREEN_HEIGHT / 30 / 2);

				} else if (board[row][column] == 2) {
					g.setFill(Color.BLACK);
					g.fillRect(SCREEN_WIDTH / 30 * column - SCREEN_WIDTH / 30 / 2,
							SCREEN_HEIGHT / 30 * row - SCREEN_HEIGHT / 30 / 2, SCREEN_WIDTH / 30, SCREEN_HEIGHT / 30);
				} else if (board[row][column] == 3) {
					g.setFill(Color.RED);
					g.fillOval(SCREEN_WIDTH / 30 * column - SCREEN_WIDTH / 30 / 4,
							SCREEN_HEIGHT / 30 * row - SCREEN_HEIGHT / 30 / 4, SCREEN_WIDTH / 30 / 2,
							SCREEN_HEIGHT / 30 / 2);
				}

			}
		}

	}

	public void openJail() {
		this.setBox(22, 10, 24, 11, 2);
	}

	public void closeJail() {
		this.setBox(22, 10, 24, 11, 0);
	}

	public void reset() {
		this.setBox(0, 0, 30, 30, 0);
		this.setBox(1, 1, 29, 29, 1);
		this.setBox(2, 2, 28, 9, 0);
		this.setBox(2, 10, 6, 28, 0);
		this.setBox(2, 24, 12, 28, 0);
		this.setBox(7, 10, 12, 23, 0);
		this.setBox(13, 10, 17, 14, 0);
		this.setBox(13, 15, 17, 28, 0);
		this.setBox(18, 10, 22, 28, 0);
		this.setBox(24, 10, 28, 28, 0);
		this.setBox(18, 24, 28, 28, 0);
		this.setBox(22, 10, 24, 24, 2);
		this.setBox(22, 10, 24, 11, 0);
		this.setPowerUps();
	}

	public boolean checkDots() {
		for (int row = 0; row < 30; row++) {
			for (int column = 0; column < 30; column++) {
				if (this.board[row][column] == 1) {
					return true;
				}
			}
		}
		return false;
	}

	public void setPowerUps() {
		this.board[28][1] = 3;
		this.board[28][28] = 3;
		this.board[1][28] = 3;
		this.board[1][1] = 3;
	}
}

/**
 * public static void main(String[] args) { GameBoard test; test = new
 * GameBoard(); test.drawGameBoard(); } }
 */

/**
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 1 1 1 1 1 1 1
 * 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 * 0 0 0 0 0 0 0 0 0 0 1 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 * 0 1 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 1 0 0 0 0
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 * 0 0 0 0 1 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 1 1
 * 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 0 0 1 0 0 0 0 1 0 0 0 0 0
 * 1 0 0 0 0 1 0 0 0 0 1 1 0 0 0 0 1 0 0 1 0 0 0 0 1 0 0 0 0 0 1 0 0 0 0 1 0 0 0
 * 0 1 1 0 0 0 0 1 0 0 1 0 0 0 0 1 0 0 0 0 0 1 0 0 0 0 1 0 0 0 0 1 1 0 0 0 0 1 0
 * 0 1 0 0 0 0 1 0 0 0 0 0 1 0 0 0 0 1 0 0 0 0 1 1 0 0 0 0 1 0 0 1 0 0 0 0 1 0 0
 * 0 0 0 1 1 1 1 1 1 0 0 0 0 1 1 0 0 0 0 1 0 0 1 0 0 0 0 1 0 0 0 0 0 1 0 0 0 0 1
 * 0 0 0 0 1 1 0 0 0 0 1 0 0 1 0 0 0 0 1 0 0 0 0 0 1 0 0 0 0 1 0 0 0 0 1 1 0 0 0
 * 0 1 0 0 1 0 0 0 0 1 0 0 0 0 0 1 0 0 0 0 1 0 0 0 0 1 1 0 0 0 0 1 0 0 1 0 0 0 0
 * 1 0 0 0 0 0 1 0 0 0 0 1 0 0 0 0 1 1 0 0 0 0 1 0 0 1 0 0 0 0 1 0 0 0 0 0 1 0 0
 * 0 0 1 0 0 0 0 1 1 0 0 0 0 1 0 0 1 0 0 0 0 1 0 0 0 0 0 1 0 0 0 0 1 0 0 0 0 1 1
 * 0 0 0 0 1 0 0 1 0 0 0 0 1 0 0 0 0 0 1 0 0 0 0 1 0 0 0 0 1 1 0 0 0 0 1 0 0 1 0
 * 0 0 0 1 0 0 0 0 0 1 0 0 0 0 1 0 0 0 0 1 1 0 0 0 0 1 0 0 1 0 0 0 0 1 1 1 1 1 1
 * 1 0 0 0 0 1 0 0 0 0 1 1 0 0 0 0 1 0 0 1 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 1 0 0 0
 * 0 0 0 0 0 0 0 1 0 0 1 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 1 0
 * 0 1 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 1 0 0 1 0 0 0 0 0 0 0
 * 0 0 0 1 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 1 0 0 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1
 * 1 1 1 1 1 1 1 1 1 1 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 * 0 0 0
 */
