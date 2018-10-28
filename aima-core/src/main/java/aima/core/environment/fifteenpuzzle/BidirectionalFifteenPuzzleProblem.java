package aima.core.environment.fifteenpuzzle;

import aima.core.search.framework.problem.BidirectionalProblem;
import aima.core.search.framework.problem.DefaultGoalTest;
import aima.core.search.framework.problem.Problem;

/**
 * @author Ruediger Lunde
 * @author M. Kaan Tasbas
 * 
 */
public class BidirectionalFifteenPuzzleProblem extends Problem implements BidirectionalProblem {

	Problem reverseProblem;

	public BidirectionalFifteenPuzzleProblem(FifteenPuzzleBoard initialState) {
		super(initialState, FifteenPuzzleFunctionFactory.getActionsFunction(),
				FifteenPuzzleFunctionFactory.getResultFunction(),
				new DefaultGoalTest(new FifteenPuzzleBoard(new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 })));

		reverseProblem = new Problem(new FifteenPuzzleBoard(new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 }),
				FifteenPuzzleFunctionFactory.getActionsFunction(), FifteenPuzzleFunctionFactory.getResultFunction(),
				new DefaultGoalTest(initialState));
	}

	public Problem getOriginalProblem() {
		return this;
	}

	public Problem getReverseProblem() {
		return reverseProblem;
	}
}
