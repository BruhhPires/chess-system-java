package boardgame;

public class Board {
	
	private int rows;
	private int columns;
	private Piece[][] pieces;
	
	public Board() {
	}
	
	public Board(int rows, int columns) {
		if (rows < 1 || columns <1){								 // METODO DEFENSIVO, EVITAR O ERRO E QUE PARE DE FUNCIONAR
			throw new BoardException("Error creating board: there must be at least 1 row and 1 column");
		}
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
		}


	public int getRows() {
		return rows;
	}


	public int getColumns() {
		return columns;
	}

	public Piece piece(int row, int column) { 
		if(!positionExists(row, column)) { 							// ESSE IF É "SE ESSA POSIÇÃO NÃO EXISTE"
			throw new BoardException("Position not on the board");  // ESSE METODO DEFENSIVO EVITA QUE SEJA LANÇADO POSIÇÃO NÃO EXISTENTE
		}
		return pieces[row][column]; 								// RETORNA A MATRIZ PIECES NA LINHA ROW E COLUNA COLUMN
	}
	
	public Piece piece(Position position) { 						// É UMA SOBRECARGA NO METODO ACIMA USANDO ARGUEMNTO POSITION/ // POREM RETORNANDO PELA POSIÇÃO
		if(!positionExists(position)) { 							// ESSE IF É "SE ESSA POSIÇÃO NÃO EXISTE"
			throw new BoardException("Position not on the board");  // ESSE METODO DEFENSIVO EVITA QUE SEJA LANÇADO POSIÇÃO NÃO EXISTENTE
		}
		return pieces[position.getRow()][position.getColumn()];
	}
	
	public void placePiece(Piece piece, Position position) {		// METODO PEGA A MATRIZ NA POSIÇÃO DADA E ATRIBUI A PEÇA QUE FOI INFORMADA
		if(thereIsAPiece(position)) { 								// VERIFICA SE JÁ TEM UMA PEÇA NESTA POSIÇÃO.
			throw new BoardException("There is Already a piece on position " + position);
		}
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position; 									// AQUI RECEBE A POSIÇÃO INFORMADA NO METODO
      	}
	
	public Piece removePiece(Position position) { 					// ** METODO DE REMOVER A PEÇA **
		if(!positionExists(position)) {
			throw new BoardException("Position not on the board");  // ESSE METODO DEFENSIVO EVITA QUE SEJA LANÇADO POSIÇÃO NÃO EXISTENTE
		}
		if (piece(position) == null) {								// SE A POSIÇÃO FOR NULLA NÃO VAI REMOVER PEÇA ALGUMA
			return null;
		}
		Piece aux = piece(position);								// PARA REMOVER A PEÇA, LANÇAMOS A AUX NA POSIÇÃO E TRANSFORMAMOS AUX EM NULO
		aux.position = null;
		pieces[position.getRow()][position.getColumn()] = null; 	// PARA REMOVER A PEÇA, LANÇAMOS A AUX NA POSIÇÃO E TRANSFORMAMOS AUX EM NULO
		return aux;													// RETORNA AUX COM VALOR NULO
	}

	
	private boolean positionExists(int row, int column) { 			// METODO CONFIRMA SE EXISTE A PEÇA NA POSIÇÃO
		return row >=0 && row < rows && column >= 0 && column < columns; // VALIDA SE O VALOR EXISTE CONFORME O PARAMETRO APRESENTADO
	} 																// ESSA É A CONDIÇÃO COMPLETA PRA VER SE A POSIÇÃO EXISTE
	
	public boolean positionExists(Position position) {
		return positionExists(position.getRow(), position.getColumn()); // ESSE METODO ESTÁ APROVEITANDO O ROW E COLUMN DE CIMA
	}
	
	public boolean thereIsAPiece(Position position) {
		if(!positionExists(position)) { 							// ESSE IF É "SE ESSA POSIÇÃO NÃO EXISTE"
			throw new BoardException("Position not on the board");  // ESSE METODO DEFENSIVO EVITA QUE SEJA LANÇADO POSIÇÃO NÃO EXISTENTE
		}
		return piece(position) != null; 							// VERIFICA SE TEM UMA PEÇA, SE DIFERENTE DE NULO É PQ TEM
	}
}
