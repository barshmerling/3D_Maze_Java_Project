package controller;

/**
 * This class extends 'AbsCommand' and implements the 'doCommand' method.
 * 
 * @author Bar
 */
public class Generate extends AbsCommand {

	/**
	 * C'tor
	 * @param controller
	 */
	public Generate(Controller controller) {
		super(controller);
	}

	/**
	 * This method apply the method of generateMaze which create a maze
	 * according to Prim algorithm.
	 */
	@Override
	public void doCommand(String command) {
		//the command is: generate 3d maze nameMaze x y z 
		String[] tempArr = command.split(" ");
		if (tempArr.length > 5) {
			if ((tempArr[0].equals("3d")) || (tempArr[0].equals("3D"))) {
				if ((tempArr[1].equals("maze")) || (tempArr[1].equals("Maze"))) {
					int x, y, z;
					try {
						x = Integer.parseInt(tempArr[3]);
						y = Integer.parseInt(tempArr[4]);
						z = Integer.parseInt(tempArr[5]);
						controller.generateMaze(tempArr[2], x, y, z);
					} catch (NumberFormatException e) {
						controller.displayMessage("invalid convert number");
					}
				}
			}
		} else
			controller.displayMessage("Invalid length");
	}

}