package chessgame;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GameLogic {
	boolean breakPoint = false;
	final Logger logger = Logger.getLogger(GameLogic.class.getName());
	String winnerMessage = " is the WINNER!";
	
	public void makeLogic(Board board, Player player1, Player player2, ReadInput input) {
		while (!GameStatus.isEnd()) {
			try {
				input.enterMovingPositions(player1, board, false);
			} catch (Exception e) {
				e.printStackTrace();
			}
			this.makeLogic1(board, player1, input, input.isUser1());
			board.printBoard();
			player2.setNumberOfPieces(board.getNumberOfBlackPieces());
			if (!breakPoint) {
				breakPoint = this.makeLogic2(player1, player2);
			} else {
				break;
			}
			if (!breakPoint) {
				try {
					input.enterMovingPositions(player2, board, false);
				} catch (Exception e) {
					e.printStackTrace();
				}
				breakPoint = this.makeLogic3(board, player1, player2, input, input.isUser2());
			}
		}
	}
	
	public void makeLogic1(Board board, Player player, ReadInput input, boolean isUser) {
		while (isUser && !GameStatus.isEnd()) {
			try {
				input.enterMovingPositions(player, board, false);
				if (player.isWhiteSide()) {
					isUser = input.isUser1();
				} else {
					isUser = input.isUser2();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean makeLogic2(Player player1, Player player2) {
		if (player1.gameOver()) {
			logger.log(Level.INFO, () -> "" + player2.getName().toUpperCase() + winnerMessage);
			breakPoint = true;
		} else if (player2.gameOver()) {
			logger.log(Level.INFO, () -> "" + player1.getName().toUpperCase() + winnerMessage);
			breakPoint = true;
		} else if (GameStatus.isEnd()) {
			breakPoint = true;
		}
		return breakPoint;
	}
	
	public boolean makeLogic3(Board board, Player player1, Player player2, ReadInput input, boolean isUser) {
		this.makeLogic1(board, player2, input, isUser);
		board.printBoard();
		player1.setNumberOfPieces(board.getNumberOfWhitePieces());
		if (!breakPoint) {
			breakPoint = this.makeLogic2(player1, player2);
		}
		return breakPoint;
	}
}
