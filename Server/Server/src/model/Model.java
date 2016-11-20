package model;

import algorithms.mazeGenerators.Maze3d;

/**
 * This interface define the methods for the model
 * @author Moran
 *
 */
public interface Model {
	boolean generate(String mazeName, int x, int y, int z);
	Maze3d getMaze3d(String mazeName);
	public boolean solve(String mazeName, String algorithm);
	public String getSolution(String mazeName);
	public void saveToZip();
	public void loadFromZip();
	//public Object getUserCommand(String command);
	public void close();
}
