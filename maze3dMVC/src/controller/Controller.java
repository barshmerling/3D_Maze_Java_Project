package controller;

/**
 * interface Controller.
 * Controller acts on both model and view. It controls the
 * data flow into model object and updates the view whenever data changes. It
 * keeps view and model separate.
 * 
 * @author Moran & Bar
 */
public interface Controller {
	
	// methods from View
	void displayArr(String[] arr);
	void displayMessage(String msg);
	void displayByteArray(byte[] byteArr);

	// methods from Model
	void generateMaze(String name, int x, int y, int z);
	void displayMaze(String name);
	void displayCrossSection(String name, String by, int index);
	void saveMaze(String name, String fileName);
	void loadMaze(String name, String fileName);
	void mazeSize(String name);
	void fileSize(String name);
	void solveMaze(String name, String algorithm);
	void displaySolution(String name);
	void exit();
	
}
