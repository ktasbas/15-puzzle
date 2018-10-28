package aima.gui.swing.applications.search.games;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.swing.JButton;

import aima.core.agent.Action;
import aima.core.agent.Agent;
import aima.core.agent.Environment;
import aima.core.agent.Percept;
import aima.core.agent.impl.AbstractEnvironment;
import aima.core.environment.fifteenpuzzle.BidirectionalFifteenPuzzleProblem;
import aima.core.environment.fifteenpuzzle.FifteenPuzzleBoard;
import aima.core.environment.fifteenpuzzle.FifteenPuzzleGoalTest;
import aima.core.environment.fifteenpuzzle.ManhattanHeuristicFunction;
import aima.core.environment.fifteenpuzzle.MisplacedTilleHeuristicFunction;
import aima.core.search.framework.SearchAgent;
import aima.core.search.framework.SearchForActions;
import aima.core.search.framework.problem.Problem;
//import aima.core.search.framework.qsearch.BidirectionalSearch;
import aima.core.search.framework.qsearch.GraphSearch;
import aima.core.search.informed.AStarSearch;
// import aima.core.search.informed.GreedyBestFirstSearch;
// import aima.core.search.local.SimulatedAnnealingSearch;
// import aima.core.search.uninformed.BreadthFirstSearch;
// import aima.core.search.uninformed.DepthLimitedSearch;
// import aima.core.search.uninformed.IterativeDeepeningSearch;
import aima.core.util.datastructure.XYLocation;
import aima.gui.swing.framework.AgentAppController;
import aima.gui.swing.framework.AgentAppEnvironmentView;
import aima.gui.swing.framework.AgentAppFrame;
import aima.gui.swing.framework.MessageLogger;
import aima.gui.swing.framework.SimpleAgentApp;
import aima.gui.swing.framework.SimulationThread;

/**
 * Graphical 15-puzzle game application. It demonstrates the performance of
 * different search algorithms. Additionally, users can make experiences with
 * human problem solving.
 * 
 * @author Ruediger Lunde
 * @author M. Kaan Tasbas
 */
public class FifteenPuzzleApp extends SimpleAgentApp {

	/** List of supported search algorithm names. */
	protected static List<String> SEARCH_NAMES = new ArrayList<String>();
	/** List of supported search algorithms. */
	protected static List<SearchForActions> SEARCH_ALGOS = new ArrayList<SearchForActions>();

	/** Adds a new item to the list of supported search algorithms. */
	public static void addSearchAlgorithm(String name, SearchForActions algo) {
		SEARCH_NAMES.add(name);
		SEARCH_ALGOS.add(algo);
	}

	static {
		addSearchAlgorithm("AStar Search (MisplacedTileHeuristic)",
				new AStarSearch(new GraphSearch(), new MisplacedTilleHeuristicFunction()));
		addSearchAlgorithm("AStar Search (ManhattanHeuristic)",
				new AStarSearch(new GraphSearch(), new ManhattanHeuristicFunction()));
	}

	/** Returns an <code>FifteenPuzzleView</code> instance. */
	public AgentAppEnvironmentView createEnvironmentView() {
		return new FifteenPuzzleView();
	}

	/** Returns a <code>FifteenPuzzleFrame</code> instance. */
	@Override
	public AgentAppFrame createFrame() {
		return new FifteenPuzzleFrame();
	}

	/** Returns a <code>FifteenPuzzleController</code> instance. */
	@Override
	public AgentAppController createController() {
		return new FifteenPuzzleController();
	}

	// ///////////////////////////////////////////////////////////////
	// main method

	/**
	 * Starts the application.
	 */
	public static void main(String args[]) {
		new FifteenPuzzleApp().startApplication();
	}

	// ///////////////////////////////////////////////////////////////
	// some inner classes

	/**
	 * Adds some selectors to the base class and adjusts its size.
	 */
	protected static class FifteenPuzzleFrame extends AgentAppFrame {
		private static final long serialVersionUID = 1L;
		public static String ENV_SEL = "EnvSelection";
		public static String SEARCH_SEL = "SearchSelection";

