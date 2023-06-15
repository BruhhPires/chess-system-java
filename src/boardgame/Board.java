package boardgame;

public class Board {
	
	private int rows;
	private int columns;
	private Piece[][] pieces;
	
	public Board() {
	}
	
	public Board(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}
	
	public Piece piece(int row, int column) { 
		return pieces[row][column]; // RETORNA A MATRIZ PIECES NA LINHA ROW E COLUNA COLUMN
	}
	
	public Piece piece(Position position) { // É UMA SOBRECARGA NO METODO ACIMA USANDO ARGUEMNTO POSITION/
		return pieces[position.getRow()][position.getColumn()]; // POREM RETORNANDO PELA POSIÇÃO
	}
	
	public void placePiece(Piece piece, Position position) { // METODO PEGA A MATRIZ NA POSIÇÃO DADA E ATRIBUI A PEÇA QUE FOI INFORMADA
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position; // AQUI RECEBE A POSIÇÃO INFORMADA NO METODO
      	}
}
