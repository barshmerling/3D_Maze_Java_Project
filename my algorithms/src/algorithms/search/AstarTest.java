package algorithms.search;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import algorithms.demo.MazeSearch;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
/**
 * Astar Test
 * @author moran
 *
 */
public class AstarTest {

	/**
	 * this method check if Astar algorithm truly solve the maze in shortest
	 * Path. this method generate a byte array witch indicate a maze.
	 * the size of the maze is 4 X 5 X 5.
	 * start position: {1,1,0}.
	 * goal position: {1,4,1}.
	 */
	@Test
	public void shortestPathTest() {
		byte[] maze = { 0, 0, 0, 4, 0, 0, 0, 5, 0, 0, 0, 5, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 4,
				0, 0, 0, 1,

				1, 1, 1, 1, 1,
				1, 1, 1, 1, 1,
				1, 1, 1, 1, 1,
				1, 1, 1, 1, 1,
				1, 1, 1, 1, 1,

				
				1, 1, 1, 1, 1,
				0, 0, 0, 0, 1,
				1, 0, 1, 0, 1,
				1, 0, 0, 0, 1,
				1, 0, 1, 1, 1,

				
				1, 1, 1, 1, 1, 
				1, 1, 1, 1, 1,
				1, 1, 1, 1, 1, 
				1, 1, 1, 1, 1, 
				1, 1, 1, 1, 1,

				
				1, 1, 1, 1, 1,
				1, 1, 1, 1, 1,
				1, 1, 1, 1, 1, 
				1, 1, 1, 1, 1, 
				1, 1, 1, 1, 1 };
		
		Maze3d mazeTest = null;
		try {
			mazeTest = new Maze3d(maze);
		} catch (IOException e) {
			e.printStackTrace();
		}
		CommonSearcher<Position> commonSearcher = new Astar<Position>(new MazeAirDistance());
		MazeSearch mazeSearch = new MazeSearch(mazeTest);
		Solution<Position> solution = commonSearcher.search(mazeSearch);
		Solution<Position> trueSol = new Solution<Position>();
		trueSol.addStateToSolutionArrayList(new State<Position>(new Position(1, 4, 1)));
		trueSol.addStateToSolutionArrayList(new State<Position>(new Position(1, 3, 1)));
		trueSol.addStateToSolutionArrayList(new State<Position>(new Position(1, 2, 1)));
		trueSol.addStateToSolutionArrayList(new State<Position>(new Position(1, 1, 1)));
		trueSol.addStateToSolutionArrayList(new State<Position>(new Position(1, 1, 0)));
		System.out.println();
		trueSol.print();
		System.out.println();
		solution.print();
		assertEquals(trueSol, solution);
	}
	
	/**
	 * this method check if the start position and the end position equals
	 */
	@Test
    public void startEqualsEndTest() {
		byte[] maze = { 0, 0, 0, 4, 0, 0, 0, 5, 0, 0, 0, 5, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 4,
				0, 0, 0, 1,

				1, 1, 1, 1, 1,
				1, 1, 1, 1, 1,
				1, 1, 1, 1, 1,
				1, 1, 1, 1, 1,
				1, 1, 1, 1, 1,

				
				1, 1, 1, 1, 1 ,
				0, 0, 0, 0, 1,
				1, 0, 1, 0, 1,
				1, 0, 0,  0, 1,
				1, 0, 1, 1, 1,

				
				1, 1, 1, 1, 1, 
				1, 1, 1, 1, 1,
				1, 1, 1, 1, 1, 
				1, 1, 1, 1, 1, 
				1, 1, 1, 1, 1,

				
				1, 1, 1, 1, 1,
				1, 1, 1, 1, 1,
				1, 1, 1, 1, 1, 
				1, 1, 1, 1, 1, 
				1, 1, 1, 1, 1 };
		
		Maze3d mazeTest = null;
		try {
			mazeTest = new Maze3d(maze);
		} catch (IOException e) {
			e.printStackTrace();
		}
		CommonSearcher<Position> cs1 = new Astar<Position>(new MazeAirDistance());
		MazeSearch mazeSearch = new MazeSearch(mazeTest);
		Solution<Position> solution = cs1.search(mazeSearch);
		Solution<Position> trueSol = new Solution<Position>();
		trueSol.addStateToSolutionArrayList(new State<Position>(new Position(1, 1, 0)));
		trueSol.print();
		System.out.println();
		solution.print();
		assertEquals(trueSol, solution);
	}
}
		


