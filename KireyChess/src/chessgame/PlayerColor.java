package chessgame;

public enum PlayerColor {
	WHITE("white"),
	BLACK("black");
	
	private String name;
	
	PlayerColor(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}
}
