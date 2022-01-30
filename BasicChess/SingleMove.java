package BasicChess;

public class SingleMove implements Move {

    private Piece _selected;
    private Piece _target;
    private Piece _replace;

    private int _x1;
    private int _y1;
    private int _x2;
    private int _y2;
    
    public SingleMove(Piece s, int x1, int y1, Piece t, int x2, int y2) {
        _selected = s;
        _x1 = x1;
        _y1 = y1;
        _target = t;
        _x2 = x2;
        _y2 = y2;
        _replace = null;
    }

    private SingleMove(Piece s, int x1, int y1, Piece t,
        int x2, int y2, Piece r) {
            
        _selected = s;
        _x1 = x1;
        _y1 = y1;
        _target = t;
        _x2 = x2;
        _y2 = y2;
        _replace = r;
    }

    @Override
    public boolean isDouble() {
        return false;
    }

    @Override
    public Move undoMove() {
        return new SingleMove(_selected, _x2, _y2, null, _x1, _y1, _target);
    }

    @Override
    public Piece movedPiece() {
        return _selected;
    }

    public Piece selected() {
        return _selected;
    }

    public Piece target() {
        return _target;
    }

    public Piece replace() {
        return _replace;
    }

    public int x1() {
        return _x1;
    }

    public int y1() {
        return _y1;
    }

    public int x2() {
        return _x2;
    }

    public int y2() {
        return _y2;
    }


}
