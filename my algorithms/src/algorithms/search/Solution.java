package algorithms.search;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The class Solution shows us the expected solution - 
 * the path from the start state to the goal state
 * @author Moran
 *
 * @param <T>
 */
public class Solution<T> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private State<T> initialState;
	private State<T> goalState;
	private ArrayList<State<T>> solution;
	
	/**
	 * C'tor
	 */
	public Solution() {
		solution = new ArrayList<State<T>>();
	}
	
	//Getters & Setters
	public State<T> getInitialState() {
		return initialState;
	}
	public void setInitialState(State<T> initialState) {
		this.initialState = initialState;
	}
	public State<T> getGoalState() {
		return goalState;
	}
	public void setGoalState(State<T> goalState) {
		this.goalState = goalState;
	}
	public ArrayList<State<T>> getSolution() {
		return solution;
	}
	public void setSolution(ArrayList<State<T>> solution) {
		this.solution = solution;
	}
	
	public void addStateToSolutionArrayList(State<T> s){
		solution.add(s);
	}
	
	@Override
	public String toString(){
		return this.solution.toString();
	}
	
	/**
     * print method
     */
    public void print() {
    	for(int i=0;i<solution.size();i++)
			System.out.println(solution.get(i));
    }
}
