package chessgame;

public class Player {
	private PlayerColor whiteSide = PlayerColor.valueOf("WHITE");
	private PlayerColor playerColor;
	private String name;
	private String color;
	private String container;
	private int numberOfPieces = 0;

	public Player(String name, String color) {
		this.name = name;
		this.color = color;
		this.playerColor = PlayerColor.valueOf(color.toUpperCase());
	}

	public String getName() {
		return name;
	}

	public String getColor() {
		return color;
	}

	public String getContainer() {
		return container;
	}

	public void setContainer(String container) {
		this.container = container;
	}

	public int getNumberOfPieces() {
		return numberOfPieces;
	}

	public void setNumberOfPieces(int numberOfPieces) {
		this.numberOfPieces = numberOfPieces;
	}

	public boolean isWhiteSide() {
		return this.whiteSide == this.playerColor;
	}

	@Override
	public String toString() {
		return name;
	}

	public boolean gameOver() {
		if (this.getNumberOfPieces() < 1) {
			GameStatus.setStatus(GameStatus.GAME_OVER);
			return true;
		}
		return false;
	}

}
