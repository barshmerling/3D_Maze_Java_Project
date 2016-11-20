package algorithms.search;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * 
 * @author Moran
 * The abstract class CommonSearcher<T> implements Searcher<T>.
 * the Data members are:
 * @param openList (Priority Queue)
 * @param evaluatedNodes
 */
public abstract class CommonSearcher<T> implements Searcher<T> {

	protected PriorityQueue<State<T>> openList;
	private int evaluatedNodes;

	/**
	 * C'tor - initialize the priority queue that it will sort the states
	 * according to their cost
	 */
	public CommonSearcher() {
		openList = new PriorityQueue<State<T>>(new Comparator<State<T>>() {
			@Override
			public int compare(State<T> o1, State<T> o2) {
				return (int) (o1.getCost() - o2.getCost());
			}
		});
		this.evaluatedNodes = 0;
	}

	/**
	 * pop an object from the open list and add 1 to the evaluated nodes.
	 * 
	 * @return State<T>
	 */
	protected State<T> popOpenList() {
		evaluatedNodes++;
		return openList.poll();
	}

	/**
	 * gets a State<T> variable and add it to the open list
	 * 
	 * @param s
	 */
	public void addToOpenList(State<T> s) {
		openList.add(s);
	}

	@Override
	public abstract Solution<T> search(Searchable<T> s);

	@Override
	public int getNumberOfNodesEvaluated() {
		return this.evaluatedNodes;
	}

	public abstract double changeCost(State<T> state, Searchable<T> search);

	/**
	 * Gets a State<T> variable and checks if it is already in the openList
	 * 
	 * @param s
	 * @return TRUE/FALSE
	 */
	public boolean openListContains(State<T> s) {
		Iterator<State<T>> itr = openList.iterator();
		while (itr.hasNext()) {
			State<T> temp = itr.next();
			if (s.getState().equals(temp.getState()))
				return true;
		}
		return false;
	}

	/**
	 * Gets a State<T> variable and checks if it is already in the closedSet
	 * 
	 * @param s
	 * @param closedSet
	 * @return TRUE/FALSE
	 */
	public boolean closedSetContians(HashSet<State<T>> closedSet, State<T> s) {
		Iterator<State<T>> itr = closedSet.iterator();
		while (itr.hasNext()) {
			State<T> temp = itr.next();
			if (s.getState().equals(temp.getState()))
				return true;
		}
		return false;
	}

	/**
	 * Gets a State<T> and checks its cost value, if it is better than the
	 * previous, it replaces the previous with the new.
	 * 
	 * @param s
	 */
	public void findAndCompare(State<T> s) {

		if (openListContains(s)) {
			Iterator<State<T>> itr = openList.iterator();
			while (itr.hasNext()) {
				State<T> temp = itr.next();
				if (s.getState().equals(temp.getState())) {
					if (s.getCost() < temp.getCost()) {
						itr.remove();
						addToOpenList(s);
					}
					return;
				}
			}
		}
		addToOpenList(s);
	}

}
