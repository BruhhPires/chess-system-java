package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner (System.in);
		ChessMatch chessMatch = new ChessMatch();
		List<ChessPiece> captured = new ArrayList<>();
		
		while (!chessMatch.getCheckMate()) {									// ENQUANTO NÃO ESTIVER COM CHECKMATE, QUANDO ACONTECE O CHECKMATE VAI PRO FINAL  E UI IMPRIMI A TELA
			try {
				UI.clearScreen();												// LIMPA O HISTORICO DO PROMPT
				UI.printMatch(chessMatch, captured);							// IMPRIME O TABULEIRO
				System.out.println();
				System.out.print("Source: ");
				ChessPosition source = UI.readChessPosition(sc);

				boolean[][] possibleMoves = chessMatch.possibleMove(source);	
				UI.clearScreen();												// LIMPA A TELA E ENVIA ELA COM FUNDO COLORIDO NAS POSIÇÕES POSSIVEIS
				UI.printBoard(chessMatch.getPieces(), possibleMoves); 			// IMPRIMI OS MOVIMENTOS POSSIVEIS
				System.out.println();
				System.out.print("Target: ");
				ChessPosition target = UI.readChessPosition(sc);
				
				ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
				
				if (capturedPiece != null){ 									// SEMPRE QUE O METODO CAPTUREDPIECE FOR DIFERENTE DE NULLO,
					captured.add(capturedPiece);								// SERÁ ADICIONADO A PEÇA A LISTA DE PEÇAS CAPTURADAS.
				}

			}
			catch (ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		UI.clearScreen();
		UI.printMatch(chessMatch, captured);
	}
}