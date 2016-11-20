package controller;

/**
 * This class extends 'AbsCommand' and implements the 'doCommand' method.
 * 
 * @author Bar
 */
public class FileSize extends AbsCommand {

	/**
	 * C'tor	
	 * @param controller
	 */
	public FileSize(Controller controller) {
		super(controller);
	}

	/**
	 * This method apply the method of fileSize which display the size of the
	 * maze in the file.
	 */
	@Override
	public void doCommand(String command) {
		String[] tempArr = command.split(" ");
		if (tempArr.length!=2){
			controller.displayMessage("Invalid input");
		}else{
			String mazeName = tempArr[1];
			controller.fileSize(mazeName);
			
		}
	}

}
