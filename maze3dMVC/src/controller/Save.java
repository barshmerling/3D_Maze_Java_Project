package controller;

/**
 * This class extends 'AbsCommand' and implements the 'doCommand' method.
 * 
 * @author Bar
 */
public class Save extends AbsCommand {

	/**
	 * C'tor
	 * @param controller
	 */
	public Save(Controller controller) {
		super(controller);
	}

	/**
	 * This method apply the method of saveMaze witch save the maze into a file.
	 */
	@Override
	public void doCommand(String command) {
		String[] tempArr= command.split(" ");
		if(tempArr.length!=2){
			 controller.displayMessage("Inavlid input");
		}else{
			String mazeName = tempArr[0];
			String fileName = tempArr[1];
			controller.saveMaze(mazeName, fileName);
		}
	}

}
