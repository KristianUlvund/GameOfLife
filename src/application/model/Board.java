package application.model;

/**
 * Created by Kristian
 *
 * This is an abstract class wich static and dynamic board extends from.
 *
 */
public abstract class Board {

    abstract void setBoard(int boardSize);
    abstract int getBoardYLength();
    abstract int getBoardXLength();
    abstract void setCellStateTrue(int x, int y);
    abstract void setCellStateFalse(int x, int y);
    abstract void setCellStatesFalse(int x, int y);
    abstract void setSquare(int x, int y);
    abstract void setGlider(int x, int y);
    abstract void setPulsar(int x, int y);
    abstract void setSpaceship(int x, int y);
    abstract void setGunGlider(int x, int y);
    abstract boolean getCellState(int x, int y);
    abstract void initRandomBoard(double density);
    abstract void initNewBoard();
    abstract void nextGeneration();
}