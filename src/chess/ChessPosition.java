package chess;

import boardgame.Position;

public class ChessPosition {
	
	private char column;
	private int row;
	
	public ChessPosition(char column, int row) {
		if(column < 'a' || column >'h' || row <1 || row >8) {
		throw new ChessException("Error Instantianting ChessPosition. Valid values are from a1 to h8.");
	}
		this.column = column;
		this.row = row;
	}

	public char getColumn() {
		return column;
	}


	public int getRow() {
		return row;
	}
	
	protected Position toPosition() { // METODO CONVERSÃO XADREX PARA POSIÇÃO NA MATRIX
		return new Position(8- row, column -'a');
	}
	
	protected static ChessPosition fromPosition(Position position) { // METODO PARA CONVERSÃO MATRIX PARA XADREX
		return new ChessPosition((char)('a' + position.getColumn()), 8 -position.getRow() ); // PRECISA USAR O CASTING PARA -- XADRE É 1º COLUNA 2º LINHA
	}
	
	@Override
	public String toString() { // IMPRESSÃO DESSA POSIÇÃO LEMBRABDO QUE O " " FORÇA O TOSTRING A CONCATENAR AUTOMATICO
		return " " + column + row;
	}

}
