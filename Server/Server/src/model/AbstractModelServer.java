package model;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import controller.PropertiesServer;

/**
 * This class implements Model interface and define parameters for all kind of ModelServer.
 * @param mazeSolution - mapping between maze3d and solution.
 * @param hashMaze - mapping between name (key) and maze3d.
 * @param propertiesServer - define the properties of the server.
 * @param threadPool - for manage the threads.
 * @author Moran
 */
public abstract class AbstractModelServer implements Model {
	
	protected HashMap<Maze3d, Solution<Position>> mazeSolution;
	protected HashMap<String, Maze3d> hashMaze;
	//private HashMap<String, Object> commandMap;
	protected PropertiesServer propertiesServer;
	protected ExecutorService threadPool;
	
	/**
	 * C'tor
	 */
	public AbstractModelServer() {
		this.mazeSolution = new HashMap<Maze3d, Solution<Position>>();
		this.hashMaze = new HashMap<String, Maze3d>();
		this.propertiesServer = new PropertiesServer();
		propertiesServer.defaultPropertiesServer();
		threadPool = Executors.newFixedThreadPool(propertiesServer.getNumOfClients());
	}
	
	 
	public abstract boolean generate(String mazeName, int x, int y, int z);
	public abstract Maze3d getMaze3d(String mazeName);
	public abstract boolean solve(String mazeName, String algorithm);
	public abstract String getSolution(String mazeName);
	public abstract void saveToZip();
	public  abstract void loadFromZip();
	//public abstract Object getUserCommand(String command);
	public abstract void close();

}
