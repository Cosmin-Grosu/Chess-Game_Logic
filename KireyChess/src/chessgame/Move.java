package chessgame;

import java.io.Serializable;

public class Move implements Serializable {
	private static final long serialVersionUID = 1L;
	private transient Player player;
	private transient SpotPlace start;
	private transient SpotPlace end;
	private Piece pieceMoved;

	public Move(Player player, SpotPlace start, SpotPlace end) {
		this.player = player;
		this.start = start;
		this.end = end;
		this.pieceMoved = start.getPiece();
	}

	public SpotPlace getStart() {
		return start;
	}

	public SpotPlace getEnd() {
		return end;
	}
	
	public Player getPlayer() {
		return player;
	}

	@Override
	public String toString() {
		return "" + pieceMoved.getType() + ": " + start + " --> " + end;
	}
}
