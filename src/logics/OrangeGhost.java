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
import javafx.scene.paint.Color;
import logics.Pacman.Point;

/**
 * This class is for the orange ghost. It keeps the ghost position, speed etc. We have created methods so the ghost can move, change to scared mode and collide with the player.
 * The orange ghost chases after the player by changing direction towards the player if on the same x or y coordinate.
 *
 */
public class OrangeGhost extends Ghosts {
	private Point2D position;
	private double speed = 1.5;
	private boolean frightened;
	private KeyCode direction = KeyCode.DOWN;
	private Image normalGraphic;
	private Image frightenedGraphic;
	private Point2D startingPos = new Point2D(440, 260);

	public OrangeGhost() {
		super();
		try {
			this.normalGraphic = new Image(new FileInputStream("orange.png"), SCREEN_WIDTH / 30, SCREEN_HEIGHT / 30,
					false, false);
			this.frightenedGraphic = new Image(new FileInputStream("scared.png"), SCREEN_WIDTH / 30, SCREEN_HEIGHT / 30,
					false, false);
		} catch (FileNotFoundException e) {
			System.out.println("Unable to find image-files!");
		}
		this.position = startingPos;
	}
	
	public void setStartPos() {
		this.position = this.startingPos;
	}


	public Point2D getPos() {
		return this.position;
	}

	public void update(GameBoard gameboard, Pacman pacman) {
		this.move(gameboard);
		this.collide(pacman);
		this.frightenMode(pacman);
		this.chase(pacman);
	}

	public void drawYourself(GraphicsContext gc) {
		if (this.frightened) {
			gc.drawImage(frightenedGraphic, position.getX() - SCREEN_WIDTH / 30 / 2,
					position.getY() - SCREEN_HEIGHT / 30 / 2);
		} else {
			gc.drawImage(normalGraphic, position.getX() - SCREEN_WIDTH / 30 / 2,
					position.getY() - SCREEN_HEIGHT / 30 / 2);
		}
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

	public void frightenMode(Pacman pacman) {
		if (pacman.getPowerUp()) {
			this.frightened = true;
		} else {
			this.frightened = false;
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
				this.position = startingPos;
				pacman.setScore(pacman.getScore() + 200);
			}

		}
	}

	public void chase(Pacman pacman) {
		if(this.direction == KeyCode.UP || this.direction == KeyCode.RIGHT) {
			if((int) Math.round((this.position.getY() + 10) / 20) == (int) Math
					.round(pacman.getPos().y / 20)) {
				if(this.position.getX() > pacman.getPos().x) {
					this.direction = KeyCode.LEFT;
				} else {
					this.direction = KeyCode.RIGHT;
				}
			} else if((int) Math.round((this.position.getX() - 10) / 20) == (int) Math
					.round(pacman.getPos().x / 20)) {
				if(this.position.getY() > pacman.getPos().y) {
					this.direction = KeyCode.UP;
				} else {
					this.direction = KeyCode.DOWN;
				}
			}
		} else {
			if((int) Math.round((this.position.getY() - 10) / 20) == (int) Math
					.round(pacman.getPos().y / 20)) {
				if(this.position.getX() > pacman.getPos().x) {
					this.direction = KeyCode.LEFT;
				} else {
					this.direction = KeyCode.RIGHT;
				}
			} else if((int) Math.round((this.position.getX() + 10) / 20) == (int) Math
					.round(pacman.getPos().x / 20)) {
				if(this.position.getY() > pacman.getPos().y) {
					this.direction = KeyCode.UP;
				} else {
					this.direction = KeyCode.DOWN;
				}
			}
		}
		
	}

}
