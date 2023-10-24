package main;

import states.GameModel;

import javafx.scene.canvas.Canvas;

/**
 * The GamePanel wraps a canvas and creates an area which the game can be drawn on.
 */
public class GamePanel extends Canvas {

	private GameModel model;

	public GamePanel(final GameModel model, int width, int height) {
		this.model = model;
		this.setWidth(width);
		this.setHeight(height);
	}

	public void repaint() {
		model.draw(getGraphicsContext2D());
	}
}
