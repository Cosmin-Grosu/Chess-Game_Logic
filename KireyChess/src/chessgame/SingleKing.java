package chessgame;

public class SingleKing extends Piece {
	private static final long serialVersionUID = 1L;

	@Override
	public boolean canMove(int startX, int startY, int endX, int endY) {
		// check if this it can be moved 1 position in any direction
		int x = Math.abs(startX - endX);
		int y = Math.abs(startY - endY);
		return ((x == 1) && (y == 0)) || ((x == 0) && (y == 1)) || ((x == 1) && (y == 1));
	}
}
