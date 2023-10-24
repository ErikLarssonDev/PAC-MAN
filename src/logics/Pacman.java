package logics;

import static constants.Constants.SCREEN_HEIGHT;
import static constants.Constants.SCREEN_WIDTH;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

/**
 * This class is responsible for almost everything related to pacman/player. The
 * class has private instance variables for player: speed, score, lives etc.
 * There is an update method which calls on a move method and is responsible for
 * pacman eating, powerup. Our move method moves the player a certain amount of
 * pixels every update based on the speed of pacman
 *
 */
public class Pacman {
	private double speed;
	private int score;
	private int lives;
	private boolean powerup;
	private KeyEvent direction = null;
	private int mouthOpen = 0;
	private int powerUpDuration;

	public class Point {
		double x;
		double y;

		public Point(double x, double y) {
			this.x = x;
			this.y = y;
		}
	}

	private static Point2D startingPos = new Point2D((SCREEN_WIDTH / 30) * 15, ((SCREEN_HEIGHT / 30) * 14));
	private Point position;
	private Image pacman;
	private int angle = 0;

	public Pacman() {
		this.speed = 2;
		this.score = 0;
		this.lives = 3;
		this.powerup = false;
		this.position = new Point(startingPos.getX(), startingPos.getY());

		try {
			pacman = new Image(new FileInputStream("pacman.png"), SCREEN_WIDTH / 30, SCREEN_HEIGHT / 30, false, false);
		} catch (FileNotFoundException e) {
			System.out.println("Unable to find image-files!");
		}
	}

	public int getScore() {
		return this.score;
	}

	public void setScore(int s) {
		this.score = s;
	}

	public void setDirection(KeyEvent key) {
		this.direction = key;
	}

	public Image getPacmanImage() {
		return this.pacman;
	}

