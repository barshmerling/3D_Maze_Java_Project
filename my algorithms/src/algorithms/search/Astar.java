package algorithms.search;
/**
 * The class AStar<T> extends the class BFS<T>
 * we can see here a strategy design pattern.
 * the class Astar "hold" an instance of the interface 'Heuristic'
 * if we want to solve any searchable problem by Astar, we need to provide a specific Heuristic: Manhattan or Air.
 * @author Moran
 *
 * @param Heuristic<T>
 */
public class Astar<T> extends Bfs<T> {

	private Heuristic<T> heuristic;
	
	/**
	 * C'tor
	 * gets a type of Heuristic<T> which tells us how to calculate the distance-
	 * by Air distance or Manhattan distance
	 * @param heuristic
	 */
	public Astar(Heuristic<T> heuristic) {
		super();
		this.heuristic = heuristic;
	}
	
	/**
	 * calculate how much far a specific state from the goal state.
	 * calculate the distance  by Air distance or Manhattan distance
	 */
	@Override
	public double changeCost(State<T> state, Searchable<T> search){
		double cost;
		//cost: distance between a specific state to the goal state
		cost=heuristic.calculate(state, search.getGoalState())*search.getMoveCost();
		//return the cost (distance between a specific state to the goal state) + cost from the start to this state + movement of one edge 
		return cost + super.changeCost(state, search);
		
	}

	
	
}
