package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {
	
	
	private ChessMatch chessMatch;


	public King(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
	}

	@Override
	public String toString() {
		return "K";
	}
	
	private boolean canMove(Position position) {  						  	 // DEFINIR COMO O "REI" DEVE SE MOVIMENTAR
		ChessPiece p = (ChessPiece)getBoard().piece(position); 				 // 1- PEGA A PEÇA P QUE ESTÁ NESTA POSIÇÃO
		return p == null || p.getColor() != getColor();						 // 2- VERIFICA SE A PEÇA É NULA OU TBM SE É ADVERSARIA, NESSES CASO PODE MOVER
	}
	
	private boolean testRookCastling(Position position) {				 	 //METODO PRA VERIFICAR SE A TORRE ESTÁ APTA PRA FAZER O ROOK = 0
		ChessPiece p = (ChessPiece)getBoard().piece(position);			     //PEGA A PEÇA QUE ESTÁ NA POSIÇÃO
		return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0; // RETORNA SE P NÃO É NULO, SE FOR A TORRE SE FOR DA MESMA COR E SE NÃO TIVER MOVIMENTADO AINDA
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean [][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0, 0);
		
		// ABOVE (ACIMA)
		p.setValues(position.getRow()-1, position.getColumn()); 			// VAI ANALISAR A POSIÇÃO ACIMA DA PEÇA 
		if (getBoard().positionExists(p) && canMove(p)) {					// 1- SE ESSA PEÇA PODE MOVER PRA POSIÇÃO P
			mat [p.getRow()][p.getColumn()] = true;							// 2- ENTÃO ESSA POSIÇÃO P-ROW E P-COLUM É VERDADEIRO
		}
		
		// BELOW (ABAIXO)
		p.setValues(position.getRow()+1, position.getColumn()); 			// VAI ANALISAR A POSIÇÃO ABAIXO DA PEÇA 
		if (getBoard().positionExists(p) && canMove(p)) {					// 1- SE ESSA PEÇA PODE MOVER PRA POSIÇÃO P
			mat [p.getRow()][p.getColumn()] = true;							// 2- ENTÃO ESSA POSIÇÃO P-ROW E P-COLUM É VERDADEIRO
		}
		
		// LEFT (ESQUERDA)
		p.setValues(position.getRow(), position.getColumn()-1); 			// VAI ANALISAR A POSIÇÃO A ESQUERDA DA PEÇA 
		if (getBoard().positionExists(p) && canMove(p)) {					// 1- SE ESSA PEÇA PODE MOVER PRA POSIÇÃO P
			mat [p.getRow()][p.getColumn()] = true;							// 2- ENTÃO ESSA POSIÇÃO P-ROW E P-COLUM É VERDADEIRO
		}
		// RIGHT (DIREITA)
		p.setValues(position.getRow(), position.getColumn()+1); 			// VAI ANALISAR A POSIÇÃO A DIREITA DA PEÇA 
		if (getBoard().positionExists(p) && canMove(p)) {					// 1- SE ESSA PEÇA PODE MOVER PRA POSIÇÃO P
			mat [p.getRow()][p.getColumn()] = true;							// 2- ENTÃO ESSA POSIÇÃO P-ROW E P-COLUM É VERDADEIRO
		}
		// NW (NOROESTE)
		p.setValues(position.getRow()-1, position.getColumn()-1); 			// VAI ANALISAR A POSIÇÃO A NOROESTE DA PEÇA 
		if (getBoard().positionExists(p) && canMove(p)) {					// 1- SE ESSA PEÇA PODE MOVER PRA POSIÇÃO P
			mat [p.getRow()][p.getColumn()] = true;							// 2- ENTÃO ESSA POSIÇÃO P-ROW E P-COLUM É VERDADEIRO
		}
		// NE (NORDESTE)
		p.setValues(position.getRow()-1, position.getColumn()+1); 			// VAI ANALISAR A POSIÇÃO A NORDESTE DA PEÇA 
		if (getBoard().positionExists(p) && canMove(p)) {					// 1- SE ESSA PEÇA PODE MOVER PRA POSIÇÃO P
			mat [p.getRow()][p.getColumn()] = true;							// 2- ENTÃO ESSA POSIÇÃO P-ROW E P-COLUM É VERDADEIRO
		}
		// SW (SUDOESTE)
		p.setValues(position.getRow()+1, position.getColumn()-1); 			// VAI ANALISAR A POSIÇÃO A SUDOESTE DA PEÇA 
		if (getBoard().positionExists(p) && canMove(p)) {					// 1- SE ESSA PEÇA PODE MOVER PRA POSIÇÃO P
			mat [p.getRow()][p.getColumn()] = true;							// 2- ENTÃO ESSA POSIÇÃO P-ROW E P-COLUM É VERDADEIRO
		}
		// SE (SUDESTE)
		p.setValues(position.getRow()+1, position.getColumn()+1); 			// VAI ANALISAR A POSIÇÃO A SUDESTE DA PEÇA 
		if (getBoard().positionExists(p) && canMove(p)) {					// 1- SE ESSA PEÇA PODE MOVER PRA POSIÇÃO P
			mat [p.getRow()][p.getColumn()] = true;							// 2- ENTÃO ESSA POSIÇÃO P-ROW E P-COLUM É VERDADEIRO
		}
		
		// #Specialmove Castling
		if(getMoveCount() == 0 && !chessMatch.getCheck()) {					// SE O GETMOVECOUNT DO REI É 0 E SE NÃO ESTÁ EM CHECK
			// #Specialmove Castling kingside Rook
			Position posT1 = new Position(position.getRow(), position.getColumn() + 3); // PEGA A POSIÇÃO AO LADO DO REI MESMA LINHA E COLUNA +3
			if (testRookCastling(posT1)) {												// TESTA SE A TORRE QUE ESTÁ NA POSIÇÃO QUE FOI PEGA ACIMA
				Position p1 = new Position(position.getRow(), position.getColumn() + 2);// TESTA A POSIÇÃO AO LADO DO REI +2, SE NÃO TEM PEÇA
				Position p2 = new Position(position.getRow(), position.getColumn() + 1);// TESTA A POSIÇÃO AO LADO DO REI +1, SE NÃO TEM PEÇA
				if (getBoard().piece(p1) == null && getBoard().piece(p2) == null) {     // SE ESSAS 2 POSIÇÕES FOR NULLO, FAÇA
					mat[position.getRow()][position.getColumn()+2] = true;          
					
			 	}
		}
		// #Specialmove Castling queenside Rook
		Position posT2 = new Position(position.getRow(), position.getColumn() - 4); // PEGA A POSIÇÃO AO LADO DO REI MESMA LINHA E COLUNA +3
		if (testRookCastling(posT2)) {												// TESTA SE A TORRE QUE ESTÁ NA POSIÇÃO QUE FOI PEGA ACIMA
			Position p1 = new Position(position.getRow(), position.getColumn() - 1);// TESTA A POSIÇÃO AO LADO DO REI +1, SE NÃO TEM PEÇA
			Position p2 = new Position(position.getRow(), position.getColumn() - 2);// TESTA A POSIÇÃO AO LADO DO REI +2, SE NÃO TEM PEÇA
			Position p3 = new Position(position.getRow(), position.getColumn() - 3);// TESTA A POSIÇÃO AO LADO DO REI +3, SE NÃO TEM PEÇA
			if (getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null) {     // SE ESSAS 2 POSIÇÕES FOR NULLO, FAÇA
				mat[position.getRow()][position.getColumn()-2] = true;          
				
				}  
			}
		
		}
		return mat;
	}
}