	public int getLives() {
		return this.lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public void setPos(int posX, int posY) {
		this.position = new Point(posX, posY);
	}

	public void setStartPos() {
		this.position = new Point(startingPos.getX(), startingPos.getY());
	}

	public Point getPos() {
		return this.position;
	}

	public boolean getPowerUp() {
		return this.powerup;
	}

	public void setPowerUp(boolean powerup) {
		this.powerup = powerup;
	}

	private void rotate(GraphicsContext gc, double angle, double px, double py) {
		Rotate r = new Rotate(angle, px, py);
		gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
	}

	private void drawRotatedImage(GraphicsContext g, Image image, double angle, double topLeftX, double topLeftY) {
		g.save(); // saves the current state on stack, including the current transform
		rotate(g, angle, topLeftX + image.getWidth() / 2, topLeftY + image.getHeight() / 2);
		g.drawImage(image, topLeftX, topLeftY);
		g.restore(); // back to original state (before rotation)
	}

	public void update(GameBoard gameboard, ArrayList<Ghosts> ghosts) {
		mouthOpen += 1;
		this.move(gameboard);
		this.powerUp(gameboard);
		this.powerUpDuration -= 1;
		if (this.powerUpDuration == 0) {
			this.powerup = false;
		}

		if (gameboard.getPoint(position.x, position.y) == 1) {
			gameboard.setPoint(position.x, position.y, 2);
			this.score += 10;
		}
		if (!gameboard.checkDots()) {
			gameboard.reset();
			for (int i = 0; i < ghosts.size(); i++) {
				ghosts.get(i).setStartPos();
			}
			this.position = new Point(startingPos.getX(), startingPos.getY());
			this.speed = (this.speed * 1.05);
			gameboard.openJail();
		}

		if (this.score % 10000 == 0 && this.score != 0) {
			this.lives += 1;
		}

	}

	public void move(GameBoard gameboard) {
		if (direction != null) {
			if (direction.getCode() == KeyCode.UP) {
				if (gameboard.getPoint(position.x, position.y - SCREEN_HEIGHT / 30 / 2) != 0) {
					this.position.y -= speed;
					if (wall(gameboard)) {
						this.position.y += speed;
					}
				}
				angle = 270; // Updates the angle of the rotating image

			} else if (direction.getCode() == KeyCode.DOWN) {
				if (gameboard.getPoint(position.x, position.y + SCREEN_HEIGHT / 30 / 2) != 0) {
					this.position.y += speed;
					if (wall(gameboard)) {
						this.position.y -= speed;
					}
				}
				angle = 90; // Updates the angle of the rotating image

			} else if (direction.getCode() == KeyCode.LEFT) {
				if (gameboard.getPoint(position.x - SCREEN_WIDTH / 30 / 2, position.y) != 0) {
					this.position.x -= speed;
					if (wall(gameboard)) {
						this.position.x += speed;
					}
				}
				angle = 180; // Updates the angle of the rotating image

			} else if (direction.getCode() == KeyCode.RIGHT) {
				if (gameboard.getPoint(position.x + SCREEN_WIDTH / 30 / 2, position.y) != 0) {
					this.position.x += speed;
					if (wall(gameboard)) {
						this.position.x -= speed;
					}
				}
				angle = 0; // Updates the angle of the rotating image

			} else {
				this.position.x += 0;
				this.position.y += 0;
				angle += 3; // Updates the angle of the rotating image
			}
		}
	}

	public boolean wall(GameBoard gameboard) {

		if (direction.getCode() == KeyCode.RIGHT) {
			if (gameboard.getPoint(position.x + SCREEN_WIDTH / 30 / 2,
					position.y - SCREEN_HEIGHT / 30 / 2 + speed) == 0) {
				return true;
			}
			if (gameboard.getPoint(position.x + SCREEN_WIDTH / 30 / 2,
					position.y + SCREEN_HEIGHT / 30 / 2 - speed) == 0) {
				return true;
			}
		}

		if (direction.getCode() == KeyCode.LEFT) {
			if (gameboard.getPoint(position.x - SCREEN_WIDTH / 30 / 2,
					position.y - SCREEN_HEIGHT / 30 / 2 + speed) == 0) {
				return true;
			}
			if (gameboard.getPoint(position.x - SCREEN_WIDTH / 30 / 2,
					position.y + SCREEN_HEIGHT / 30 / 2 - speed) == 0) {
				return true;
			}
		}

		if (direction.getCode() == KeyCode.UP) {
			if (gameboard.getPoint(position.x - SCREEN_WIDTH / 30 / 2 + speed,
					position.y - SCREEN_HEIGHT / 30 / 2) == 0) {
				return true;
			}
			if (gameboard.getPoint(position.x + SCREEN_WIDTH / 30 / 2 - speed,
					position.y - SCREEN_HEIGHT / 30 / 2) == 0) {
				return true;
			}
		}

		if (direction.getCode() == KeyCode.DOWN) {
			if (gameboard.getPoint(position.x - SCREEN_WIDTH / 30 / 2 + 2,
					position.y + SCREEN_HEIGHT / 30 / speed) == 0) {
				return true;
			}
			if (gameboard.getPoint(position.x + SCREEN_WIDTH / 30 / 2 - 2,
					position.y + SCREEN_HEIGHT / 30 / speed) == 0) {
				return true;
			}
		}

		return false;
	}

	public void powerUp(GameBoard gameboard) {
		if (gameboard.getPoint(position.x, position.y) == 3) {
			gameboard.setPoint(position.x, position.y, 2);
			this.powerup = true;
			this.powerUpDuration = 250;
		}
	}

	public void drawYourself(GraphicsContext g) {
		if (mouthOpen % 10 < 5) {
			drawRotatedImage(g, pacman, angle, position.x - SCREEN_WIDTH / 30 / 2, position.y - SCREEN_HEIGHT / 30 / 2);
		} else {
			g.setFill(Color.YELLOW);
			g.fillOval(position.x - SCREEN_WIDTH / 30 / 2, position.y - SCREEN_HEIGHT / 30 / 2, SCREEN_WIDTH / 30,
					SCREEN_HEIGHT / 30);
		}

	}

	private void draw(GraphicsContext g) {
		if (position.x >= SCREEN_WIDTH || position.y >= SCREEN_HEIGHT) {
			// The position is exiting the screen, so we reset it
			position.x = 0.0;
			position.y = 0.0;
		}

		drawRotatedImage(g, pacman, angle, position.x - SCREEN_WIDTH / 30 / 2, position.y - SCREEN_HEIGHT / 30 / 2);
	}

}
