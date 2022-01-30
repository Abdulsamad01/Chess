package BasicChess;

import java.awt.event.MouseEvent;

import static chess.GameDisplay.*;


class ChessGUI extends TopLevel {

    ChessGUI(String title, Game game) {
        super(title, true);
        _game = game;
        addLabel("Welcome to 2-Player Chess. "
            + "Click a piece and then its destination to play! "
            + "WHITE's turn.", "turn",
            new LayoutSpec("y", 0, "x", 0));
        addMenuButton("Options->Quit", "quit");
        addMenuButton("Options->Undo", "undo");
        addMenuButton("Options->New Game", "newGame");
        _display = new GameDisplay(game);
        add(_display, new LayoutSpec("y", 2, "width", 2));
        _display.setMouseHandler("press", this, "mousePressed");
        display(true);
    }

    public void newGame(String dummy) {
        _game.newGame();
        repaint(true);
    }

    public void quit(String dummy) {
        _game.quit();
    }

    public void undo(String dummy) {
        _game.undoMove();
        _game.setSelectedX(-1);
        _game.setSelectedY(-1);
        repaint(true);
    }

    public synchronized void mousePressed(MouseEvent event) {
        if (_game.selectedX() == -1) {
            int pressedX = (event.getX() - MARGIN) / CELL;
            int pressedY = (event.getY() - MARGIN) / CELL;
            Piece selected = _game.get(pressedX, pressedY);
            if (selected != null && selected.color() == _game.turn()) {
                _game.setSelectedX(pressedX);
                _game.setSelectedY(pressedY);
                _display.repaint();
            }
        } else {
            int releasedX = (event.getX() - MARGIN) / CELL;
            int releasedY = (event.getY() - MARGIN) / CELL;
            Piece selected = _game.get(_game.selectedX(), _game.selectedY());
            _game.setSelectedX(-1);
            _game.setSelectedY(-1);
            repaint(selected.makeValidMove(releasedX, releasedY));
        }
    }

    public void repaint(boolean validMove) {
        String label;
        if (validMove) {
            if (_game.noMoves()) {
                if (_game.inCheck(_game.turn())) {
                    label = "CHECKMATE, " + _game.turn().opposite().string()
                        + " wins.";
                } else {
                    label = "STALEMATE, game ends in draw.";
                }
            } else {
                label = _game.turn().string() + "'s turn.";
            }
        } else {
            label = "Invalid Move. " + _game.turn().string() + "'s turn.";
        }
        setLabel("turn", label);
        _display.repaint();
    }

    private final GameDisplay _display;

    private final Game _game;

}
