package states;

/*
 * This class is for saving and loading the highscore for each player in an appropriate format.
 */

public class Highscore {
	private String name;
	private int score;

	public Highscore(String Name, int Score) {
		this.name = Name;
		this.score = Score;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String Name) {
		this.name = Name;
	}

	public Integer getScore() {
		return this.score;
	}

	public void setScore(int Score) {
		this.score = Score;
	}

}
