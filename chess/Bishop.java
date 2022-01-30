package chess;

import static chess.PieceType.*;

public class Bishop implements Piece {

    public Bishop(PieceColor color, Game game, int x, int y) {
        _color = color;
        _game = game;
        _x = x;
        _y = y;
    }

    @Override
    public String imageString() {
        return _color.abbrev() + BISHOP.abbrev();
    }

    @Override
    public PieceColor color() {
        return _color;
    }

    @Override
    public PieceType type() {
        return BISHOP;
    }

    @Override
    public boolean makeValidMove(int a, int b) {
        if (_game.get(a, b) != null
            && _game.get(a, b).color() == _color) {
            return false;
        } else if (a + b == _x + _y) {
            int dir = (a - _x) / Math.abs(a - _x);
            for (int i = _x + dir, j = _y - dir; i != a; i += dir, j -= dir) {
                if (_game.get(i, j) != null) {
                    return false;
                }
            }
            Move move = new SingleMove(this, _x, _y, _game.get(a, b), a, b);
            return makeMoveCareful(move);
        } else if (a - b == _x - _y) {
            int dir = (a - _x) / Math.abs(a - _x);
            for (int i = _x + dir, j = _y + dir; i != a; i += dir, j += dir) {
                if (_game.get(i, j) != null) {
                    return false;
                }
            }
            Move move = new SingleMove(this, _x, _y, _game.get(a, b), a, b);
            return makeMoveCareful(move);
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
        if ((_x + 1 <= 7 && _y + 1 <= 7 && makeValidMove(_x + 1, _y + 1))
            || (_x + 1 <= 7 && _y - 1 >= 0 && makeValidMove(_x + 1, _y - 1))
            || (_x - 1 >= 0 && _y + 1 <= 7 && makeValidMove(_x - 1, _y + 1))
            || (_x - 1 >= 0 && _y - 1 >= 0 && makeValidMove(_x - 1, _y - 1))) {
            _game.undoMove();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean canCapture(int a, int b) {
        if (a + b == _x + _y) {
            int dir = (a - _x) / Math.abs(a - _x);
            for (int i = _x + dir, j = _y - dir; i != a; i += dir, j -= dir) {
                if (_game.get(i, j) != null) {
                    return false;
                }
            }
            return true;
        } else if (a - b == _x - _y) {
            int dir = (a - _x) / Math.abs(a - _x);
            for (int i = _x + dir, j = _y + dir; i != a; i += dir, j += dir) {
                if (_game.get(i, j) != null) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
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

    private Game _game;

    private PieceColor _color;

    private int _x;

    private int _y;

}
