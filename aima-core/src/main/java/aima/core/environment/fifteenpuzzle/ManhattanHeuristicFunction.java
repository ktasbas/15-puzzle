package aima.core.environment.fifteenpuzzle;

import aima.core.search.framework.evalfunc.HeuristicFunction;
import aima.core.util.datastructure.XYLocation;

/**
 * @author Ravi Mohan
 * @author M. Kaan Tasbas
 * 
 */
public class ManhattanHeuristicFunction implements HeuristicFunction {

	public double h(Object state) {
		FifteenPuzzleBoard board = (FifteenPuzzleBoard) state;
		int retVal = 0;
		// loop through board to get each distance
		for (int i = 1; i < 16; i++) {
			XYLocation loc = board.getLocationOf(i);
			// keep running total
			retVal += evaluateManhattanDistanceOf(i, loc);
		}
		return retVal;

	}

	/**
	 * Calculates the manhattan distance for the selected tile from its current position
	 * 
	 * @param int i, value of tile
	 * @param XYLocation loc, xy location of tile on board
	 * @return int (manhattan distance if valid tile value, else -1)
	 */
	public int evaluateManhattanDistanceOf(int i, XYLocation loc) {
		int retVal = -1;
		int xpos = loc.getXCoOrdinate();
		int ypos = loc.getYCoOrdinate();

		//	0  1  2  3
		//	4  5  6  7
		//	8  9  10 11
		//  12 13 14 15

		switch (i) {

		case 1:
			retVal = Math.abs(xpos - 0) + Math.abs(ypos - 1);
			break;
		case 2:
			retVal = Math.abs(xpos - 0) + Math.abs(ypos - 2);
			break;
		case 3:
			retVal = Math.abs(xpos - 0) + Math.abs(ypos - 3);
			break;
		case 4:
			retVal = Math.abs(xpos - 1) + Math.abs(ypos - 0);
			break;
		case 5:
			retVal = Math.abs(xpos - 1) + Math.abs(ypos - 1);
			break;
		case 6:
			retVal = Math.abs(xpos - 1) + Math.abs(ypos - 2);
			break;
		case 7:
			retVal = Math.abs(xpos - 1) + Math.abs(ypos - 3);
			break;
		case 8:
			retVal = Math.abs(xpos - 2) + Math.abs(ypos - 0);
			break;
		case 9:
			retVal = Math.abs(xpos - 2) + Math.abs(ypos - 1);
			break;
		case 10:
			retVal = Math.abs(xpos - 2) + Math.abs(ypos - 2);
			break;
		case 11:
			retVal = Math.abs(xpos - 2) + Math.abs(ypos - 3);
			break;
		case 12:
			retVal = Math.abs(xpos - 3) + Math.abs(ypos - 0);
			break;
		case 13:
			retVal = Math.abs(xpos - 3) + Math.abs(ypos - 1);
			break;
		case 14:
			retVal = Math.abs(xpos - 3) + Math.abs(ypos - 2);
			break;
		case 15:
			retVal = Math.abs(xpos - 3) + Math.abs(ypos - 3);
			break;

		}
		return retVal;
	}
}