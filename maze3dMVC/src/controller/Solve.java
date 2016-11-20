package controller;

/**
 * This class extends 'AbsCommand' and implements the 'doCommand' method.
 * 
 * @author Bar
 */
public class Solve extends AbsCommand {

	public Solve(Controller controller) {
		super(controller);
	}

	/**
	 * This method apply the method of solveMaze witch solve the maze by an algorithm
	 */
	@Override
	public void doCommand(String command) {
		String[] tempArr = command.split(" ");
		if (tempArr.length != 2) {
			controller.displayMessage("Invalid parameters");
		} else {
			String mazeName = tempArr[0];
			String algorithmsName = tempArr[1];
			controller.solveMaze(mazeName, algorithmsName);
		}
	}
}
