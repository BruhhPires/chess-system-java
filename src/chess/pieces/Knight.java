package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Knight extends ChessPiece {

	public Knight(Board board, Color color) {
		super(board, color);
		
	}

	@Override
	public String toString() {
		return "N";
	}
	
	private boolean canMove(Position position) {  						  	 // DEFINIR COMO O "REI" DEVE SE MOVIMENTAR
		ChessPiece p = (ChessPiece)getBoard().piece(position); 				 // 1- PEGA A PEÇA P QUE ESTÁ NESTA POSIÇÃO
		return p == null || p.getColor() != getColor();						 // 2- VERIFICA SE A PEÇA É NULA OU TBM SE É ADVERSARIA, NESSES CASO PODE MOVER
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean [][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0, 0);
		
		p.setValues(position.getRow()-1, position.getColumn()-2); 			// VAI ANALISAR A POSIÇÃO ACIMA DA PEÇA 
		if (getBoard().positionExists(p) && canMove(p)) {					// 1- SE ESSA PEÇA PODE MOVER PRA POSIÇÃO P
			mat [p.getRow()][p.getColumn()] = true;							// 2- ENTÃO ESSA POSIÇÃO P-ROW E P-COLUM É VERDADEIRO
		}
		
		p.setValues(position.getRow()+2, position.getColumn()-1); 			// VAI ANALISAR A POSIÇÃO ABAIXO DA PEÇA 
		if (getBoard().positionExists(p) && canMove(p)) {					// 1- SE ESSA PEÇA PODE MOVER PRA POSIÇÃO P
			mat [p.getRow()][p.getColumn()] = true;							// 2- ENTÃO ESSA POSIÇÃO P-ROW E P-COLUM É VERDADEIRO
		}
		
		p.setValues(position.getRow()-2, position.getColumn()+1); 			// VAI ANALISAR A POSIÇÃO A ESQUERDA DA PEÇA 
		if (getBoard().positionExists(p) && canMove(p)) {					// 1- SE ESSA PEÇA PODE MOVER PRA POSIÇÃO P
			mat [p.getRow()][p.getColumn()] = true;							// 2- ENTÃO ESSA POSIÇÃO P-ROW E P-COLUM É VERDADEIRO
		}
		p.setValues(position.getRow()+1 , position.getColumn()+2); 			// VAI ANALISAR A POSIÇÃO A DIREITA DA PEÇA 
		if (getBoard().positionExists(p) && canMove(p)) {					// 1- SE ESSA PEÇA PODE MOVER PRA POSIÇÃO P
			mat [p.getRow()][p.getColumn()] = true;							// 2- ENTÃO ESSA POSIÇÃO P-ROW E P-COLUM É VERDADEIRO
		}
		p.setValues(position.getRow()+2, position.getColumn()-1); 			// VAI ANALISAR A POSIÇÃO A NOROESTE DA PEÇA 
		if (getBoard().positionExists(p) && canMove(p)) {					// 1- SE ESSA PEÇA PODE MOVER PRA POSIÇÃO P
			mat [p.getRow()][p.getColumn()] = true;							// 2- ENTÃO ESSA POSIÇÃO P-ROW E P-COLUM É VERDADEIRO
		}
		p.setValues(position.getRow()+1, position.getColumn()-2); 			// VAI ANALISAR A POSIÇÃO A NORDESTE DA PEÇA 
		if (getBoard().positionExists(p) && canMove(p)) {					// 1- SE ESSA PEÇA PODE MOVER PRA POSIÇÃO P
			mat [p.getRow()][p.getColumn()] = true;							// 2- ENTÃO ESSA POSIÇÃO P-ROW E P-COLUM É VERDADEIRO
		}
		p.setValues(position.getRow()-2, position.getColumn()-1); 			// VAI ANALISAR A POSIÇÃO A NORDESTE DA PEÇA 
		if (getBoard().positionExists(p) && canMove(p)) {					// 1- SE ESSA PEÇA PODE MOVER PRA POSIÇÃO P
			mat [p.getRow()][p.getColumn()] = true;							// 2- ENTÃO ESSA POSIÇÃO P-ROW E P-COLUM É VERDADEIRO
		}
		p.setValues(position.getRow()+2, position.getColumn()+1); 			// VAI ANALISAR A POSIÇÃO A NORDESTE DA PEÇA 
		if (getBoard().positionExists(p) && canMove(p)) {					// 1- SE ESSA PEÇA PODE MOVER PRA POSIÇÃO P
			mat [p.getRow()][p.getColumn()] = true;							// 2- ENTÃO ESSA POSIÇÃO P-ROW E P-COLUM É VERDADEIRO
		}
		p.setValues(position.getRow()-1, position.getColumn()+2); 			// VAI ANALISAR A POSIÇÃO A NORDESTE DA PEÇA 
		if (getBoard().positionExists(p) && canMove(p)) {					// 1- SE ESSA PEÇA PODE MOVER PRA POSIÇÃO P
			mat [p.getRow()][p.getColumn()] = true;							// 2- ENTÃO ESSA POSIÇÃO P-ROW E P-COLUM É VERDADEIRO
		}
				
		return mat;
	}
}
