package aima.core.environment.fifteenpuzzle;

import java.util.ArrayList;
import java.util.List;

import aima.core.agent.Action;
import aima.core.agent.impl.DynamicAction;
import aima.core.util.datastructure.XYLocation;

/**
 * @author Ravi Mohan
 * @author R. Lunde
 * @author M. Kaan Tasbas
 */
public class FifteenPuzzleBoard {

	public static Action LEFT = new DynamicAction("Left");

	public static Action RIGHT = new DynamicAction("Right");

	public static Action UP = new DynamicAction("Up");

	public static Action DOWN = new DynamicAction("Down");

	private int[] state;

	//
	// PUBLIC METHODS
	//

	public FifteenPuzzleBoard() {
		state = new int[] { 15, 2, 1, 12, 8, 5, 6, 11, 4, 9, 10, 7, 3, 14, 13, 0 };
	}

	public FifteenPuzzleBoard(int[] state) {
		this.state = new int[state.length];
		System.arraycopy(state, 0, this.state, 0, state.length);
	}

	public FifteenPuzzleBoard(FifteenPuzzleBoard copyBoard) {
		this(copyBoard.getState());
	}

	/**
	 * Checks whether goal board is reachable from the current board by comparing the number of inversions
	 * in each board. Both boards must have the similar (# of inversions % 2) values to be reachable
	 * @param FifteenPuzzleBoard goalBoard
	 * @return true if goal is reachable from this.FifteenPuzzleBoard
	 */
	public boolean GoalReachable(FifteenPuzzleBoard goalBoard) {
		int thisInversions = 0;
		int goalBoardInversions = 0;

		// calculate number of inversions
		for(int i = 0; i < 16; i++) {
			// outter for loop traverses board
			// get values of current tile for initial and goal boards
			int thisCurrentTile = this.state[i];
			//int thisCurrentTile = this.getValueAt(this.getLocationOf(i));
			int goalBoardCurrentTile = goalBoard.state[i];
			for(int pos = i + 1; pos < 16; pos++) {
				// innner for loop traverses remaining tiles after current
				// get values of remaining tiles
				int thisRemainingTile = this.state[pos];
				int goalBoardRemainingTile = goalBoard.state[pos];
				// keep running total
				// don't add if comparing to empty tile
				if(thisRemainingTile != 0 && thisCurrentTile > thisRemainingTile) {
					thisInversions++;
				}
				if(goalBoardRemainingTile != 0 && goalBoardCurrentTile > goalBoardRemainingTile) {
					goalBoardInversions++;
				}
			}
		}
		// add row number (x coordinate) of empty tile
		// +1 because x coord is returned [0,3]
		thisInversions += this.getXCoord(this.getGapPosition()) + 1;
		goalBoardInversions += goalBoard.getXCoord(goalBoard.getGapPosition()) + 1;

		// calculate N mod 2 value
		thisInversions = thisInversions % 2;
		goalBoardInversions = goalBoardInversions % 2;

		// compare number of inversions
		if(thisInversions == goalBoardInversions) {
			return true;
		}
		return false;
	}

	public int[] getState() {
		return state;
	}

	public int getValueAt(XYLocation loc) {
		return getValueAt(loc.getXCoOrdinate(), loc.getYCoOrdinate());
	}

	public XYLocation getLocationOf(int val) {
		int absPos = getPositionOf(val);
		return new XYLocation(getXCoord(absPos), getYCoord(absPos));
	}

	public void moveGapRight() {
		int gapPos = getGapPosition();
		int x = getXCoord(gapPos);
		int ypos = getYCoord(gapPos);
		if (!(ypos == 3)) {
			int valueOnRight = getValueAt(x, ypos + 1);
			setValue(x, ypos, valueOnRight);
			setValue(x, ypos + 1, 0);
		}

	}

	public void moveGapLeft() {
		int gapPos = getGapPosition();
		int x = getXCoord(gapPos);
		int ypos = getYCoord(gapPos);
		if (!(ypos == 0)) {
			int valueOnLeft = getValueAt(x, ypos - 1);
			setValue(x, ypos, valueOnLeft);
			setValue(x, ypos - 1, 0);
		}

	}

