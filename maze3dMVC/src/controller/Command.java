package controller;

/**
 * this interface holds 'doCommand' method which implements differently in each
 * class that extends 'AbsCommand'
 * 
 * @author Moran
 */
public interface Command {

	public void doCommand(String command);

}
