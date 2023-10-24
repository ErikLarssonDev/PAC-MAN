package main;

import states.GameModel;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import static constants.Constants.SCREEN_HEIGHT;
import static constants.Constants.SCREEN_WIDTH;

/**
 * This class is the main class and is responsible for starting the application, setting up the game window and running the game loop.
 */
public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage gameStage) throws Exception {
		gameStage.setTitle("Pac Man");
		gameStage.setWidth(SCREEN_WIDTH);
		gameStage.setHeight(SCREEN_HEIGHT);
		GameModel model = new GameModel();
		GameFrame frame = new GameFrame(model, SCREEN_WIDTH, SCREEN_HEIGHT);
		Scene gameScene = new Scene(frame);
		final double targetFps = 50.0;
		final double nanoPerUpdate = 1000000000.0 / targetFps;
		gameStage.setScene(gameScene);

		gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				model.keyPressed(event);
			}
		});

		new AnimationTimer() {
			long lastUpdate = 0;

			public void handle(long now) {

				if ((now - lastUpdate) > nanoPerUpdate) {
					model.update();
					frame.repaint();
					lastUpdate = now;
				}
			}
		}.start(); 

		gameStage.show();

	}
}