package controller;

import model.Model;
import view.View;

/**
 * This class implements 'Controller' interface. Holds View and Model as
 * parameters.
 * 
 * @author Moran
 */
public abstract class AbsController implements Controller {

	protected View view;
	protected Model model;

	/**
	 * C'tor
	 */
	public AbsController(View view, Model model) {
		super();
		this.view = view;
		this.model = model;
	}

	/**
	 * Setters & Getters
	 */
	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public abstract void generateMaze(String name, int x, int y, int z);
	public abstract void displayCrossSection(String name, String by, int index);
	public abstract void saveMaze(String name, String fileName);
	public abstract void loadMaze(String name, String fileName);
	public abstract void mazeSize(String name);
	public abstract void fileSize(String name);
	public abstract void solveMaze(String name, String algorithm);
	public abstract void displaySolution(String name);
	public abstract void exit();

	/**
	 * this method display a string array using view parameter
	 * 
	 * @param arr
	 */
	@Override
	public void displayArr(String[] arr) {
		this.view.printArr(arr);
	}

	/**
	 * this method display a String message using view parameter
	 * 
	 * @param msg
	 */
	@Override
	public void displayMessage(String msg) {
		this.view.printMessage(msg);
	}

	/**
	 * this method display a byte array using view parameter
	 * 
	 * @param byteArr
	 */
	@Override
	public void displayByteArray(byte[] byteArr) {
		this.view.printByteArray(byteArr);
	}

	/**
	 * this method display a maze using model parameter
	 * 
	 * @param name
	 */
}
