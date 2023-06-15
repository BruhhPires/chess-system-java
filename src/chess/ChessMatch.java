package chess;

import boardgame.Board;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch { // CLASSE PRINCIPAL, ONDE ENCONTRA-SE AS REGRAS 
	
	private Board board;
	
	public ChessMatch() {
		board = new Board(8, 8); //DELEGAÇÃO TAMANHO DO TABULEIRO
		initialSetup();
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
	
	private void initialSetup() { // METODO RESPONSAVEL POR INICIAR AS POSIÇÕES DAS PEÇAS NO TABULEIRO
		board.placePiece(new Rook(board, Color.WHITE), 	new Position(2, 1));; // INSTANCIA A PEÇA/ COR E SUA POSIÇÃO
		board.placePiece(new King(board, Color.BLACK), new Position(0, 4)); // INSTANCIA A PEÇA/ COR E SUA POSIÇÃO
		board.placePiece(new King(board, Color.WHITE), new Position(7, 4)); // INSTANCIA A PEÇA/ COR E SUA POSIÇÃO
	}
}
