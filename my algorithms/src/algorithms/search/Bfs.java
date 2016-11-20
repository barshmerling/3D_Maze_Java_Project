package algorithms.search;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Best First Search class using a priority queue
 * 
 * @author Moran
 * @param Solution<T>
 * @return Solution- the steps for solving the problem from start state to goal
 *         state
 */
public class Bfs<T> extends CommonSearcher<T> {

	@Override
	public Solution<T> search(Searchable<T> s) {
		
		HashSet<State<T>> closedSet = new HashSet<State<T>>();
	
		// add the start state to the open list
		addToOpenList(s.getInitialState());
		
		// while there is more states we have not checked yet
		while (openList.size() > 0) {
			State<T> n = popOpenList();
			closedSet.add(n);

			// if we found the path from the start state to the goal state
			if ((n.getState()).equals(s.getGoalState().getState())) {
			//if ((n).equals(s.getGoalState())) {
				// private method, back traces through the parents
				return backTrace(n, s.getInitialState());
			}

			ArrayList<State<T>> successors = s.getAllPossibleStates(n);

			for (State<T> state : successors) {
				// update the cost of current state according to the parent
				state.setCost(n.getCost() + changeCost(state, s));
				state.setCameFrom(n);
				// if this is the first time we see this state
				if ((!closedSetContians(closedSet, state)) && (!openListContains(state))) {
				    addToOpenList(state);
					
				} else {
					if (!closedSetContians(closedSet, state))
						findAndCompare(state);
				}			
			}
		}
		return new Solution<T>();// the "real" return happens in backTrace in
									// the end of the while-loop
	}

	/**
	 * private method, back traces through the parents
	 * 
	 * @param goalState
	 * @param startState
	 * @return Solution<T> witch represent us the path from the start state to
	 *         the end state
	 */
	private Solution<T> backTrace(State<T> goalState, State<T> startState) {
		// variables
		Solution<T> solution = new Solution<T>();
		State<T> currentState = goalState;

		solution.setInitialState(startState);
		solution.setGoalState(goalState);

		while (currentState.getCameFrom()!= null) {
			solution.addStateToSolutionArrayList(currentState);
			currentState = currentState.getCameFrom();
		}
		solution.addStateToSolutionArrayList(currentState);

		return solution;
	}

	/**
	 * this method change the cost for each State
	 * 
	 * @param state
	 * @param search
	 */
	@Override
	public double changeCost(State<T> state, Searchable<T> search) {
		return search.getMoveCost();
	}
	
}
