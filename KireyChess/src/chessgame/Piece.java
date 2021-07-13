package chessgame;

import java.io.Serializable;

public abstract class Piece implements Serializable {

	private static final long serialVersionUID = 1L;

	private String color;
	private String number;
	private String type;

	public abstract boolean canMove(int startX, int startY, int endX, int endY);

	public boolean isWhite() {
		return this.getColor().equalsIgnoreCase("white");
	}

	@Override
	public int hashCode() {
		final var prime = 31;
		var result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Piece other = (Piece) obj;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		return true;
	}
	
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "Piece [number=" + number + ", type=" + type + ", color=" + color + "]";
	}

}
