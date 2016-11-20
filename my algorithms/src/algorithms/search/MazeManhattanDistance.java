package algorithms.search;

import algorithms.mazeGenerators.Position;
/**
 * this class calculate the Manhattan Distance by: the sum of distance levels, rows and columns from the goal state
 * @author Moran
 * @param <T>
 */ 
public class MazeManhattanDistance implements Heuristic<Position> {

	@Override
	public double calculate(State<Position> state, State<Position> goalState) {
		double levels, rows, columns;
		levels=(double)(Math.abs(goalState.getState().getX() - state.getState().getX()));
		rows=(double)(Math.abs(goalState.getState().getY() - state.getState().getY()));
		columns=(double)(Math.abs(goalState.getState().getZ() - state.getState().getZ()));
		return levels+rows+columns;
 	}

}
