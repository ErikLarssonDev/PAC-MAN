package logics;

import static constants.Constants.SCREEN_HEIGHT;
import static constants.Constants.SCREEN_WIDTH;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import logics.Pacman.Point;

/**
 * 
 * Ghosts is the superclass of all ghosts in the game. It keeps the ghost's
 * position, speed etc. We have creates methods for moving, wallhit and
 * collision with Pac Man.
 *
 */
public abstract class Ghosts {
	private Point2D position;
	private Point2D startingPos = new Point2D(440, 260);
	private int speed;
	private boolean frightened;
	private KeyCode direction = KeyCode.LEFT;
	private Image graphic;

	public Ghosts() {
		this.speed = 4;
		this.frightened = false;
	}

	public Point2D getPos() {
		return this.position;
	}

	public void setStartPos() {
		this.position = this.startingPos;
	}

	public void drawYourself(GraphicsContext gc) {
	}

	public void update(GameBoard gameboard, Pacman pacman) {
		this.move(gameboard);
		this.collide(pacman);
		this.frightenMode(pacman);
	}

	public void move(GameBoard gameboard) {
		if (direction == KeyCode.UP) {
			this.position = new Point2D(position.getX(), position.getY() - speed);
			if (this.wall(gameboard)) {
				this.direction = this.newDirection();
				this.position = new Point2D(position.getX(), position.getY() + speed);
			}
		} else if (direction == KeyCode.DOWN) {
			this.position = new Point2D(position.getX(), position.getY() + speed);
			if (this.wall(gameboard)) {
				this.direction = this.newDirection();
				this.position = new Point2D(position.getX(), position.getY() - speed);
			}
		} else if (direction == KeyCode.LEFT) {
			this.position = new Point2D(position.getX() - speed, position.getY());
			if (this.wall(gameboard)) {
				this.direction = this.newDirection();
				this.position = new Point2D(position.getX() + speed, position.getY());
			}
		} else if (direction == KeyCode.RIGHT) {
			this.position = new Point2D(position.getX() + speed, position.getY());
			if (this.wall(gameboard)) {
				this.direction = this.newDirection();
				this.position = new Point2D(position.getX() - speed, position.getY());
			}
		}
	}

	public boolean wall(GameBoard gameboard) {

		if (direction == KeyCode.RIGHT) {
			if (gameboard.getPoint(position.getX() + SCREEN_WIDTH / 30 / 2,
					position.getY() - SCREEN_HEIGHT / 30 / 2 + speed) == 0) {
				return true;
			}
			if (gameboard.getPoint(position.getX() + SCREEN_WIDTH / 30 / 2,
					position.getY() + SCREEN_HEIGHT / 30 / 2 - speed) == 0) {
				return true;
			}
		}

		if (direction == KeyCode.LEFT) {
			if (gameboard.getPoint(position.getX() - SCREEN_WIDTH / 30 / 2,
					position.getY() - SCREEN_HEIGHT / 30 / 2 + speed) == 0) {
				return true;
			}
			if (gameboard.getPoint(position.getX() - SCREEN_WIDTH / 30 / 2,
					position.getY() + SCREEN_HEIGHT / 30 / 2 - speed) == 0) {
				return true;
			}
		}

		if (direction == KeyCode.UP) {
			if (gameboard.getPoint(position.getX() - SCREEN_WIDTH / 30 / 2 + speed,
					position.getY() - SCREEN_HEIGHT / 30 / 2) == 0) {
				return true;
			}
			if (gameboard.getPoint(position.getX() + SCREEN_WIDTH / 30 / 2 - speed,
					position.getY() - SCREEN_HEIGHT / 30 / 2) == 0) {
				return true;
			}
		}

		if (direction == KeyCode.DOWN) {
			if (gameboard.getPoint(position.getX() - SCREEN_WIDTH / 30 / 2 + speed,
					position.getY() + SCREEN_HEIGHT / 30 / 2) == 0) {
				return true;
			}
			if (gameboard.getPoint(position.getX() + SCREEN_WIDTH / 30 / 2 - speed,
					position.getY() + SCREEN_HEIGHT / 30 / 2) == 0) {
				return true;
			}
		}

		return false;
	}

	public KeyCode newDirection() {
		Random rand = new Random();
		int direc = rand.nextInt(4);
		if (direc == 0) {
			return KeyCode.UP;
		} else if (direc == 1) {
			return KeyCode.DOWN;
		} else if (direc == 2) {
			return KeyCode.LEFT;
		} else {
			return KeyCode.RIGHT;
		}
	}

	public void collide(Pacman pacman) {
		if ((int) Math.round(this.position.getX() / SCREEN_WIDTH * 30.0) == (int) Math
				.round(pacman.getPos().x / SCREEN_WIDTH * 30.0)
				&& (int) Math.round(this.position.getY() / SCREEN_HEIGHT * 30.0) == (int) Math
						.round(pacman.getPos().y / SCREEN_HEIGHT * 30.0)) {
			if (!this.frightened) {
				pacman.setStartPos();
				pacman.setLives(pacman.getLives() - 1);
			} else if (this.frightened) {
				this.frightened = false;
				this.position = new Point2D(440, 260);
				pacman.setScore(pacman.getScore() + 200);
			}

		}
	}

	public void frightenMode(Pacman pacman) {
		if (pacman.getPowerUp()) {
			this.frightened = true;
		}
	}
}
