package algorithms.demo;

import java.util.ArrayList;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Searchable;
import algorithms.search.State;

/**
 * this class implements search method for maze3d problem
 * @author Moran
 * @param <T> =Position 
 */
public class MazeSearch implements Searchable<Position> {

	//variables
	private State<Position> start;
	private State<Position> goal; 
	private Maze3d maze3d;
	
	//C'tor
	public MazeSearch(Maze3d myMaze3d)
	{
		this.start=new State<Position>(myMaze3d.getStartPosition());
		this.goal=new State<Position>(myMaze3d.getGoalPosition());
		this.maze3d = myMaze3d;
	}
	
	@Override
	public State<Position> getInitialState() {
		return this.start;
	}

	@Override
	public State<Position> getGoalState() {
		return this.goal;
	}
	
	@Override
	public ArrayList<State<Position>> getAllPossibleStates(State<Position> s) {
		//variables
		ArrayList<State<Position>> possibleMoves = new ArrayList<State<Position>>();
		Position pos = s.getState();
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();

		//convert arrays of String to ArrayList.
		String [] tempStringArray = this.maze3d.getPossibleMoves(pos);
		for (String direction : tempStringArray){
			switch(direction){
			case "Up":
				possibleMoves.add(new State<Position>(new Position(x+1,y,z)));
				break;
			case "Down":
				possibleMoves.add(new State<Position>(new Position(x-1,y,z)));
				break;
			case "Left":
 				possibleMoves.add(new State<Position>(new Position(x,y-1,z)));
				break;
			case "Right":
				possibleMoves.add(new State<Position>(new Position(x,y+1,z)));
				break;
			case "Forward":
				possibleMoves.add(new State<Position>(new Position(x,y,z+1)));
				break;

			case "Backward":
				possibleMoves.add(new State<Position>(new Position(x,y,z-1)));
				break;
			}
		}
		//
//		if (s.getCameFrom()!=null) {
//			possibleMoves.remove(s.getCameFrom()); 
//		}
		return possibleMoves;
	}

	@Override
	public double getMoveCost() {
		return 1;
	}

}
