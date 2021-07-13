package chessgame;

public enum GameStatus {
	ACTIVE,
	GAME_OVER,
	QUIT_GAME;
	
	private static GameStatus status;
	
	public static boolean isEnd() {
		return GameStatus.getStatus() != GameStatus.ACTIVE;
	}

	public static GameStatus getStatus() {
		return GameStatus.status;
	}

	static void setStatus(GameStatus status) {
		GameStatus.status = status;
	}
}
