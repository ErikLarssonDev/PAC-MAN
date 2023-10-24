package main;

import javafx.scene.layout.HBox;
import states.GameModel;

/**
 * The gameframe creates an gamepanel and adds it to itself to make it visible.
 */
public class GameFrame extends HBox {
	private GamePanel g;

	public GameFrame(GameModel model, int width, int height) {
		g = new GamePanel(model, width, height);
		this.getChildren().add(g);
	}

	public void repaint() {
		g.repaint();
	}
}
