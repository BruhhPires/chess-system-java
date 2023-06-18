package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

	public Pawn(Board board, Color color) {
		super(board, color);
		

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
	}
		else {
		p.setValues(position.getRow() +1, position.getColumn()); 							// ELA PODE MOVIMENTAR PRA CIMA 1 CASA
		if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){			// SE A POSIÇÃO EXISTE E NÃO HÁ PEÇAS
			mat[p.getRow()][p.getColumn()] = true;									// ENTÃO É VERDADEIRO
		}
		p.setValues(position.getRow() +2, position.getColumn()); 							// ELA PODE MOVIMENTAR PRA CIMA 2 CASA
		Position p2 = new Position(position.getRow() -1, position.getColumn());		// VERIFICA SE A CASA 1 TBM ESTÁ LIVRE
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
		}

	return mat;
	}

	@Override
	public String toString() {
		return "P";
	}
}