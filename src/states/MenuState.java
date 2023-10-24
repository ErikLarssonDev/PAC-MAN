package states;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import static constants.Constants.SCREEN_HEIGHT;
import static constants.Constants.SCREEN_WIDTH;

/**
 * This class represents the game menu and gives the user three choices: play new game(switches to playstate), exit(ends the program) or watch the highscore(switches to HighscoreState). 
 */
public class MenuState extends GameState {

	private String informationText;
	private Color bgColor;
	private Color fontColor;
	private PlayState playState;
	private HighScoreState highScoreState;

	public MenuState(GameModel model) {
		super(model);
		playState = new PlayState(model);
		highScoreState = new HighScoreState(model);
		informationText = "Press Enter To Play new game\nEscape to exit\nSpace to see highscore";
		bgColor = Color.BLUE;
		fontColor = Color.YELLOW;
	}

	@Override
	public void draw(GraphicsContext g) {
		drawBg(g, bgColor);
		g.setFill(fontColor);
		g.setFont(new Font(30));
		g.fillText(informationText, SCREEN_WIDTH / 30 * 2, SCREEN_HEIGHT / 30 * 2);
	}

	@Override
	public void keyPressed(KeyEvent key) {
		System.out.println("Trycker på " + key.getText() + " i MenuState");

		if (key.getCode() == KeyCode.ENTER) {
			model.switchState(playState);
		} else if (key.getCode() == KeyCode.ESCAPE) {
			System.exit(0);
		} else if(key.getCode() == KeyCode.SPACE) {
			model.switchState(highScoreState);
		}
	}

	@Override
	public void update() {
	}

	@Override
	public void activate() {
	}

	@Override
	public void deactivate() {
	}

}
