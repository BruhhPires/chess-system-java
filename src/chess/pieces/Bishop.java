package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Bishop extends ChessPiece { // É A PEÇA TORRE, É UMA SUBCLASSE DO CHESS

	public Bishop(Board board, Color color) {
		super(board, color);

	}
	
	@Override
	public String toString() {
		return "B";
	}
	
	@Override
	public boolean[][] possibleMoves() {
		boolean [][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0, 0);
		
		//NW (NOROESTE)
		p.setValues(position.getRow() - 1, position.getColumn()-1); 						// CONFIRMA SE AS POSIÇÕES ACIMA DA PEÇA SÃO VERDADEIRA
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
			mat[p.getRow()][p.getColumn()]=true;
			p.setValues(p.getRow()-1, p.getColumn()-1);
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {					// CONFIRMA SE AS POSIÇÕES ACIMA TEM OPONETE
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//NE (NORDESTE)
		p.setValues(position.getRow() - 1, position.getColumn() + 1); 						// CONFIRMA SE AS POSIÇÕES ESQUERDA DA PEÇA SÃO VERDADEIRA
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
			mat[p.getRow()][p.getColumn()]=true;
			p.setValues(p.getRow()-1, p.getColumn()+1);
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {					// CONFIRMA SE AS POSIÇÕES ESQUERDA TEM OPONENTE
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//SE (SUDESTE)
		p.setValues(position.getRow() + 1, position.getColumn() + 1); 						// CONFIRMA SE AS POSIÇÕES DIREITA DA PEÇA SÃO VERDADEIRA
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
			mat[p.getRow()][p.getColumn()]=true;
			p.setValues(p.getRow() + 1, p.getColumn() + 1);	
			}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {					// CONFIRMA SE AS POSIÇÕES DIREITA TEM OPONENTE
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//SW (SUDOESTE)
		p.setValues(position.getRow() + 1, position.getColumn() - 1); 						// CONFIRMA SE AS POSIÇÕES ABAIXO DA PEÇA SÃO VERDADEIRA
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
			mat[p.getRow()][p.getColumn()]=true;
			p.setValues(p.getRow() + 1, p.getColumn() - 1);
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {					// CONFIRMA SE AS POSIÇÕES ABAIXO TEM OPONETE
			mat[p.getRow()][p.getColumn()] = true;
		}
	
		return mat;

	}

}
