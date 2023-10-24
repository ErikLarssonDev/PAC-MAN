package states;

import logics.CyanGhost;
import logics.GameBoard;
import logics.Ghosts;
import logics.OrangeGhost;
import logics.Pacman;
import logics.Pinky;
import logics.RedGhost;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import static constants.Constants.SCREEN_HEIGHT;
import static constants.Constants.SCREEN_WIDTH;

import java.util.ArrayList;

/**
 * This class is responisble for creating all objects and updating them during the game while also updating the graphics on the screen.
 */
public class PlayState extends GameState {

	private String ScoreText;
	private Color bgColor;
	private Color fontColor;
	private GameBoard gameboard;
	private Pacman pacman;
	private RedGhost blinky;
	private CyanGhost cyan;
	private OrangeGhost orange;
	private Pinky pinky;
	private ArrayList<Ghosts> ghosts = new ArrayList<Ghosts>();

	public PlayState(GameModel model) {
		super(model);

		bgColor = Color.BLACK;
		fontColor = Color.YELLOW;
		gameboard = new GameBoard();
		pacman = new Pacman();
		blinky = new RedGhost();
		cyan = new CyanGhost();
		orange = new OrangeGhost();
		pinky = new Pinky();
		this.ghosts.add(blinky);
		this.ghosts.add(orange);
		this.ghosts.add(cyan);
		this.ghosts.add(pinky);
	}

	@Override
	public void draw(GraphicsContext g) {
		drawBg(g, bgColor);
		g.setFill(fontColor);
		g.setFont(new Font(30));
		gameboard.drawYourself(g);
		blinky.drawYourself(g);
		cyan.drawYourself(g);
		orange.drawYourself(g);
		pinky.drawYourself(g);
		pacman.drawYourself(g);		
		g.setFill(Color.YELLOW);
		g.setFont(new Font(30)); 
		g.fillText("Score: " + pacman.getScore(), SCREEN_WIDTH / 30 * 4, SCREEN_HEIGHT / 30 * 4);
		g.setFill(Color.YELLOW);
		g.setFont(new Font(30)); // Big letters
		g.fillText("Lives: " + pacman.getLives(), SCREEN_WIDTH / 30 * 4, SCREEN_HEIGHT / 30 * 6);
	}

	@Override
	public void keyPressed(KeyEvent key) {
		System.out.println("Trycker på " + key.getCode() + " i PlayState");
		if (key.getCode() == KeyCode.ESCAPE) {
			model.switchState(new MenuState(model));
		} else if (key.getCode() == KeyCode.UP) {
			pacman.setDirection(key);
		} else if (key.getCode() == KeyCode.DOWN) {
			pacman.setDirection(key);
		} else if (key.getCode() == KeyCode.LEFT) {
			pacman.setDirection(key);
		} else if (key.getCode() == KeyCode.RIGHT) {
			pacman.setDirection(key);
		}
	}

	@Override
	public void update() {
		gameboard.update(ghosts);
		pacman.update(gameboard, ghosts);
		blinky.update(gameboard, pacman);
		cyan.update(gameboard, pacman);
		orange.update(gameboard, pacman);
		pinky.update(gameboard, pacman);
		if(pacman.getLives() == 0) {
			model.switchState(new endState(model, pacman.getScore()));
		}

	}

	@Override
	public void activate() {

	}

	@Override
	public void deactivate() {

	}

}
