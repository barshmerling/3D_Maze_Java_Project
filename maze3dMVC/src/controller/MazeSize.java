package controller;

/**
 * This class extends 'AbsCommand' and implements the 'doCommand' method.
 * 
 * @author Bar
 */
public class MazeSize extends AbsCommand {

	/**
	 * C'tor	
	 * @param controller
	 */
	public MazeSize(Controller controller) {
		super(controller);
	}

	/**
	 * This method apply the method of mazeSize witch display the size of the maze.
	 */
	@Override
	public void doCommand(String command) {
		String[] tempArr = command.split(" ");
		if (tempArr.length!=2){
			controller.displayMessage("Invalid input");
		}else{
			String mazeName = tempArr[1];
			controller.mazeSize(mazeName);
			
		}
	}
}
