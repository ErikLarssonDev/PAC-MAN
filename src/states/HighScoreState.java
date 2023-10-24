package states;

import logics.Pacman;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import static constants.Constants.SCREEN_HEIGHT;
import static constants.Constants.SCREEN_WIDTH;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * This state reads the highscore from a text file and displays it on the screen.
 */
public class HighScoreState extends GameState {

	private String informationText;
	private String ScoreText;
	private Color bgColor;
	private Color fontColor;
	private LinkedList<Highscore> highscores;

	public HighScoreState(GameModel model) {
		super(model);
		this.highscores = new LinkedList<Highscore>();
		informationText = "Press Escape To Return To The Menu\nHighscore:\n";
		bgColor = Color.BLUE;
		fontColor = Color.YELLOW;
	}

	@Override
	public void draw(GraphicsContext g) {
		drawBg(g, bgColor);
		g.setFill(fontColor);
		g.setFont(new Font(30));
		g.fillText(informationText, SCREEN_WIDTH / 30 * 2, SCREEN_HEIGHT / 30 * 2);
		for (int i = 0; i < 10; i++) {
			g.fillText((i + 1) + ". " + this.highscores.get(i).getName() + " " + this.highscores.get(i).getScore(),
					SCREEN_WIDTH / 30 * 2, SCREEN_HEIGHT / 30 * (2 * i + 6));
		}
	}

	public void readHighscores() throws IOException {
		try {
			File highscorefile = new File("Highscore.txt");

			if (highscorefile.createNewFile()) {
				System.out.println("Created file: " + highscorefile.getName());
			} else {

			}
		} finally {
			FileReader reader = new FileReader("Highscore.txt");
			Scanner sc = new Scanner(reader);
			String name = "";
			int score = 1;
			int Place = 0;
			int empty = 0;
			for (int i = 0; i < 10; i++) {
				if (sc.hasNext()) {
					empty += 1;
					name = sc.nextLine();
					if (sc.hasNext()) {
						score = Integer.parseInt(sc.nextLine());
						highscores.addLast(new Highscore(name, score));
					}
				} else {
					highscores.add(new Highscore("N/A", 0));
				}
			}
			sc.close();
		}
	}

	@Override
	public void keyPressed(KeyEvent key) {
		System.out.println("Trycker på " + key.getCode() + " i PlayState");

		if (key.getCode() == KeyCode.ESCAPE) {
			model.switchState(new MenuState(model));
		}
	}

	@Override
	public void update() {
		try {
			this.readHighscores();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void activate() {

	}

	@Override
	public void deactivate() {

	}

}
