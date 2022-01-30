package chess;

public interface Piece {

    String imageString();
    PieceColor color();
    PieceType type();

    boolean makeValidMove(int a, int b);
    boolean hasMove();
    boolean canCapture(int a, int b);
    void setLocation(int x, int y);

}
