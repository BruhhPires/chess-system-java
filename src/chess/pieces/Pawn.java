package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {
	
	private ChessMatch chessMatch;

	public Pawn(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
		

}

	@Override
	public boolean[][] possibleMoves() {
	boolean [][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
	Position p = new Position(0, 0);
	
		if(getColor() == Color.WHITE) {													// SE A PEÇA FOR BRANCA ENTÃO					
			p.setValues(position.getRow() -1, position.getColumn()); 							// ELA PODE MOVIMENTAR PRA CIMA 1 CASA
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){			// SE A POSIÇÃO EXISTE E NÃO HÁ PEÇAS
				mat[p.getRow()][p.getColumn()] = true;									// ENTÃO É VERDADEIRO
			}
			p.setValues(position.getRow() -2, position.getColumn()); 							// ELA PODE MOVIMENTAR PRA CIMA 2 CASA
			Position p2 = new Position(position.getRow() -1, position.getColumn());		// VERIFICA SE A CASA 1 TBM ESTÁ LIVRE
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && 
				getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0){// SE A POSIÇÃO EXISTE,NÃO HÁ PEÇAS E A PEÇA NÃO TIVER MECHIDO AINDA
				mat[p.getRow()][p.getColumn()] = true;									// ENTÃO É VERDADEIRO
			}	
			p.setValues(position.getRow() -1, position.getColumn()-1); 							// ELA PODE MOVIMENTAR PRA NOROESTE
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)){			    // SE A POSIÇÃO EXISTE E NÃO HÁ PEÇAS
				mat[p.getRow()][p.getColumn()] = true;									// ENTÃO É VERDADEIRO
			}
			p.setValues(position.getRow() -1, position.getColumn()+1); 							// ELA PODE MOVIMENTAR PRA NORDESTE
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)){			    // SE A POSIÇÃO EXISTE E NÃO HÁ PEÇAS
				mat[p.getRow()][p.getColumn()] = true;									// ENTÃO É VERDADEIRO
			}
			
			// #specialmove en passant white
			if(position.getRow() == 3) {												// SE A POSIÇÃO DA PEÇA BRANCA É IGUAL A LINHA 3
				Position left = new Position(position.getRow(), position.getColumn() -1);// PEGA APOSIÇÃO A ESQUERDA
				if(getBoard().positionExists(left) && isThereOpponentPiece(left) && 
					getBoard().piece(left) == chessMatch.getEnPassantVunerable()) {		 // TESTA SE A POSICAO A ESQUERDA ESTÁ OCUPADA, SE É OPOENTE E SE ESTÁ EM LE PASSANT VUNERABILI
					mat[left.getRow()-1][left.getColumn()] = true;						 // ENTÃO É VERDADEIRO E O PEÃO PODE PEGAR A POSIÇÃO ACIMA DA PEÇA A ESQUERDA
			}
			Position right = new Position(position.getRow(), position.getColumn() + 1);// PEGA APOSIÇÃO A ESQUERDA
			if(getBoard().positionExists(right) && isThereOpponentPiece(right) && 
				getBoard().piece(right) == chessMatch.getEnPassantVunerable()) {		 // TESTA SE A POSICAO A ESQUERDA ESTÁ OCUPADA, SE É OPOENTE E SE ESTÁ EM LE PASSANT VUNERABILI
				mat[right.getRow() - 1][right.getColumn()] = true;						 // ENTÃO É VERDADEIRO E O PEÃO PODE PEGAR A POSIÇÃO ACIMA DA PEÇA A ESQUERDA
					}
				}
			}
			else {
			p.setValues(position.getRow() +1, position.getColumn()); 							// ELA PODE MOVIMENTAR PRA CIMA 1 CASA
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){			// SE A POSIÇÃO EXISTE E NÃO HÁ PEÇAS
				mat[p.getRow()][p.getColumn()] = true;									// ENTÃO É VERDADEIRO
			}
			p.setValues(position.getRow() +2, position.getColumn()); 							// ELA PODE MOVIMENTAR PRA CIMA 2 CASA
			Position p2 = new Position(position.getRow()  + 1, position.getColumn());		// VERIFICA SE A CASA 1 TBM ESTÁ LIVRE
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && 
				getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0){// SE A POSIÇÃO EXISTE,NÃO HÁ PEÇAS E A PEÇA NÃO TIVER MECHIDO AINDA
				mat[p.getRow()][p.getColumn()] = true;									// ENTÃO É VERDADEIRO
			}	
			p.setValues(position.getRow() +1, position.getColumn()-1); 							// ELA PODE MOVIMENTAR PRA NOROESTE
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)){			    // SE A POSIÇÃO EXISTE E NÃO HÁ PEÇAS
				mat[p.getRow()][p.getColumn()] = true;									// ENTÃO É VERDADEIRO
			}
			p.setValues(position.getRow() +1, position.getColumn()+1); 							// ELA PODE MOVIMENTAR PRA NORDESTE
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)){			    // SE A POSIÇÃO EXISTE E NÃO HÁ PEÇAS
				mat[p.getRow()][p.getColumn()] = true;									// ENTÃO É VERDADEIRO
			}
			// #specialmove en passant black
			if(position.getRow() == 4) {												// SE A POSIÇÃO DA PEÇA PRETA É IGUAL A LINHA 4
				Position left = new Position(position.getRow(), position.getColumn() -1);// PEGA APOSIÇÃO A ESQUERDA
				if(getBoard().positionExists(left) && isThereOpponentPiece(left) && 
					getBoard().piece(left) == chessMatch.getEnPassantVunerable()) {	 // TESTA SE A POSICAO A ESQUERDA ESTÁ OCUPADA, SE É OPOENTE E SE ESTÁ EM LE PASSANT VUNERABILI
					mat[left.getRow() +1][left.getColumn()] = true;						 // ENTÃO É VERDADEIRO E O PEÃO PODE PEGAR A POSIÇÃO ACIMA DA PEÇA A ESQUERDA
				}
				Position right = new Position(position.getRow(), position.getColumn() + 1);// PEGA APOSIÇÃO A ESQUERDA
				if(getBoard().positionExists(right) && isThereOpponentPiece(right) && 
					getBoard().piece(right) == chessMatch.getEnPassantVunerable()) {		 // TESTA SE A POSICAO A ESQUERDA ESTÁ OCUPADA, SE É OPOENTE E SE ESTÁ EM LE PASSANT VUNERABILI
					mat[right.getRow() + 1][right.getColumn()] = true;						 // ENTÃO É VERDADEIRO E O PEÃO PODE PEGAR A POSIÇÃO ACIMA DA PEÇA A ESQUERDA
				}
			} 
		}	return mat;
	}

	@Override
	public String toString() {
		return "P";
	}
}
