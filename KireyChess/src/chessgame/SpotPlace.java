package chessgame;

public class SpotPlace {
	private Piece piece;
	private String type;
	private int x;
	private int y;

	public SpotPlace(int x, int y, Piece piece) {
		this.setPiece(piece);
		this.setX(x);
		this.setY(y);
	}

	public String getType() {
		return type;
	}

	public Piece getPiece() {
		return this.piece;
	}

	public void setPiece(Piece p) {
		this.piece = p;
	}

	public int getX() {
		return this.x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return this.y;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "[" + x + "," + y + "]";
	}

}
