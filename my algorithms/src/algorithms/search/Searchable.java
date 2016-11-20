package algorithms.search;

import java.util.ArrayList;

/**
 * we use this interface due to the reason we are able to switch 
 * searchable domains independently from the searching algorithm
 */
public interface Searchable<T> {
	
	State<T> getInitialState();
	State<T> getGoalState();
	ArrayList<State<T>> getAllPossibleStates(State<T> s);
	double getMoveCost();

}
