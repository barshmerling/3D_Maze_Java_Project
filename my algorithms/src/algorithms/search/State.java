package algorithms.search;

import java.io.Serializable;

import algorithms.mazeGenerators.Position;

/**
 * Generic version of the State class.
 * @param <T>  represent the type of our state
 * @param cost is the weight of the path - the calculate cost from the start position to this state
 * @param cameFrom indicate from which state we arrive to this current state
 */
public class State<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private T state;
	private double cost;
	private State<T> cameFrom;
	
	/**
	 * C'tor
	 * @param state
	 */
	public State(T state){
		this.state=state;
		this.cost = 0;
		this.cameFrom = null;
	}
	
	/**
	 * Copy C'tor
	 * @param s
	 */
	public State(State<T> s){
		this.state=s.state;
		this.cost=s.cost;
		this.cameFrom=s.cameFrom;
	}

	//need to implement the method equals in every <T> type
/*	public boolean equals(State<T> s){
		if(this.getState() instanceof Position){
			return ((Position)(this.getState())).equals((Position)(s.state));
		}
		return false;
	}*/
	
	// we override Object's equals method
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		return this.state.equals(((State<T>)(obj)).getState());
	}
	
	//Getters& Setters
	public T getState() {
		return state;
	}

	public void setState(T state) {
		this.state = state;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public State<T> getCameFrom() {
		return cameFrom;
	}

	public void setCameFrom(State<T> cameFrom) {
		this.cameFrom = cameFrom;
	}
	
	/**
	 * override methods of "Object"
	 */

	
	/*@Override
	public String toString(){	
		return this.getState().toString();
	}*/
	
	@Override
	public String toString(){
		if(this.getState() instanceof Position){
			return ((Position)(this.getState())).toString();
		}
		return this.getState().toString();

	}
	
	@Override
	public int hashCode(){
		return toString().hashCode();
	}
	
	 /**
     * print method
     */
    public void print() {
	System.out.print("[" + state + "," + cost + "]");
	System.out.println();
    }
}
