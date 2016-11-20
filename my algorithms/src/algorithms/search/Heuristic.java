package algorithms.search;

/**
 * this interface define mutual method named "calculate" for Manhatten class and Air Heuristic class.
 * each class implement differently the method calculate
 * @author Moran
 *
 * @param <T>
 */
public interface Heuristic<T> {
	
	double calculate(State<T> state, State<T> goalState);

}
