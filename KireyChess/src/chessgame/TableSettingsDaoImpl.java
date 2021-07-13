package chessgame;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TableSettingsDaoImpl implements TableSettingsDao {
	Connection myConn = null;
	Statement myStmt = null;
	ResultSet myRset = null;
	TableProperties tableProperties;
	List<TableContainer> tableContainer;
	ReadPropertyFile prop;

	public TableSettingsDaoImpl(ReadPropertyFile prop) {
		this.tableProperties = null;
		this.tableContainer = new ArrayList<>();
		this.prop = prop;
	}
	
	public Connection getConnection() {
		try {
			myConn = DriverManager.getConnection(prop.getDB_URL(), prop.getUSER(), prop.getPASS());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return myConn;
	}

	@Override
	public TableProperties getProperties() {
		try {
			this.getConnection();
			myStmt = myConn.createStatement();
			myRset = myStmt.executeQuery("Select * from Cosmin_properties");

			while (myRset.next()) {
				tableProperties = new TableProperties(myRset.getInt("chessBoardWidth"),
						myRset.getInt("chessBoardHeight"), myRset.getString("boardFillingType"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tableProperties;
	}

	@Override
	public List<TableContainer> getContainer() {
		try {
			this.getConnection();
			myStmt = myConn.createStatement();
			myRset = myStmt.executeQuery("Select * from Cosmin_container");

			while (myRset.next()) {
				tableContainer.add(new TableContainer(myRset.getString("type"), myRset.getString("number")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tableContainer;
	}

	@Override
	public void writeMovesPlayed(List<Move> movesPlayed) {
		try {
			this.getConnection();
			myStmt = myConn.createStatement();
			for (Move move: movesPlayed) {
				myStmt.executeUpdate("Insert into Cosmin_moves values ('" + move.toString() + "')");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