		public FifteenPuzzleFrame() {
			setTitle("Fifteen Puzzle Application");
			setSelectors(new String[] { ENV_SEL, SEARCH_SEL },
					new String[] { "Select Environment", "Select Search" });
			setSelectorItems(ENV_SEL, new String[] { "Three Moves", "Medium", "Extreme", "Random" }, 0);
			setSelectorItems(SEARCH_SEL, (String[]) SEARCH_NAMES.toArray(new String[] {}), 0);
			setEnvView(new FifteenPuzzleView());
			setSize(1000, 800);
		}
	}

	/**
	 * Displays the informations provided by a
	 * <code>FifteenPuzzleEnvironment</code> on a panel using an grid of buttons.
	 * By pressing a button, the user can move the corresponding tile to the
	 * adjacent gap.
	 */
	protected static class FifteenPuzzleView extends AgentAppEnvironmentView implements ActionListener {
		private static final long serialVersionUID = 1L;
		protected JButton[] squareButtons;

		protected FifteenPuzzleView() {
			setLayout(new GridLayout(4, 4));
			Font f = new java.awt.Font(Font.SANS_SERIF, Font.PLAIN, 26);
			squareButtons = new JButton[16];
			for (int i = 0; i < 16; i++) {
				JButton square = new JButton("");
				square.setFont(f);
				square.addActionListener(this);
				squareButtons[i] = square;
				add(square);
			}
		}

		@Override
		public void setEnvironment(Environment env) {
			super.setEnvironment(env);
			showState();
		}

		/** Agent value null indicates a user initiated action. */
		@Override
		public void agentActed(Agent agent, Action action, Environment source) {
			showState();
			notify((agent == null ? "User: " : "") + action.toString());
		}

		@Override
		public void agentAdded(Agent agent, Environment source) {
			showState();
		}

		/**
		 * Displays the board state by labeling and coloring the square buttons.
		 */
		protected void showState() {
			int[] vals = ((FifteenPuzzleEnvironment) env).getBoard().getState();
			for (int i = 0; i < 16; i++) {
				squareButtons[i].setBackground(vals[i] == 0 ? Color.LIGHT_GRAY : Color.WHITE);
				squareButtons[i].setText(vals[i] == 0 ? "" : Integer.toString(vals[i]));
			}
		}

		/**
		 * When the user presses square buttons the board state is modified
		 * accordingly.
		 */
		@Override
		public void actionPerformed(ActionEvent ae) {
			for (int i = 0; i < 16; i++) {
				if (ae.getSource() == squareButtons[i]) {
					FifteenPuzzleController contr = (FifteenPuzzleController) getController();
					XYLocation locGap = ((FifteenPuzzleEnvironment) env).getBoard().getLocationOf(0);
					if (locGap.getXCoOrdinate() == i / 4) {
						if (locGap.getYCoOrdinate() == i % 4 - 1)
							contr.executeUserAction(FifteenPuzzleBoard.RIGHT);
						else if (locGap.getYCoOrdinate() == i % 4 + 1)
							contr.executeUserAction(FifteenPuzzleBoard.LEFT);
					} else if (locGap.getYCoOrdinate() == i % 4) {
						if (locGap.getXCoOrdinate() == i / 4 - 1)
							contr.executeUserAction(FifteenPuzzleBoard.DOWN);
						else if (locGap.getXCoOrdinate() == i / 4 + 1)
							contr.executeUserAction(FifteenPuzzleBoard.UP);
					}
				}
			}
		}
	}

	/**
	 * Defines how to react on standard simulation button events.
	 */
	protected static class FifteenPuzzleController extends AgentAppController {

		protected FifteenPuzzleEnvironment env = null;
		protected SearchAgent agent = null;
		protected boolean dirty;

		/** Prepares next simulation. */
		@Override
		public void clear() {
			prepare(null);
		}

		/**
		 * Creates an eight puzzle environment and clears the current search
		 * agent.
		 */
		@Override
		public void prepare(String changedSelector) {
			AgentAppFrame.SelectionState selState = frame.getSelection();
			FifteenPuzzleBoard board = null;
			switch (selState.getIndex(FifteenPuzzleFrame.ENV_SEL)) {
			case 0: // three moves
				board = new FifteenPuzzleBoard(new int[] { 1, 2, 3, 0, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 });
				break;
			case 1: // medium
				board = new FifteenPuzzleBoard(new int[] { 1, 5, 2, 3, 9, 6, 7, 11, 4, 8, 10, 0, 12, 13, 14, 15 });
				break;
			case 2: // extreme
				board = new FifteenPuzzleBoard(new int[] { 2, 6, 5, 13, 4, 10, 12, 7, 1, 14, 9, 0, 8, 15, 11, 3 });
				break;
			case 3: // random
				board = new FifteenPuzzleBoard(new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 });
				Random r = new Random(System.currentTimeMillis());
				for (int i = 0; i < 200; i++) {
					switch (r.nextInt(4)) {
					case 0:
						board.moveGapUp();
						break;
					case 1:
						board.moveGapDown();
						break;
					case 2:
						board.moveGapLeft();
						break;
					case 3:
						board.moveGapRight();
						break;
					}
				}
			}
			env = new FifteenPuzzleEnvironment(board);
			agent = null;
			dirty = false;
			frame.getEnvView().setEnvironment(env);
		}

