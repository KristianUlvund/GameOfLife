package application.controller;


import application.model.DynamicBoardModel;
import javafx.scene.control.Label;

import java.util.ArrayList;

/**
 * Created by Aleksander and Kristian
 *
 * This class is the controller for the statistics for the main board on game canvas.
 */
public class StatisticController {

    /**
     * This method is called whenever a number of true cells on the board is to be counted.
     *
     * This method takes the size of a board and count every true cells on that board.
     *
     * @param statBoard is the board that this method is counting true cells on.
     *
     * @return number for true cells.
     */
    public int numberOfAliveCells(ArrayList<ArrayList<Boolean>> statBoard)
    {
        int numAliveCell = 0;
        for (int x = 0; x <statBoard.size() ; x++) {
            for (int y = 0; y <statBoard.get(0).size() ; y++) {
                if(statBoard.get(x).get(y) == true)
                {
                    numAliveCell++;
                }
            }
        }
        return numAliveCell;
    }

    /**
     * This method is called whenever a number of false cells on the board is to be counted.
     *
     * This method takes the size of a board and count every false cells on that board.
     *
     * @param statBoard is the board that this method is counting false cells on.
     *
     * @return number for false cells.
     */
    public int numberOfDeadCells(ArrayList<ArrayList<Boolean>> statBoard)
    {
        int numDeadCell = 0;
        for (int x = 0; x <statBoard.size() ; x++) {
            for (int y = 0; y <statBoard.get(0).size() ; y++) {
                if(statBoard.get(x).get(y) == false)
                {
                    numDeadCell++;
                }
            }
        }
        return numDeadCell;
    }


    /**
     * This is called whenever the reset of the countGeneration variable from the
     * dynamicalBoardModel object in the viewController needs to be reset.
     *
     * This method resets the countGeneration variable from the DynamicalBoardModel
     * object for viewController.
     *
     * @param numGen is the DynamicalBoardModel object in the viewController.
     *
     * @return Returns the new numGen value
     */
    public DynamicBoardModel resetGeneration(DynamicBoardModel numGen)
    {
        numGen.setCountGeneration(0);
        return numGen;
    }

    /**
     * This method is called whenever draw need to initiate stats.
     *
     * This method sets the text for the tabPane for stats.
     *
     * @param aliveCell Is the label for alive cells in tabPane for stats.
     * @param deadCell Is the label for dead cells in tabPane for stats.
     * @param countGeneration Is the label for number of generation in tabPane for stats.
     * @param board is the board that is needed for the specific functions.
     * @param numGeneration is number of generation on the board.
     */
    public void initStats(Label aliveCell, Label deadCell, Label countGeneration, ArrayList<ArrayList<Boolean>> board, int numGeneration)
    {
        aliveCell.setText(Integer.toString(numberOfAliveCells(board)));
        deadCell.setText(Integer.toString(numberOfDeadCells(board)));
        countGeneration.setText(Integer.toString(numGeneration));
    }
}

