package controller;

import java.io.File;

/**
 * This class extends 'AbsCommand' and implements the 'doCommand' method.
 * 
 * @author Moran
 */
public class Dir extends AbsCommand {

	/**
	 * C'tor
	 * @param controller
	 */
	public Dir(Controller controller) {
		super(controller);
	}
	
	/**
	 * This method apply the method of displayArr which display all files and
	 * folders in a specific path.
	 */
	@Override
	public void doCommand(String command) {
		if (command == null) {
			controller.displayMessage("Invalid input");
		} else {
			try {
				File file = new File(command);
				//the command 'file.list()' returns an array of strings
				if (file.list().length == 0)
					controller.displayMessage("The file is not exsist");
				else
					controller.displayArr(file.list());
			} catch (NullPointerException e) {
				controller.displayMessage("Invalid path");
			}
		}
	}
}
