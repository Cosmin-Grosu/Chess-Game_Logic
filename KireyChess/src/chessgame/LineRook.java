package chessgame;

public class LineRook extends Piece {
	private static final long serialVersionUID = 1L;
	protected int maxVertical = 7;
	protected int maxHorizontal = 7;

	@Override
	public boolean canMove(int startX, int startY, int endX, int endY) {
		// check if this it can be moved maximum positions horizontally or vertically
		int x = Math.abs(startX - endX);
		int y = Math.abs(startY - endY);
		return (((x >= 1) && (x <= this.maxVertical)) && (y == 0))
				|| (((y >= 1) && (y <= this.maxHorizontal)) && (x == 0));
	}
}
