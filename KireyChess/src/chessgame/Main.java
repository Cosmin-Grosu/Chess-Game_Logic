package chessgame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class Main {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		Map<String, Piece> piecesOfPlayer1 = null;
		Map<String, Piece> piecesOfPlayer2 = null;
		var board = new Board();
		final var logger = Logger.getLogger(Main.class.getName());
		logger.log(Level.INFO, () -> "************* The game has started *************");

		logger.log(Level.INFO, () -> "Enter player1's name: ");
		var scan = new Scanner(System.in);
		String player1Name = scan.nextLine();
		logger.log(Level.INFO, () -> "Enter player2's name: ");
		String player2Name = scan.nextLine();

		var player1 = new Player(player1Name, PlayerColor.WHITE.toString());
		var player2 = new Player(player2Name, PlayerColor.BLACK.toString());
		var prop = new ReadPropertyFile();
		prop.readProperty();
		TableSettingsDao tableSettingsDao = new TableSettingsDaoImpl(prop);
		var containerPieces = new ContainerPieces(tableSettingsDao);

		// Decide if the properties info's are read from database (else -> it is read
		// from files)
		if (prop.getReadFromDB().equals("true")) {
			piecesOfPlayer1 = board.parsePieces(containerPieces.readContainerFromDB());
			piecesOfPlayer2 = board.parsePieces(containerPieces.readContainerFromDB());
			board.setChessHeight((tableSettingsDao.getProperties()).getChessBoardHeight());
			board.setChessWidth(tableSettingsDao.getProperties().getChessBoardWidth());
			board.setBoardFillingType(tableSettingsDao.getProperties().getBoardFillingType());
		} else {
			piecesOfPlayer1 = board.parsePieces(containerPieces.readContainerFromXML(prop.getContainer()));
			piecesOfPlayer2 = board.parsePieces(containerPieces.readContainerFromXML(prop.getContainer()));
			board.setChessHeight(Integer.valueOf(prop.getChessBoardHeight()));
			board.setChessWidth(Integer.valueOf(prop.getChessBoardWidth()));
			board.setBoardFillingType(prop.getBoardFillingType());
		}

		GameStatus.setStatus(GameStatus.ACTIVE);
		player1.setNumberOfPieces(piecesOfPlayer1.size());
		player2.setNumberOfPieces(piecesOfPlayer2.size());
		var input = new ReadInput(scan);
		var indexPlayer1 = 1;
		var indexPlayer2 = 1;

		ArrayList<String> typeOfAvailablePiecesPlayer1 = new ArrayList<>();
		ArrayList<String> typeOfAvailablePiecesPlayer2 = new ArrayList<>();
		for (String numberOfPiece : piecesOfPlayer1.keySet()) {
			typeOfAvailablePiecesPlayer1.add(numberOfPiece);
		}
		for (String numberOfPiece : piecesOfPlayer2.keySet()) {
			typeOfAvailablePiecesPlayer2.add(numberOfPiece);
		}

		board.setBoxes(new SpotPlace[board.getChessHeight()][board.getChessWidth()]);
		board.setNumberOfWhitePieces(player1.getNumberOfPieces());
		board.setNumberOfBlackPieces(player2.getNumberOfPieces());
		board.initBoard();
		board.placingPiecesOnBoard(player1, player2, piecesOfPlayer1, piecesOfPlayer2, typeOfAvailablePiecesPlayer1,
				typeOfAvailablePiecesPlayer2, indexPlayer1, indexPlayer2, input);
		
		if (GameStatus.isEnd()) {
			logger.log(Level.INFO, () -> "" + GameStatus.getStatus());
			logger.log(Level.INFO, () -> "************* The game has finished *************");
		} else {
			board.printBoard();

			var gameLogic = new GameLogic();
			gameLogic.makeLogic(board, player1, player2, input);

			if (prop.getReadFromDB().equals("true")) {
				tableSettingsDao.writeMovesPlayed(board.getMovesPlayed());
			} else {
				board.writeMovesPlayed();
			}

			logger.log(Level.INFO, () -> "" + GameStatus.getStatus());
			logger.log(Level.INFO, () -> "************* The game has finished *************");
		}
	}
}
