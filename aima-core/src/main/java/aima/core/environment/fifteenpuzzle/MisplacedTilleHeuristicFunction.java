package aima.core.environment.fifteenpuzzle;

import aima.core.search.framework.evalfunc.HeuristicFunction;
import aima.core.util.datastructure.XYLocation;

/**
 * @author Ravi Mohan
 * @author M. Kaan Tasbas
 * 
 */
public class MisplacedTilleHeuristicFunction implements HeuristicFunction {

	public double h(Object state) {
		FifteenPuzzleBoard board = (FifteenPuzzleBoard) state;
		return getNumberOfMisplacedTiles(board);
	}

	/**
	 * Calculates the total number of misplaced tiles on the board
	 * 
	 * @param FifteenPuzzleBoard board
	 * @return int numberOfMisplacedTiles
	 */
	private int getNumberOfMisplacedTiles(FifteenPuzzleBoard board) {
		int numberOfMisplacedTiles = 0;
		if (!(board.getLocationOf(0).equals(new XYLocation(0, 0)))) {
			numberOfMisplacedTiles++;
		}
		if (!(board.getLocationOf(1).equals(new XYLocation(0, 1)))) {
			numberOfMisplacedTiles++;
		}
		if (!(board.getLocationOf(2).equals(new XYLocation(0, 2)))) {
			numberOfMisplacedTiles++;
		}
		if (!(board.getLocationOf(3).equals(new XYLocation(0, 3)))) {
			numberOfMisplacedTiles++;
		}
		if (!(board.getLocationOf(4).equals(new XYLocation(1, 0)))) {
			numberOfMisplacedTiles++;
		}
		if (!(board.getLocationOf(5).equals(new XYLocation(1, 1)))) {
			numberOfMisplacedTiles++;
		}
		if (!(board.getLocationOf(6).equals(new XYLocation(1, 2)))) {
			numberOfMisplacedTiles++;
		}
		if (!(board.getLocationOf(7).equals(new XYLocation(1, 3)))) {
			numberOfMisplacedTiles++;
		}
		if (!(board.getLocationOf(8).equals(new XYLocation(2, 0)))) {
			numberOfMisplacedTiles++;
		}
		if (!(board.getLocationOf(9).equals(new XYLocation(2, 1)))) {
			numberOfMisplacedTiles++;
		}
		if (!(board.getLocationOf(10).equals(new XYLocation(2, 2)))) {
			numberOfMisplacedTiles++;
		}
		if (!(board.getLocationOf(11).equals(new XYLocation(2, 3)))) {
			numberOfMisplacedTiles++;
		}
		if (!(board.getLocationOf(12).equals(new XYLocation(3, 0)))) {
			numberOfMisplacedTiles++;
		}
		if (!(board.getLocationOf(13).equals(new XYLocation(3, 1)))) {
			numberOfMisplacedTiles++;
		}
		if (!(board.getLocationOf(14).equals(new XYLocation(3, 2)))) {
			numberOfMisplacedTiles++;
		}
		if (!(board.getLocationOf(15).equals(new XYLocation(3, 3)))) {
			numberOfMisplacedTiles++;
		}
		
		// Subtract the gap position from the # of misplaced tiles
		// as its not actually a tile (see issue 73).
		if (numberOfMisplacedTiles > 0) {
			numberOfMisplacedTiles--;
		}
		return numberOfMisplacedTiles;
	}
}