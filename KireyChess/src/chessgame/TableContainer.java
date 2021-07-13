package chessgame;

public class TableContainer {
	private String type;
	private String number;
	
	public TableContainer(String type, String number) {
		this.type = type;
		this.number = number;
	}

	public String getType() {
		return type;
	}

	public String getNumber() {
		return number;
	}
}