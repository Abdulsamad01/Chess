package chess;


public interface Move {

    boolean isDouble();
    Move undoMove();
    Piece movedPiece();

}
