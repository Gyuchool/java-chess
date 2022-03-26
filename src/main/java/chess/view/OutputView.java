package chess.view;

import chess.piece.Piece;
import chess.piece.Pieces;

import java.util.Collections;

public class OutputView {

    public static void startGame() {
        System.out.println("> 체스 게임을 시작합니다.\n" +
                "> 게임 시작 : start\n" +
                "> 게임 종료 : end\n" +
                "> 게임 이동 : move source위치 target위치 - 예. move b2 b3");
    }

    public static void printBoard(Pieces board) {
        Collections.sort(board.getPieces());
        for (Piece piece : board.getPieces()) {
            System.out.print(piece.getName());
            makeNewLine(piece);
        }
    }

    private static void makeNewLine(Piece piece) {
        if (piece.isLastFile()) {
            System.out.println();
        }
    }
}
