package chess;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class ChessMatch {														 // CLASSE PRINCIPAL, ONDE ENCONTRA-SE AS REGRAS 
	private int turn;
	private Color currentPlayer;
	private Board board;
	private boolean check;
	private boolean checkMate;	
	private ChessPiece enPassantVunerable;
	private ChessPiece promoted;
	
	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();

	public ChessMatch() {
		board = new Board(8, 8); 												//DELEGAÇÃO TAMANHO DO TABULEIRO
		turn = 1;																//PRIMEIRO TURNO VALE 1											
		currentPlayer = Color.WHITE;											//PRIEMIRO TURNO INICIA COM COR BRANCA
		initialSetup();
	}
	
	public int getTurn() {
		return turn;
	}
	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	public boolean getCheck() {
		return check;
	}
	public boolean getCheckMate() {
		return checkMate;
	}
	public ChessPiece getEnPassantVunerable() {
		return enPassantVunerable;
	}
	public ChessPiece getPromoted() {
		return promoted;
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
	
	public boolean[][] possibleMove(ChessPosition sourcePosition){				// ESSA OPERAÇÃO É PRA VERIFICAR AS POSIÇÕES POSSIVEIS APARTIR DA ORIGEM(SOURCE)
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
	}
	
	public ChessPiece performChessMove (ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source); 
		validateTargetPosition(source,target);
		Piece capturedPiece = makeMove(source, target);
		
		if (testCheck(currentPlayer)) {											// ANTES DE FINALIZAR A JOGADA O TEST CHECK É CHAMADO PRA VERIFICAR SE FOI COLCOADO EM CHECK 
			undoMove(source, target, capturedPiece);							// SE VERDADEIRO CHAMA O UNDOMOVE QUE DESFAZ A JOGADA
			throw new ChessException("You can't put yourself in check");		// E LANÇA A EXCEÇÃO
		}
		
		ChessPiece movedPiece = (ChessPiece)board.piece(target);
		
		// # Specialmove promotion
		promoted = null;
		if(movedPiece instanceof Pawn) {
			if (movedPiece.getColor() == Color.WHITE && target.getRow() == 0 ||movedPiece.getColor() == Color.BLACK && target.getRow() == 7 ) {
				promoted = (ChessPiece)board.piece(target);
				promoted = replacePromotedPiece("Q");
			}
		}
		
		check = (testCheck(opponnent(currentPlayer))) ? true : false;			// SE O OPONENT FICOU EM CHECK A PROPRIEDADE CHECK SERÁ ATUALIZADA PARA VERDADEIRA, SENÃO É FALSO
		
		
		if (testCheckMate(opponnent(currentPlayer))) {							// METODO PRA VALIDAR SE O OPONENTE FICOU EM CHECKMATE
			checkMate = true;													// SE VERDADEIRA, ACABA O JOGO
		}
		else {																	// SE FALSE(SAIU DO CHECK, AI VOLTA O TURNO PRA ELE
		nextTurn();																//CHAMA O METODO PRA TROCA O TURN E JOGADOR ANTES DE RETURNAR 
		}
		
		// #SPECIALMOVE EN PASSANT
		
		if (movedPiece instanceof Pawn && (target.getRow() == source.getRow() -2 || target.getRow() == source.getRow() +2)) {
			enPassantVunerable = movedPiece;
		}
		else {
			enPassantVunerable = null;
		}
		
		
		return (ChessPiece)capturedPiece;
	}
	
	public ChessPiece replacePromotedPiece(String type) {
		if (promoted == null) {
			throw new IllegalStateException ("There is no piece to be promoted");
			
		}
		if (!type.equals("B") && !type.equals("N") && !type.equals("R") & !type.equals("Q")) {
			return promoted;
		}
		Position pos = promoted.getChessPosition().toPosition();
		Piece p = board.removePiece(pos);
		piecesOnTheBoard.remove(p);
		
		ChessPiece newPiece = newPiece(type, promoted.getColor());
		board.placePiece(newPiece, pos);
		piecesOnTheBoard.add(newPiece);
		
		return newPiece;
		
	}
	
		private ChessPiece newPiece(String type, Color color) {
			if(type.equals("B")) return new Bishop(board, color);
			if(type.equals("N")) return new Knight(board, color);
			if(type.equals("Q")) return new Queen(board, color);
			return new Rook(board, color);
		}
		
		
	private Piece makeMove(Position source, Position target) {					// METODO MOVER A PEÇA - COMER
		ChessPiece p = (ChessPiece)board.removePiece(source);					// RETIRA A PEÇA DA POSIÇÃO DE ORIGEM
		p.increaseMoveCount();													// AUMENTA 1 NA CONTAGEM DE JOGADA
		Piece capturedPiece = board.removePiece(target);						// ESSA VAI SER A PEÇA CAPTURADA NA POSIÇÃO DE DESTINO
		board.placePiece(p, target); 											// COLOCA A PEÇA P NA POSIÇÃO DE DESTINO
		if (capturedPiece != null) {											// SE A PEÇA CAPTURADA FOR DIFERENTE DE NULO (CAPTURAR DE FATO)
			piecesOnTheBoard.remove(capturedPiece);								// 1- REMOVE DA LISTA DE PEÇAS NO TABULEIRO
			capturedPieces.add(capturedPiece);									// 2- ADCIONA A LISTA DE PEÇAS CAPTURADAS
		}
		
		//# Specialmove castling kingside rook
		if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3); // A POSIÇÃO DE ORIGEM DA TORRE ERA COLUNA +3 DO REI
			Position targetT = new Position(source.getRow(), source.getColumn() + 1); // E A DE DESTINO É A DE DESTINO +1 DO REI	
			ChessPiece rook = (ChessPiece)board.removePiece(sourceT);				  // REMOVE A TORRE DA POSIÇÃO DE ORIGEM
			board.placePiece(rook, targetT);										  // E COLOCA NA POSIÇÃO DE DESTINO
			rook.increaseMoveCount();												  // DA UM PONTO DE MOVIMENTO PARA A TORRE
		}
		//# Specialmove castling queenside rook
		if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() - 4); // A POSIÇÃO DE ORIGEM DA TORRE ERA COLUNA -4 DO REI
			Position targetT = new Position(source.getRow(), source.getColumn() - 1); // E A DE DESTINO É A DE DESTINO -1 DO REI	
			ChessPiece rook = (ChessPiece)board.removePiece(sourceT);				  // REMOVE A TORRE DA POSIÇÃO DE ORIGEM
			board.placePiece(rook, targetT);										  // E COLOCA NA POSIÇÃO DE DESTINO
			rook.increaseMoveCount();												  // DA UM PONTO DE MOVIMENTO PARA A TORRE
		}
		
		//#Specialmove en passant
		if(p instanceof Pawn) {
			if (source.getColumn() != target.getColumn() && capturedPiece == null) {   // SE A COLUNA DE ORIGEM DE PEÃO MUDOU E NÃO FOI CAPTURADA PEÇA, É QUE HOUVE EN PASSANT
				Position pawnPosition;
				if(p.getColor() == Color.WHITE) {									     // SE A PEÇA FOR BRANCA
					pawnPosition = new Position(target.getRow()+ 1, target.getColumn()); // PEGA A PEÇA A DIREITA
				} 
				else {
					pawnPosition = new Position(target.getRow()- 1, target.getColumn()); // PEGA A PEÇA A ESQUERDA
				}
				capturedPiece = board.removePiece(pawnPosition);								// CAPTURA A PEÇA DAQEUAL POSIÇÃO
				capturedPieces.add(capturedPiece);										// ADCIONA A LISTA DE PEÇAS CAPTURADAS
				piecesOnTheBoard.remove(capturedPiece);                                 // REMOVE DA LISTA DE PEÇAS DO TABULEIRO
			}
		}
		
		return capturedPiece; 													// RETORNA A PEÇA CAPTURADA
		
	}
	
	private void undoMove(Position source, Position target, Piece capturedPiece) { // ESSE METODO DESFAZ A JOGADA
		ChessPiece p = (ChessPiece)board.removePiece(target);					   // RETIRA A PEÇA DA POSIÇÃO DE DESTINO						
		p.decreaseMoveCount();													   // REDUZ 1 DA CONTAGEM DE JOGADA		
		board.placePiece(p, source);											   // VOLTA A PEÇA P PARA A POSIÇÃO DE ORIGEM
		if (capturedPiece != null) {											   // SE A PEÇA CAPTURADA FOR DIFERENTE DE NULO (CAPTURAR DE FATO)
				board.placePiece(capturedPiece, target);							
				capturedPieces.remove(capturedPiece);							   // 1- REMOVE DA LISTA DE PEÇAS CAPTURADAS
				piecesOnTheBoard.add(capturedPiece);							   // 2- INCLUI NOVAMENTE NA LISTA DE PEÇAS NO TABULEIRO
			}
		//# Specialmove castling kingside rook
				if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
					Position sourceT = new Position(source.getRow(), source.getColumn() + 3); // A POSIÇÃO DE ORIGEM DA TORRE ERA COLUNA +3 DO REI
					Position targetT = new Position(source.getRow(), source.getColumn() + 1); // E A DE DESTINO É A DE DESTINO +1 DO REI	
					ChessPiece rook = (ChessPiece)board.removePiece(targetT);				  // REMOVE A TORRE DA POSIÇÃO DE ORIGEM
					board.placePiece(rook, sourceT);										  // E COLOCA NA POSIÇÃO DE DESTINO
					rook.decreaseMoveCount();												  // DA UM PONTO DE MOVIMENTO PARA A TORRE
				}
				//# Specialmove castling queenside rook
				if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
					Position sourceT = new Position(source.getRow(), source.getColumn() - 4); // A POSIÇÃO DE ORIGEM DA TORRE ERA COLUNA -4 DO REI
					Position targetT = new Position(source.getRow(), source.getColumn() - 1); // E A DE DESTINO É A DE DESTINO -1 DO REI	
					ChessPiece rook = (ChessPiece)board.removePiece(targetT);				  // REMOVE A TORRE DA POSIÇÃO DE ORIGEM
					board.placePiece(rook, sourceT);										  // E COLOCA NA POSIÇÃO DE DESTINO
					rook.decreaseMoveCount();												  // DA UM PONTO DE MOVIMENTO PARA A TORRE
				}
				//#Specialmove en passant
				if(p instanceof Pawn) {
					if (source.getColumn() != target.getColumn() && capturedPiece == enPassantVunerable) {  
						ChessPiece pawn = (ChessPiece)board.removePiece(target);
						Position pawnPosition;
						if(p.getColor() == Color.WHITE) {									     // SE A PEÇA FOR BRANCA
							pawnPosition = new Position(3, target.getColumn()); // PEGA A PEÇA A DIREITA
						} 
						else {
							pawnPosition = new Position(4, target.getColumn()); // PEGA A PEÇA A ESQUERDA
						}
						board.placePiece(pawn, pawnPosition);

					}
				}
				
		}
	
	private void validateSourcePosition (Position position) {
		if(!board.thereIsAPiece(position)) {
			throw new ChessException("There is no piece on source position");
		}
		if(currentPlayer != ((ChessPiece)board.piece(position)).getColor()) {	// SE O JOGADOR CORRENTE TENTAR MOVER PEÇA ADVERSARIA TRARÁ A EXCEÇÃO, USOU O DOWNCAST 
			throw new ChessException("The chosen piece is not yours");
		}
		if(!board.piece(position).isThereAnyPossibleMove()) { 					// VERIFICA SE HÁ MOVIMENTOS POSSIVEIS
			throw new ChessException("There is no possible moves for the chosen piece");
		}
	}
	
	private void validateTargetPosition(Position source, Position target) { 	// VALIDA SE A POSIÇÃO DE DESTINO É VALIDA DE ACORDO COM A POSSIBILIDADE DA POSIÇÃO DE PARTIDA
		if(!board.piece(source).possibleMove(target)) {
			throw new ChessException("The chosen piece can't move to target position"); // CASO NÃO, ACONTECE ESSA EXCEÇÃO
		}
	}
	
	private void nextTurn() {
		turn++;																	 // TURNO VAI AUMENTANDO
		currentPlayer=(currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;// CONDIÇÃO TERNARIA, SE FOR BRANCO VIRA PRETO CASO CONTRARIO VIRA BRANCO
	}
	
	private Color opponnent(Color color) {										// METODO DEVOLVE O OPONENTE DE UMA COR
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;				// CONDIÇÃO TERNARIA, SE FOR BRANCO VIRA PRETO CASO CONTRARIO VIRA BRANCO
	}
	
	private ChessPiece king (Color color) {										// METODO LOCALIZA O REI DE CADA COR 
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList()); // FILTRA A LISTA PROCURANDO TODA PEÇA DE DETERMINADA COR, E COLETA, HOUVE DOWNCAST POIS "LIST" NÃO TEM COR
		for (Piece p: list) {													// VARRE A LISTA PIECE
			if (p instanceof King) {											// SE ESSA PEÇA P É UMA INSTANCIA DE REI, SIGINIFICA QUE É UM REI
				return (ChessPiece)p;											// RETORNA A PEÇA P, POREM TEM QUE FAZER UM DOWNCAST POIS É UM CHEEPIECE
			}
		}
		throw new IllegalStateException("There is no " + color + " king on the board"); // SE NÃO ENCONTRAR NENHUM REI, RETORNA UMA EXCEÇÃO
	}
	
	private boolean testCheck(Color color) {									// METODO TESTA SE O REI DESTA COR ESTÁ EM CHECK
		Position kingPosition = king(color).getChessPosition().toPosition();	// 1 - PEGA A POSIÇÃO DO REI NO FORMATO DE MATRIZ
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponnent(color)).collect(Collectors.toList()); // ESSA LISTA SERÁ AS PEÇAS NO TABULEIRO FILTRADAS COM A COR DO OPONENT
		for (Piece p: opponentPieces) {											// VARRE A LIST OPPPNENT PIECES // PARA CADA PEÇA P NA LISTA DE OPONENTES
			boolean[][] mat = p.possibleMoves();								// ESSA É A MATRIZ DE MOVIMENTOS POSSIVEIS
			if (mat[kingPosition.getRow()][kingPosition.getColumn()]){			// SE NESTA MATRIZ A POSIÇÃO DE MOVIMENTOS P, FOR VERDADEIRO SIGNIFICA QUE O REI ESTÁ EM CHECK
				return true;													// SE VERDADEIRO RETORNA TRUE
			}
		} return false;
	}
	
	private boolean testCheckMate(Color color) {
		if (!testCheck(color)) {												// SE ESSAC COR NÃO ESTÁ EM CHECK NÃO ESTÁ EM CHECKMATE
			return false;
		}
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList()); // PEGA UMA LISTA COM TODAS AS PEÇAS COM A COR EM QUESTÃO
		for(Piece p: list) {													// PERCORRE ALISTA PROCURANDO ALGUMA PEÇA QUE TIRA DO CHECKMATE
			boolean[][] mat = p.possibleMoves();								// PEGA A MATRIZ COM OS MOVIMENTOS POSSIVEIS
				for(int i=0; i<board.getRows(); i++) {							// O FOR VAI PERCORRER AS LINHAS 
					for (int j=0; j<board.getColumns(); j++) {					// O FOR VAI PERCORRER AS COLUNAS 
						if (mat[i][j]) {										// SE A POSIÇÃO DA MATRIZ É UM MOVIMENTO POSSIVEL
							Position source = ((ChessPiece)p).getChessPosition().toPosition(); // PEGA P NA POSIÇÃO DE ORIGEM COM O DOWNCAST POIS NÃO PODE PEGAR O POSITION POIS É PROTECTED
							Position target = new Position(i, j);				// PEGA O P NA POSIÇÃO DE DESTINO
							Piece capturedPiece = makeMove(source, target);		// CHAMA O METODO QUE FAZ O MOVIMENTO
							boolean testCheck = testCheck(color);				// APOS O MOVIMENTO O TESTCHECK, CONFIRMA SE O REI DA COR EM QUESTÃO ESTÁ EM CHECK.
							undoMove(source, target, capturedPiece); 			// EM SEGUIDA CHAMAMOS UNDOMOVE PRA DESFAZER A JOGADA POIS ERA APENAS UM TEST
							if(!testCheck) {									// TESTA SE O MOVIMENTO TIROU O REI DO CHECK OU NÃO.
								return false;
							}
							
						}
					}
				}
		} return true;
	}
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition()); 	// ESSE METODO TRANSFORMA A FORMA DE LANÇAR O VALOR PRA MODO XADREX
		piecesOnTheBoard.add(piece);											// ADCIONA NA LISTA DE PEÇAS NO TABUEIRO
	}
	
	private void initialSetup() { 												// METODO RESPONSAVEL POR INICIAR AS POSIÇÕES DAS PEÇAS NO TABULEIRO // 
																				// ESTA USANDO O METODO CRIADO ACIMA 'PLACENEWPIECE"
		placeNewPiece('a', 1, new Rook(board, Color.WHITE));		
		placeNewPiece('c', 1, new Bishop(board, Color.WHITE));		
		placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
		placeNewPiece('b', 1, new Knight(board, Color.WHITE));
		placeNewPiece('g', 1, new Knight(board, Color.WHITE));
		placeNewPiece('d', 1, new Queen(board, Color.WHITE));
		placeNewPiece('e', 1, new King(board, Color.WHITE, this));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
    	placeNewPiece('a', 2, new Pawn(board, Color.WHITE,this));		
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE,this));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE,this));
    	placeNewPiece('d', 2, new Pawn(board, Color.WHITE,this));		
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE,this));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE,this));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE,this));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE,this));
       

        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));		
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
		placeNewPiece('b', 8, new Knight(board, Color.BLACK));
		placeNewPiece('g', 8, new Knight(board, Color.BLACK));
		placeNewPiece('d', 8, new Queen(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK, this));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK,this));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK,this));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK,this));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK,this));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK,this));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK,this));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK,this));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK,this));
       
	}
}
