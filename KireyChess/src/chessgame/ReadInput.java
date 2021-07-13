package chessgame;

import java.util.List;
import java.util.Scanner;

public class ReadInput {
	private boolean user1;
	private boolean user2;
	private Scanner scan;
	String playerTurn = "Player's turn: ";

	public ReadInput(Scanner scan) {
		this.scan = scan;
		this.setUser1(false);
		this.setUser2(false);
	}

	public Scanner getScan() {
		return scan;
	}

	public boolean isUser1() {
		return user1;
	}

	public void setUser1(boolean user1) {
		this.user1 = user1;
	}

	public boolean isUser2() {
		return user2;
	}

	public void setUser2(boolean user2) {
		this.user2 = user2;
	}

	public int[] enterPiecePositions(String playerName) throws IllegalArgumentException {
		System.out.println(playerTurn + playerName.toUpperCase());
		System.out.println("Enter a new piece positions value, like: '00' ");
		System.out.print("- ");
		var values = new int[2];
		String value = scan.nextLine();
		if (value.equalsIgnoreCase("quit")) {
			GameStatus.setStatus(GameStatus.QUIT_GAME);
		} else {
			try {
				if (value.length() != 2) {
					throw new IllegalArgumentException("INVALID POSITION's NUMBER, PLEASE ENTER VALID POSITIONS!");
				}
				values[0] = Character.getNumericValue(value.charAt(0));
				values[1] = Character.getNumericValue(value.charAt(1));
			} catch (StringIndexOutOfBoundsException e) {
				e.printStackTrace();
			}
		}
		return values;
	}

	public String enterPieceTypeValue(String playerName, List<String> typeOfAvailablePiecesPlayer)
			throws IllegalArgumentException {
		System.out.println(playerTurn + playerName.toUpperCase());
		System.out.println("Write a type of piece from the following: " + typeOfAvailablePiecesPlayer + " ");
		System.out.print("- ");
		String value = scan.nextLine();
		if (value.equalsIgnoreCase("quit")) {
			GameStatus.setStatus(GameStatus.QUIT_GAME);
		} else {
			if (!typeOfAvailablePiecesPlayer.contains(value.toUpperCase())) {
				throw new IllegalArgumentException("INVALID TYPE OF PIECE, PLEASE ENTER VALID PIECE TYPE!");
			}
		}
		return value.toUpperCase();
	}

	public void enterMovingPositions(Player player, Board board, boolean isUser)
			throws IllegalArgumentException, StringIndexOutOfBoundsException {
		System.out.println(playerTurn + player.getName().toUpperCase());
		System.out.println("To move the piece, enter a value like: '00-11' or '00-20' ");
		System.out.println("To quit the game, type: 'quit' ");
		System.out.print("- ");

		this.setUser1(isUser);
		this.setUser2(isUser);
		String value = scan.nextLine();
		var values = new int[4];
		if (value.equalsIgnoreCase("quit")) {
			GameStatus.setStatus(GameStatus.QUIT_GAME);
		} else {
			this.tryMovingPosition(value, values);
			try {
				if (!board.playerMove(player, values[0], values[1], values[2], values[3])) {
					if (player.isWhiteSide()) {
						this.user1 = true;
					} else {
						this.user2 = true;
					}
				} else {
					if (player.isWhiteSide()) {
						this.user1 = false;
					} else {
						this.user2 = false;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void tryMovingPosition(String value, int[] values) {
		try {
			if (value.length() != 5) {
				this.user1 = true;
				this.user2 = true;
				throw new IllegalArgumentException("INVALID POSITION's NUMBER, PLEASE ENTER VALID POSITIONS!");
			} else {
				this.user1 = false;
				this.user2 = false;
			}
			values[0] = Character.getNumericValue(value.charAt(0));
			values[1] = Character.getNumericValue(value.charAt(1));
			values[2] = Character.getNumericValue(value.charAt(3));
			values[3] = Character.getNumericValue(value.charAt(4));
		} catch (StringIndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}
}
