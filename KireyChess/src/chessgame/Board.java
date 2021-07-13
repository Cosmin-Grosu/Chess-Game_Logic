package chessgame;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class Board {
	private SpotPlace[][] boxes;
	private int chessHeight;
	private int chessWidth;
	private String boardFillingType;
	private List<Move> movesPlayed = new ArrayList<>();
	private int numberOfWhitePieces;
	private int numberOfBlackPieces;
	private boolean checkFalsePiece = false;
	final Logger logger = Logger.getLogger(Board.class.getName());

	public int getChessHeight() {
		return chessHeight;
	}

	public void setChessHeight(int chessHeight) {
		this.chessHeight = chessHeight;
	}

	public int getChessWidth() {
		return chessWidth;
	}

	public void setChessWidth(int chessWidth) {
		this.chessWidth = chessWidth;
	}

	public String getBoardFillingType() {
		return boardFillingType;
	}

	public void setBoardFillingType(String boardFillingType) {
		this.boardFillingType = boardFillingType;
	}

	public void setBoxes(SpotPlace[][] boxes) {
		this.boxes = boxes;
	}

	public List<Move> getMovesPlayed() {
		return movesPlayed;
	}

	public int getNumberOfWhitePieces() {
		return numberOfWhitePieces;
	}

	public void setNumberOfWhitePieces(int numbersOfWhitePieces) {
		this.numberOfWhitePieces = numbersOfWhitePieces;
	}

	public int getNumberOfBlackPieces() {
		return numberOfBlackPieces;
	}

	public void setNumberOfBlackPieces(int numberOfBlackPieces) {
		this.numberOfBlackPieces = numberOfBlackPieces;
	}

	public SpotPlace getBox(int x, int y) throws IndexOutOfBoundsException {
		if (x < 0 || x > (chessHeight - 1) || y < 0 || y > (chessWidth - 1)) {
			throw new IndexOutOfBoundsException("Index out of bound, out of the Chess Board!".toUpperCase());
		}
		return this.boxes[x][y];
	}

	public void initBoard() {
		for (var i = 0; i < this.boxes.length; i++) {
			for (var j = 0; j < this.boxes[i].length; j++) {
				this.boxes[i][j] = new SpotPlace(i, j, null);
			}
		}
	}

	public void addPieceOnBoard(Player player, Piece piece, int start, int end) throws IllegalArgumentException {
		if (this.getBox(start, end).getPiece() != null) {
			throw new IllegalArgumentException("THIS SPOT IS ALREADY OCCUPIED, PLEASE ENTER VALID POSITIONS!");
		}
		piece.setColor(player.getColor());
		this.boxes[start][end] = new SpotPlace(start, end, piece);
	}

	public void placingPiecesOnBoard(Player player1, Player player2, Map<String, Piece> piecesOfPlayer1,
			Map<String, Piece> piecesOfPlayer2, List<String> typeOfAvailablePiecesPlayer1,
			List<String> typeOfAvailablePiecesPlayer2, int indexPlayer1, int indexPlayer2, ReadInput input) {
		String value = input.getScan().nextLine();
		if (value.equalsIgnoreCase("quit")) {
			GameStatus.setStatus(GameStatus.QUIT_GAME);
		} else {
			this.placingPiecesOnBoardPlayerX(player1, piecesOfPlayer1, typeOfAvailablePiecesPlayer1, indexPlayer1,
					input);
			this.placingPiecesOnBoardPlayerX(player2, piecesOfPlayer2, typeOfAvailablePiecesPlayer2, indexPlayer2,
					input);
		}
	}

	public void placingPiecesOnBoardPlayerX(Player player, Map<String, Piece> piecesOfPlayer,
			List<String> typeOfAvailablePiecesPlayer, int indexPlayer, ReadInput input) {
		while (indexPlayer <= player.getNumberOfPieces() && !GameStatus.isEnd()) {
			try {
				int[] positions = input.enterPiecePositions(player.getName());
				if (this.getBoardFillingType().equals("sequential")) {
					var validSpotSequential = false;
					indexPlayer = this.tryValidPlayerSpot(validSpotSequential, player, piecesOfPlayer, indexPlayer,
							positions);
				} else if (this.getBoardFillingType().equals("map")) {
					var validSpotMap = false;
					indexPlayer = this.tryValidPlayerSpotMap(validSpotMap, player, piecesOfPlayer, indexPlayer,
							positions, typeOfAvailablePiecesPlayer, input);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public int tryValidPlayerSpot(boolean validSpotSequential, Player player, Map<String, Piece> piecesOfPlayer,
			int indexPlayer, int[] positions) {
		while (!validSpotSequential) {
			try {
				this.addPieceOnBoard(player, piecesOfPlayer.get("" + indexPlayer), positions[0], positions[1]);
				validSpotSequential = true;
				indexPlayer++;
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
		}
		return indexPlayer;
	}

	public int tryValidPlayerSpotMap(boolean validSpotMap, Player player, Map<String, Piece> piecesOfPlayer,
			int indexPlayer, int[] positions, List<String> typeOfAvailablePiecesPlayer, ReadInput input) {
		while (!validSpotMap) {
			try {
				String typeOfPiece = input.enterPieceTypeValue(player.getName(), typeOfAvailablePiecesPlayer);
				indexPlayer = this.tryValidPlayerSpotMapBlock(player, piecesOfPlayer, indexPlayer, positions,
						typeOfAvailablePiecesPlayer, typeOfPiece);
				validSpotMap = true;
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
		}
		return indexPlayer;
	}
	
	public int tryValidPlayerSpotMapBlock(Player player, Map<String, Piece> piecesOfPlayer, int indexPlayer,
			int[] positions, List<String> typeOfAvailablePiecesPlayer, String typeOfPiece) {
		try {
			this.addPieceOnBoard(player, piecesOfPlayer.get(typeOfPiece), positions[0], positions[1]);
			piecesOfPlayer.remove(typeOfPiece);
			typeOfAvailablePiecesPlayer.remove(typeOfPiece);
			indexPlayer++;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return indexPlayer;
	}

	public void printBoard() {
		System.out.println();
		System.out.println("------------- This is the CHESSBOARD -------------");
		System.out.println();

		for (var i = 0; i < boxes.length; i++) {
			for (var j = 0; j < boxes[i].length; j++) {
				if (boxes[i][j] == null) {
					System.out.print("[" + i + j + "]______" + " ");
				} else if (boxes[i][j].getPiece() == null) {
					System.out.print("[" + i + j + "]______" + " ");
				} else {
					if (boxes[i][j].getPiece().getColor().equalsIgnoreCase("white")) {
						System.out.print("[" + i + j + "]" + "W-" + boxes[i][j].getPiece().getType() + " ");
					} else {
						System.out.print("[" + i + j + "]" + "B-" + boxes[i][j].getPiece().getType() + " ");
					}
				}
			}
			System.out.println("\n");
		}
	}

	public boolean validPiece(Move move) throws NullPointerException, IllegalArgumentException {
		if (move.getStart().getPiece() == null) {
			this.checkFalsePiece = true;
			throw new NullPointerException("THE PIECE YOU ARE TRYING TO MOVE IS NULL!");
		} else if (move.getStart().getPiece().isWhite() != move.getPlayer().isWhiteSide()) {
			this.checkFalsePiece = true;
			throw new IllegalArgumentException("THE PIECE YOU ARE TRYING TO MOVE IS NOT YOUR PIECE!");
		}
		this.checkFalsePiece = false;
		return true;
	}

	public boolean validMove1(Move move) throws IllegalArgumentException {
		if (move.getEnd().getPiece() == null) {
			try {
				if (!this.validPiece(move)) {
					return false;
				} else if (!move.getStart().getPiece().canMove(move.getStart().getX(), move.getStart().getY(),
						move.getEnd().getX(), move.getEnd().getY())) {
					this.checkFalsePiece = true;
					throw new IllegalArgumentException("THE '" + move.getStart().getPiece().getType()
							+ "' CAN ONLY BE MOVED ACCORDING TO IT'S PATTERN!");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				this.validMove2(move);
				if (this.checkFalsePiece) {
					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (this.checkFalsePiece) {
			return false;
		}
		this.checkFalsePiece = false;
		return true;
	}

	public boolean validMove2(Move move) throws IllegalArgumentException {
		var sourcePiece = move.getStart().getPiece();
		var destPiece = move.getEnd().getPiece();

		try {
			if (!this.validPiece(move)) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (this.checkFalsePiece) {
			return false;
		} else if (sourcePiece.getColor().toLowerCase().equalsIgnoreCase(destPiece.getColor())) {
			this.checkFalsePiece = true;
			throw new IllegalArgumentException("THE DESTINATION PIECE IS YOURS TOO!");
		} else if (!sourcePiece.canMove(move.getStart().getX(), move.getStart().getY(), move.getEnd().getX(),
				move.getEnd().getY())) {
			this.checkFalsePiece = true;
			throw new IllegalArgumentException("THE PIECE CAN ONLY BE MOVED ACCORDING TO IT'S PATTERN!");
		}
		if (destPiece.getType() != null) {
			if (destPiece.isWhite()) {
				this.setNumberOfWhitePieces(this.getNumberOfWhitePieces() - 1);
			} else {
				this.setNumberOfBlackPieces(this.getNumberOfBlackPieces() - 1);
			}
		}
		this.checkFalsePiece = false;
		return true;
	}

	public boolean playerMove(Player player, int startX, int startY, int endX, int endY)
			throws IndexOutOfBoundsException {
		try {
			SpotPlace startBox = this.getBox(startX, startY);
			SpotPlace endBox = this.getBox(endX, endY);
			var move = new Move(player, startBox, endBox);
			return this.makeMoveOnBord(move);
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean makeMoveOnBord(Move move) {
		var sourcePiece = move.getStart().getPiece();
		try {
			this.validMove1(move);
			if (this.checkFalsePiece) {
				return false;
			} else {
				movesPlayed.add(move);
				move.getEnd().setPiece(sourcePiece);
				move.getStart().setPiece(null);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return true;
	}

	public void writeMovesPlayed() {
		try (var br = new BufferedWriter(new FileWriter("FileSettings/moves_played.txt"))) {

			for (Move move : this.getMovesPlayed()) {
				br.write(move.toString());
				br.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Map<String, Piece> parsePieces(List<TableContainer> tableContainer) {
		Map<String, Piece> pieces = new HashMap<>();
		Piece piece = null;
		for (TableContainer tabCont : tableContainer) {
			switch (tabCont.getType()) {
			case "King":
				piece = new SingleKing();
				break;
			case "Rook":
				piece = new LineRook();
				break;
			case "Pawn":
				piece = new Line3Pawn();
				break;
			default:
				piece = new Line3Pawn();
				break;
			}
			piece.setType(tabCont.getType().toUpperCase());
			piece.setNumber(tabCont.getNumber());
			pieces.put(piece.getNumber(), piece);
		}
		return pieces;
	}

	@Override
	public String toString() {
		return "Board properties: [type = " + this.boardFillingType + ", chessBoardHeight = " + this.chessHeight
				+ ", chessBoardWidth = " + this.chessWidth + "]";
	}
}
