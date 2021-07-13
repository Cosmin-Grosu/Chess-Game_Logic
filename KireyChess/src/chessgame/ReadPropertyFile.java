package chessgame;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ReadPropertyFile {	
	private String chessBoardWidth;
	private String chessBoardHeight;
	private String boardFillingType;
	private String container;
	private String readFromDB;
	private String DB_URL;
	private String USER;
	private String PASS;

	public String getChessBoardWidth() {
		return chessBoardWidth;
	}

	public String getChessBoardHeight() {
		return chessBoardHeight;
	}

	public String getBoardFillingType() {
		return boardFillingType;
	}

	public String getContainer() {
		return container;
	}

	public String getReadFromDB() {
		return readFromDB;
	}

	public String getDB_URL() {
		return DB_URL;
	}

	public String getUSER() {
		return USER;
	}

	public String getPASS() {
		return PASS;
	}

	public void readProperty() {
		var prop = new Properties();
		
		try (var fis = new FileInputStream("FileSettings/settings.properties")) {
			
			prop.load(fis);
			
			this.chessBoardWidth = prop.getProperty("chessBoardWidth");
			this.chessBoardHeight = prop.getProperty("chessBoardHeight");
			this.boardFillingType = prop.getProperty("boardFillingType");
			this.container = prop.getProperty("container");
			this.readFromDB = prop.getProperty("readFromDB");
			this.DB_URL = prop.getProperty("DB_URL");
			this.USER = prop.getProperty("USER");
			this.PASS = prop.getProperty("PASS");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "Proprietes [chessBoardWidth= " + chessBoardWidth + ",chessBoardHeight= " + chessBoardHeight + 
				"type= " + boardFillingType + "]";
	}
}
