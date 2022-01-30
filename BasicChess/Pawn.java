package chess;

import static chess.PieceType.*;

public class Pawn implements Piece {

    
    private Game _game;
    private PieceColor _color;
    private int _x;
    private int _y;
    
    public Pawn(PieceColor color, Game game, int x, int y) {
        _color = color;
        _game = game;
        _x = x;
        _y = y;
    }

    @Override
    public String imageString() {
        return _color.abbrev() + PAWN.abbrev();
    }

    @Override
    public PieceColor color() {
        return _color;
    }

    @Override
    public PieceType type() {
        return PAWN;
    }

    @Override
    public boolean makeValidMove(int a, int b) {
        if (_y == start()) {
            if (b == _y + 2 * direction()) {
                if (a == _x && _game.get(a, _y + direction()) == null
                    && _game.get(a, b) == null) {
                    Move move = new SingleMove(this, _x, _y,
                        _game.get(a, b), a, b);
                    return makeMoveCareful(move);
                } else {
                    return false;
                }
            }
        }
        if (b == _y + direction()) {
            if (a == _x && _game.get(a, b) == null) {
                if (b == start() + 6 * direction()) {
                    SingleMove move1 = new SingleMove(this, _x, _y,
                        _game.get(a, b), a, b);
                    Piece newQu = new Queen(_color, _game, a, b);
                    SingleMove move2 = new SingleMove(newQu, a, b, this, a, b);
                    DoubleMove move = new DoubleMove(move1, move2);
                    return makeMoveCareful(move);
                } else {
                    Move move = new SingleMove(this, _x, _y,
                        _game.get(a, b), a, b);
                    return makeMoveCareful(move);
                }
            } else if (Math.abs(a - _x) == 1 && _game.get(a, b) != null
                && _game.get(a, b).color() != _color) {
                if (b == start() + 6 * direction()) {
                    SingleMove move1 = new SingleMove(this, _x, _y,
                        _game.get(a, b), a, b);
                    Piece newQueen = new Queen(_color, _game, a, b);
                    SingleMove move2 = new SingleMove(newQueen, a, b,
                        this, a, b);
                    DoubleMove move = new DoubleMove(move1, move2);
                    return makeMoveCareful(move);
                } else {
                    Move move = new SingleMove(this, _x, _y,
                        _game.get(a, b), a, b);
                    return makeMoveCareful(move);
                }
            } else if (Math.abs(a - _x) == 1 && _game.get(a, b) == null
                && _y == start() + 3 * direction() && _game.get(a, _y) != null
                && _game.get(a, _y).color() != _color
                && _game.get(a, _y).type() == PAWN
                && _game.get(a, _y) == _game.lastMover()) {
                SingleMove move1 = new SingleMove(this, _x, _y,
                    _game.get(a, b), a, b);
                SingleMove move2 = new SingleMove(null, _x, _y,
                    _game.get(a, b - direction()), a, b - direction());
                DoubleMove move = new DoubleMove(move1, move2);
                return makeMoveCareful(move);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public void setLocation(int x, int y) {
        _x = x;
        _y = y;
    }

    @Override
    public boolean hasMove() {
        if ((_x + 1 <= 7 && makeValidMove(_x + 1, _y + 1))
            || (makeValidMove(_x, _y + 1))
            || (_x - 1 >= 0 && makeValidMove(_x - 1, _y + 1))) {
            _game.undoMove();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean canCapture(int a, int b) {
        return (b == _y + direction() && Math.abs(a - _x) == 1);
    }

    private boolean makeMoveCareful(Move move) {
        _game.makeMove(move);
        if (_game.inCheck(_game.turn().opposite())) {
            _game.undoMove();
            return false;
        } else {
            return true;
        }
    }

    private int direction() {
        return _color.direction();
    }

    private int start() {
        if (_color == PieceColor.WHITE) {
            return 6;
        } else {
            return 1;
        }
    }


}
