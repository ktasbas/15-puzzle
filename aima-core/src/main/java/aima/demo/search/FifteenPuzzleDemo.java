package aima.demo.search;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import aima.core.agent.Action;
import aima.core.environment.fifteenpuzzle.FifteenPuzzleBoard;
import aima.core.environment.fifteenpuzzle.FifteenPuzzleFunctionFactory;
import aima.core.environment.fifteenpuzzle.FifteenPuzzleGoalTest;
import aima.core.environment.fifteenpuzzle.ManhattanHeuristicFunction;
import aima.core.environment.fifteenpuzzle.MisplacedTilleHeuristicFunction;
import aima.core.search.framework.SearchAgent;
import aima.core.search.framework.SearchForActions;
import aima.core.search.framework.problem.Problem;
import aima.core.search.framework.qsearch.GraphSearch;
import aima.core.search.informed.AStarSearch;
/**
 * Possible future implementations for 15 puzzle:
 */
// import aima.core.search.informed.GreedyBestFirstSearch;
// import aima.core.search.local.SimulatedAnnealingSearch;
// import aima.core.search.uninformed.DepthLimitedSearch;
// import aima.core.search.uninformed.IterativeDeepeningSearch;

/**
 * @author Ravi Mohan
 * @author M. Kaan Tasbas
 * 
 */

public class FifteenPuzzleDemo {
	static FifteenPuzzleBoard boardWithThreeMoveSolution = new FifteenPuzzleBoard(new int[] { 1, 2, 3, 0, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 });;

	static FifteenPuzzleBoard random1 = new FifteenPuzzleBoard(new int[] { 1, 5, 2, 3, 9, 6, 7, 11, 4, 8, 10, 0, 12, 13, 14, 15 });

	//static FifteenPuzzleBoard extreme = new FifteenPuzzleBoard(new int[] { 0, 8, 7, 6, 5, 4, 3, 2, 1 });

	static FifteenPuzzleBoard unreachable = new FifteenPuzzleBoard(new int[] { 2, 1, 5, 12, 6, 8, 0, 4, 9, 7, 10, 13, 14, 15, 3, 11});

	public static void main(String[] args) {
		fifteenPuzzleAStarDemo();
		fifteenPuzzleAStarManhattanDemo();
	}

	/**
	 * Driver for 15 puzzle using A* search with misplaced tile heuristic
	 * Currently uses a preset board
	 * TODO: make random option
	 * @param none
	 * @return none
	 */
	private static void fifteenPuzzleAStarDemo() {
		FifteenPuzzleBoard initialBoard = random1;
		FifteenPuzzleGoalTest goalBoard = new FifteenPuzzleGoalTest();

		System.out.println("\nFifteenPuzzleDemo AStar Search (MisplacedTileHeursitic)-->");
		// check if goal is reachable
		if(initialBoard.GoalReachable(goalBoard.getGoalBoard())) {
			try {
				Problem problem = new Problem(initialBoard, FifteenPuzzleFunctionFactory.getActionsFunction(),
						FifteenPuzzleFunctionFactory.getResultFunction(), new FifteenPuzzleGoalTest());
				SearchForActions search = new AStarSearch(new GraphSearch(), new MisplacedTilleHeuristicFunction());
				SearchAgent agent = new SearchAgent(problem, search);
				printActions(agent.getActions());
				printInstrumentation(agent.getInstrumentation());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			System.out.println("ERROR: goal state is not reachable");
		}
	}

	/**
	 * Driver for 15 puzzle using A* search with manhattan distance heuristic
	 * Currently uses a preset board
	 * TODO: make a random option
	 * @param none
	 * @return none
	 */
	private static void fifteenPuzzleAStarManhattanDemo() {
		FifteenPuzzleBoard initialBoard = random1;
		FifteenPuzzleGoalTest goalBoard = new FifteenPuzzleGoalTest();

		System.out.println("\nFifteenPuzzleDemo AStar Search (ManhattanHeursitic)-->");
		// check if goal is reachable
		if(initialBoard.GoalReachable(goalBoard.getGoalBoard())) {
			try {
				Problem problem = new Problem(random1, FifteenPuzzleFunctionFactory.getActionsFunction(),
						FifteenPuzzleFunctionFactory.getResultFunction(), new FifteenPuzzleGoalTest());
				SearchForActions search = new AStarSearch(new GraphSearch(), new ManhattanHeuristicFunction());
				SearchAgent agent = new SearchAgent(problem, search);
				printActions(agent.getActions());
				printInstrumentation(agent.getInstrumentation());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			System.out.println("ERROR: goal state is not reachable");
		}
	}

	private static void printInstrumentation(Properties properties) {
		Iterator<Object> keys = properties.keySet().iterator();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			String property = properties.getProperty(key);
			System.out.println(key + " : " + property);
		}

	}

	private static void printActions(List<Action> actions) {
		for (int i = 0; i < actions.size(); i++) {
			String action = actions.get(i).toString();
			System.out.println(action);
		}
	}

}