		/**
		 * Creates a new search agent and adds it to the current environment if
		 * necessary.
		 */
		protected void addAgent() throws Exception {
			if (agent == null) {
				int pSel = frame.getSelection().getIndex(FifteenPuzzleFrame.SEARCH_SEL);
				Problem problem = new BidirectionalFifteenPuzzleProblem(env.getBoard());
				SearchForActions search = SEARCH_ALGOS.get(pSel);
				agent = new SearchAgent(problem, search);
				env.addAgent(agent);
			}
		}

		/** Checks whether simulation can be started. */
		@Override
		public boolean isPrepared() {
			return !dirty && (agent == null || !agent.isDone());
		}

		/** Starts simulation. */
		@Override
		public void run(MessageLogger logger) {
			logger.log("<simulation-log>");

			// check if goal board is reachable, else notify user

			if(env.getBoard().GoalReachable(new FifteenPuzzleGoalTest().getGoalBoard())) {
				try {
					addAgent();
					while (!agent.isDone() && !frame.simulationPaused()) {
						Thread.sleep(500);
						env.step();
					}
				} catch (InterruptedException e) {
					// nothing to do...
				} catch (Exception e) {
					e.printStackTrace(); // probably search has failed...
				}
				logger.log(getStatistics());
			}
			else {
				logger.log("ERROR: goal state is not reachable");
			}
			
			logger.log("</simulation-log>\n");
		}

		/** Executes one simulation step. */
		@Override
		public void step(MessageLogger logger) {
			try {
				addAgent();
				env.step();
			} catch (Exception e) {
				e.printStackTrace(); // probably search has failed...
			}
		}

		/** Updates the status of the frame after simulation has finished. */
		public void update(SimulationThread simulationThread) {
			if (simulationThread.isCanceled()) {
				frame.setStatus("Task canceled.");
			} else if (frame.simulationPaused()) {
				frame.setStatus("Task paused.");
			} else {
				frame.setStatus("Task completed.");
			}
		}

		/** Provides a text with statistical information about the last run. */
		private String getStatistics() {
			StringBuffer result = new StringBuffer();
			Properties properties = agent.getInstrumentation();
			Iterator<Object> keys = properties.keySet().iterator();
			while (keys.hasNext()) {
				String key = (String) keys.next();
				String property = properties.getProperty(key);
				result.append("\n" + key + " : " + property);
			}
			return result.toString();
		}

		public void executeUserAction(Action action) {
			env.executeAction(null, action);
			agent = null;
			dirty = true;
			frame.updateEnabledState();
		}
	}

	/** Simple environment maintaining just the current board state. */
	protected static class FifteenPuzzleEnvironment extends AbstractEnvironment {
		FifteenPuzzleBoard board;

		protected FifteenPuzzleEnvironment(FifteenPuzzleBoard board) {
			this.board = board;
		}

		protected FifteenPuzzleBoard getBoard() {
			return board;
		}

		/** Executes the provided action and returns null. */
		@Override
		public void executeAction(Agent agent, Action action) {
			if (action == FifteenPuzzleBoard.UP)
				board.moveGapUp();
			else if (action == FifteenPuzzleBoard.DOWN)
				board.moveGapDown();
			else if (action == FifteenPuzzleBoard.LEFT)
				board.moveGapLeft();
			else if (action == FifteenPuzzleBoard.RIGHT)
				board.moveGapRight();
			if (agent == null)
				notifyEnvironmentViews(agent, action);
		}

		/** Returns null. */
		@Override
		public Percept getPerceptSeenBy(Agent anAgent) {
			return null;
		}
	}
}
