package view;

import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Command;
import presenter.Properties;
/**
 * This interface define methods for the VIEW
 * @author Moran & Bar
 */
public interface View {

	  void start();
	  void display(String message);
	  void displayStringArray(String[] arr);
	  void displayCrossSectionBy(int[][] maze2d);
	  void displaySolution(Solution<Position> solution);
	  void setCommand(HashMap<String, Command> commandMap);
	  void displayMaze(Maze3d maze);
	  void displayPosition(Position position);
	  void setProperties(Properties prop);
	  void exit();
}
