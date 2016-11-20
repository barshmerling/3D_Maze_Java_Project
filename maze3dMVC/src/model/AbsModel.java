package model;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import controller.Controller;

/**
 * this class implements Model interface
 * 
 * @param controller
 *            holds the specific controller
 * @param hashMaze
 *            mapping between the key (String maze's name) to the Maze
 * @param hashSolution
 *            mapping between the key (String maze's name) to the Solution
 * @param threadPool
 *            managing threads
 * @author Moran
 *
 */
public abstract class AbsModel implements Model {

	protected Controller controller;
	protected HashMap<String, Maze3d> hashMaze;
	protected HashMap<String, Solution<Position>> hashSolution;
	protected ExecutorService threadPool;

	/**
	 * C'tor
	 */
	public AbsModel() {
		this.hashMaze = new HashMap<String, Maze3d>();
		this.hashSolution = new HashMap<String, Solution<Position>>();
		this.threadPool = Executors.newCachedThreadPool();
	}

	/**
	 * Setters & Getters
	 */
	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public HashMap<String, Maze3d> getHashMaze() {
		return hashMaze;
	}

	public void setHashMaze(HashMap<String, Maze3d> hashMaze) {
		this.hashMaze = hashMaze;
	}

	public HashMap<String, Solution<Position>> getHashSolution() {
		return hashSolution;
	}

	public void setHashSolution(HashMap<String, Solution<Position>> hashSolution) {
		this.hashSolution = hashSolution;
	}

	public abstract void generateMaze(String name, int x, int y, int z);
	public abstract void displayMaze(String name);
	public abstract void displayCrossSection(String name, String by, int index);
	public abstract void saveMaze(String name, String fileName);
	public abstract void loadMaze(String name, String fileName);
	public abstract void mazeSize(String name);
	public abstract void fileSize(String name);
	public abstract void solveMaze(String name, String algorithm);
	public abstract void displaySolution(String name);
	public abstract void exit();

}
