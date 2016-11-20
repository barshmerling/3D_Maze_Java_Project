package algorithms.demo;


import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.Astar;
import algorithms.search.Bfs;
import algorithms.search.MazeAirDistance;
import algorithms.search.MazeManhattanDistance;
import algorithms.search.Searcher;
import algorithms.search.Solution;

/**
 * this class creates an object adapter, solving the maze by Bfs and Astar (separate for each heuristic)
 * @author Moran
 *
 */
public class Demo {
	public void run(){
		
		//part A- Creating a maze3d by MyMaze3dGenerator 
		MyMaze3dGenerator myMaze = new MyMaze3dGenerator();
		Maze3d maze3d = myMaze.generate(4,6,6);
		
		//print the maze
		maze3d.printMaze();
		
		Position start=new Position(maze3d.getStartPosition());
		Position goal=new Position(maze3d.getGoalPosition());
		MazeSearch mazeSearch = new MazeSearch(maze3d);
		System.out.println("Start position:" + start);
		System.out.println("Goal position:" + goal);
		System.out.println("Prints the pate from the goal state to the statr:");
		System.out.println("*******************************");
		//part C- Solving the maze using Bfs
		Searcher<Position> searcher = new Bfs<Position>();
		Solution<Position> solution = searcher.search(mazeSearch);
		System.out.println("Bfs");
		solution.print();
		System.out.println();
		System.out.println("evaluded nodes:"+ searcher.getNumberOfNodesEvaluated());
		System.out.println();

		//part D- Solving the maze using Heuristic
		
		//MazeAirDistance
		Searcher<Position> searcher1 = new Astar<Position>(new MazeAirDistance());
		Solution<Position> solution1 = searcher1.search(mazeSearch);
		
		System.out.println("Astar - by Air Distance");
		solution1.print();
		System.out.println();
		System.out.println("evaluded nodes:"+ searcher1.getNumberOfNodesEvaluated());
		System.out.println();

		//Manhattan Heuristic
		Searcher<Position> searcher2 = new Astar<Position>(new MazeManhattanDistance());
		Solution<Position> solution2 = searcher2.search(mazeSearch);
		
		
		System.out.println("Astar - by Manhattan Distance");
		solution2.print();
		System.out.println("evaluded nodes:"+ searcher2.getNumberOfNodesEvaluated());
	} 
}
