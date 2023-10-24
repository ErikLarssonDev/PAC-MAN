package states;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import static constants.Constants.SCREEN_HEIGHT;
import static constants.Constants.SCREEN_WIDTH;

/**
 * This class represents a state of the game and is used for building all states of the game as it includes some mandatory functions each state must have.
 */

public abstract class GameState {

	protected GameModel model;

	public GameState(GameModel model) {
		this.model = model;
	}

	public abstract void update();

	public abstract void draw(GraphicsContext g);

	public abstract void keyPressed(KeyEvent key);

	public void drawBg(GraphicsContext g, Color color) {
		g.setFill(color);
		g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
	}

	public abstract void activate();

	public abstract void deactivate();
}
