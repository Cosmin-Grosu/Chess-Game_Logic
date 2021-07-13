package chessgame;

public class TableProperties {
	private int chessBoardWidth;
	private int chessBoardHeight;
	private String boardFillingType;
	
	public TableProperties(int chessBoardWidth, int chessBoardHeight, String boardFillingType) {
		this.chessBoardWidth = chessBoardWidth;
		this.chessBoardHeight = chessBoardHeight;
		this.boardFillingType = boardFillingType;
	}

	public String getBoardFillingType() {
		return boardFillingType;
	}

	public int getChessBoardWidth() {
		return chessBoardWidth;
	}

	public int getChessBoardHeight() {
		return chessBoardHeight;
	}
}