	public void moveGapDown() {
		int gapPos = getGapPosition();
		int x = getXCoord(gapPos);
		int y = getYCoord(gapPos);
		if (!(x == 3)) {
			int valueOnBottom = getValueAt(x + 1, y);
			setValue(x, y, valueOnBottom);
			setValue(x + 1, y, 0);
		}

	}

	public void moveGapUp() {
		int gapPos = getGapPosition();
		int x = getXCoord(gapPos);
		int y = getYCoord(gapPos);
		if (!(x == 0)) {
			int valueOnTop = getValueAt(x - 1, y);
			setValue(x, y, valueOnTop);
			setValue(x - 1, y, 0);
		}
	}

	public List<XYLocation> getPositions() {
		ArrayList<XYLocation> retVal = new ArrayList<XYLocation>();
		for (int i = 0; i < 16; i++) {
			int absPos = getPositionOf(i);
			XYLocation loc = new XYLocation(getXCoord(absPos),
					getYCoord(absPos));
			retVal.add(loc);

		}
		return retVal;
	}

	public void setBoard(List<XYLocation> locs) {
		int count = 0;
		for (int i = 0; i < locs.size(); i++) {
			XYLocation loc = locs.get(i);
			this.setValue(loc.getXCoOrdinate(), loc.getYCoOrdinate(), count);
			count = count + 1;
		}
	}

	public boolean canMoveGap(Action where) {
		boolean retVal = true;
		int absPos = getPositionOf(0);
		if (where.equals(LEFT))
			retVal = (getYCoord(absPos) != 0);
		else if (where.equals(RIGHT))
			retVal = (getYCoord(absPos) != 3);
		else if (where.equals(UP))
			retVal = (getXCoord(absPos) != 0);
		else if (where.equals(DOWN))
			retVal = (getXCoord(absPos) != 3);
		return retVal;
	}

	@Override
	public boolean equals(Object o) {

		if (this == o) {
			return true;
		}
		if ((o == null) || (this.getClass() != o.getClass())) {
			return false;
		}
		FifteenPuzzleBoard aBoard = (FifteenPuzzleBoard) o;

		for (int i = 0; i < 15; i++) {
			if (this.getPositionOf(i) != aBoard.getPositionOf(i)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		for (int i = 0; i < 15; i++) {
			int position = this.getPositionOf(i);
			result = 37 * result + position;
		}
		return result;
	}

	@Override
	public String toString() {
		String retVal = state[0] + " " + state[1] + " " + state[2] + " " + state[3] + "\n"
				+ state[4] + " " + state[5] + " " + state[6] + " " + state[7] + "\n"
				+ state[8] + " " + state[9] + " " + state[10] + " " + state[11] + "\n"
				+ state[12] + " " + state[13] + " " + state[14] + " " + state[15];

		return retVal;
	}

	//
	// PRIVATE METHODS
	//

	/**
	 * Note: The graphic representation maps x values on row numbers (x-axis in
	 * vertical direction).
	 */
	private int getXCoord(int absPos) {
		return absPos / 4;
	}

	/**
	 * Note: The graphic representation maps y values on column numbers (y-axis
	 * in horizontal direction).
	 */
	private int getYCoord(int absPos) {
		return absPos % 4;
	}

	private int getAbsPosition(int x, int y) {
		return x * 4 + y;
	}

	private int getValueAt(int x, int y) {
		// refactor this use either case or a div/mod soln
		return state[getAbsPosition(x, y)];
	}

	private int getGapPosition() {
		return getPositionOf(0);
	}

	private int getPositionOf(int val) {
		for (int i = 0; i < 16; i++) {
			if (state[i] == val) {
				return i;
			}
		}
		return -1;
	}

	private void setValue(int x, int y, int val) {
		int absPos = getAbsPosition(x, y);
		state[absPos] = val;

	}
}
