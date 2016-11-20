package controller;

/**
 * This class extends 'AbsCommand' and implements the 'doCommand' method.
 * 
 * @author Bar
 */
public class Display extends AbsCommand {
	
	/**
	 * C'tor
	 * @param controller
	 */
	public Display(Controller controller) {
		super(controller);
	}
	
	/**
	 * This method apply the relevant application according to command:
	 * display cross section by / display solution / display nameMaze.
	 */
	@Override
	public void doCommand(String command) {
		String[] tempArr = command.split(" ");
		String mazeName;
		if(tempArr.length>5){
			if(tempArr[0].equals("cross")){// if the command is: display cross section by x/y/z index nameMaze
				int i=0;
				try{
					i=Integer.parseInt(tempArr[4]);
				}catch (NumberFormatException e){
					controller.displayMessage("invalid input");
				}
				String by = tempArr[3];
				mazeName = tempArr[5];
				controller.displayCrossSection(mazeName, by, i);
			}
		}else if(tempArr.length>1){
			if(tempArr[0].equals("solution")){// if the command is: display solution nameMaze
				mazeName=tempArr[1];
				controller.displaySolution(mazeName);
			}
		}else{// if the command is: display mazeName
			mazeName=tempArr[0];
			controller.displayMaze(mazeName);		
		}
	}
}