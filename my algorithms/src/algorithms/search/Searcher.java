package algorithms.search;

/**
 * we may want to implement other searching algorithms in the future as well.
 * for that we need to define this interface
 */

public interface Searcher<T> {
	
	//the search method
	public Solution<T> search(Searchable<T> s);
	
	//get how many nodes were evaluated by the algorithm
	public int getNumberOfNodesEvaluated();
}
