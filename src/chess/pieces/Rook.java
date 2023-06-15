package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece { // É A PEÇA TORRE, É UMA SUBCLASSE DO CHESS

	public Rook(Board board, Color color) {
		super(board, color);

	}
	
	@Override
	public String toString() {
		return "R";
	}

}
