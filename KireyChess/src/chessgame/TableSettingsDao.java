package chessgame;

import java.util.List;

public interface TableSettingsDao {
	public TableProperties getProperties();
	public List<TableContainer> getContainer();
	public void writeMovesPlayed(List<Move> movesPlayed);
}
