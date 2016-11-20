package algorithms.search;

import algorithms.mazeGenerators.Position;

/**
 * this class calculate the Air Distance by: the square root of the sum of the distances
 * @author Moran
 * @param <T>
 */
public class MazeAirDistance implements Heuristic<Position> {

	@Override
	public double calculate(State<Position> state, State<Position> goalState) {
		double levels, rows, columns;
		levels=(double)(Math.pow(goalState.getState().getX() - state.getState().getX(),2));
		rows=(double)(Math.pow(goalState.getState().getY() - state.getState().getY(),2));
		columns=(double)(Math.pow(goalState.getState().getZ() - state.getState().getZ(),2));
		return Math.sqrt(levels+rows+columns);
	}
}
