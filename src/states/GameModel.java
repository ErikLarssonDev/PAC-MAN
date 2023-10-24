package states;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;

/**
 * This class represents the current state of the game and makes it possible to switch the current state.
 */

public class GameModel {

	private GameState currentState;

	public GameModel() {
		this.currentState = new MenuState(this);
	}

	public void switchState(GameState nextState) {
		currentState.deactivate();
		currentState = nextState;
		currentState.activate();
	}

	public void keyPressed(KeyEvent key) {
		currentState.keyPressed(key);
	}

	public void update() {
		currentState.update();
	}

	public void draw(GraphicsContext g) {
		currentState.draw(g);
	}
}
