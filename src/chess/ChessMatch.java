package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {														 // CLASSE PRINCIPAL, ONDE ENCONTRA-SE AS REGRAS 
	
	private Board board;
	
	public ChessMatch() {
		board = new Board(8, 8); 												//DELEGAÇÃO TAMANHO DO TABULEIRO
		initialSetup();
	}
	public ChessPiece[][] getPieces(){ 											// PERCORRER A MATRIZ E FAZER UM DOWNCAST PRA CHESSPIECE
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
			for (int i=0; i<board.getRows(); i++) {
				for (int j=0; j<board.getColumns(); j++) {
					mat[i][j] = (ChessPiece) board.piece(i, j); 				// DOWNCAST
				}
			}
			return mat;
	}
	
	public ChessPiece performChessMove (ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source); 
		Piece capturedPiece = makeMove(source, target);
		return (ChessPiece)capturedPiece;
	}
	
	private Piece makeMove(Position source, Position target) {					// METODO MOVER A PEÇA - COMER
		Piece p = board.removePiece(source);									// RETIRA A PEÇA DA POSIÇÃO DE ORIGEM
		Piece capturedPiece = board.removePiece(target);						// ESSA VAI SER A PEÇA CAPTURADA NA POSIÇÃO DE DESTINO
		board.placePiece(p, target); 											// COLOCA A PEÇA P NA POSIÇÃO DE DESTINO
		return capturedPiece; 													// RETORNA A PEÇA CAPTURADA
		
	}
	
	private void validateSourcePosition (Position position) {
		if(!board.thereIsAPiece(position)) {
			throw new ChessException("There is no piece on source position");
		}
		if(!board.piece(position).isThereAnyPossibleMove()) { 					// VERIFICA SE HÁ MOVIMENTOS POSSIVEIS
			throw new ChessException("There is no possible moves for the chosen piece");
		}
	}
	
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition()); 	// ESSE METODO TRANSFORMA A FORMA DE LANÇAR O VALOR PRA MODO XADREX
	}
	
	private void initialSetup() { 												// METODO RESPONSAVEL POR INICIAR AS POSIÇÕES DAS PEÇAS NO TABULEIRO // 
																				// ESTA USANDO O METODO CRIADO ACIMA 'PLACENEWPIECE"
		placeNewPiece('c', 1, new Rook(board, Color.WHITE));		
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
	}
}
