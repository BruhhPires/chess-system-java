package chess;

import boardgame.Board;

public class ChessMatch { // CLASSE PRINCIPAL, ONDE ENCONTRA-SE AS REGRAS 
	
	private Board board;
	
	public ChessMatch() {
		board = new Board(8, 8); //DELEGAÇÃO TAMANHO DO TABULEIRO
	}
	public ChessPiece[][] getPieces(){ // PERCORRER A MATRIZ E FAZER UM DOWNCAST PRA CHESSPIECE
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
			for (int i=0; i<board.getRows(); i++) {
				for (int j=0; j<board.getColumns(); j++) {
					mat[i][j] = (ChessPiece) board.piece(i, j); // DOWNCAST
				}
			}
			return mat;
	}
}
