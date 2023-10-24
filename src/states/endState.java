package states;




import logics.Pacman;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;


import static constants.Constants.SCREEN_HEIGHT;
import static constants.Constants.SCREEN_WIDTH;

import java.util.Scanner;

/**
 * This state represents the end state which is what follows loosing all your lives. Here your name and score is collected and saved to the highscore if your round was good enough.
 */
public class endState extends GameState {

	private String informationText;
	private String ScoreText;
	private Color bgColor;
	private Color fontColor;
	private int ourScore;
	private String Name;
	private LinkedList<Highscore> highscores = new LinkedList<Highscore>();
	private Highscore[] highscore = new Highscore[10];
	private boolean NotAddedToHighscore = true;

	public endState(GameModel model, int ourscore) {
		super(model);
		this.ourScore = ourscore;
		Name = "";
		ScoreText = "Score: " + ourScore;
		informationText = "Enter your name: ";
		bgColor = Color.BLUE;
		fontColor = Color.YELLOW;
	}

	public void setScore(Integer pacman_score) {
		ourScore = pacman_score;		
	}

	@Override
	public void draw(GraphicsContext g) {
		drawBg(g, bgColor);
		g.setFill(fontColor);
		g.setFont(new Font(30)); // Big letters
		g.fillText(ScoreText + "\n" + informationText + "\n" + "Press ENTER to register name\nName: " + Name, SCREEN_WIDTH / 30 * 2, SCREEN_HEIGHT / 30 * 2);
	}

	@Override
	public void keyPressed(KeyEvent key) {
		System.out.println("Trycker på " + key.getCode() + " i endState");

		if (key.getCode().isLetterKey()) {
			Name = Name + key.getCode();
		} else if (key.getCode() == KeyCode.SPACE) {
			Name = Name + " ";
		}

		if (key.getCode() == KeyCode.ENTER) {

			try {
				saveHighscore();
			} catch (IOException e){
				System.out.println("Error when saving highscore");	
			}		

			model.switchState(new HighScoreState(model));
		} else if (key.getCode() == KeyCode.BACK_SPACE) {
			Name = Name.substring(0, Name.length() - 1); 
		}
	}

	public void saveHighscore() throws IOException{
		try {
			File highscorefile = new File("Highscore.txt");

			if (highscorefile.createNewFile()) {
				System.out.println("Created file: " + highscorefile.getName());
			} else {
				System.out.println(highscorefile.getName() + " already exists");
			}
		} finally {
			FileReader reader = new FileReader("Highscore.txt");
			Scanner sc = new Scanner(reader);
			String name = "";
			int score = 1;

			for(int i = 0; i < 10; i++) {
				if(sc.hasNext()) {
					name = sc.nextLine();
					if(sc.hasNext()) {
						score = Integer.parseInt(sc.nextLine());
						if(score >= ourScore) {
							highscores.add(new Highscore(name, score));
						} else {
							highscores.add(new Highscore(Name, ourScore));
							highscores.add(new Highscore(name, score));
							for(int j = i + 2; j < 10; j++) {
								if(sc.hasNext()) {
									name = sc.nextLine();
									if(sc.hasNext()) {
										score = Integer.parseInt(sc.nextLine());
										highscores.add(new Highscore(name, score));
									}
								}
							}
							break;
						}
					}
				} else {
					highscores.add(new Highscore(Name, ourScore));
					break;
				}
			}
			sc.close();	

			FileWriter myWriter = new FileWriter("Highscore.txt");
			for(int i = 0; i < highscores.size(); ++i) {
				myWriter.write(highscores.get(i).getName() + "\n" + highscores.get(i).getScore() + "\n");
			}
			highscores.clear();
			myWriter.close();
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

