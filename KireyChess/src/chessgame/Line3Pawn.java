package chessgame;

public class Line3Pawn extends LineRook {
	private static final long serialVersionUID = 1L;
	
  @Override
  public boolean canMove(int startX, int startY, int endX, int endY) {
	  super.maxVertical = 3;
	  super.maxHorizontal = 3;
	  return super.canMove(startX, startY, endX, endY);
  }
}