package model;

/**
 * This interface define methods which need for the Model
 * 
 * @author Moran
 */
public interface Model {
	void generateMaze(String name, int x, int y, int z);
	void displayMaze(String name);
	void displayCrossSection(String name, String by, int index);
	void saveMaze(String name, String fileName);
	void loadMaze(String name, String fileName);
	void mazeSize(String name);
	void fileSize(String name);
	void solveMaze(String name,String algorithm);
	void displaySolution(String name);
	void exit();


	
}
