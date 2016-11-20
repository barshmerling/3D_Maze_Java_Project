package presenter;
/**
 * This class apply the relevant application according to command:
 * display cross section by / display solution / display nameMaze.
 */
public class Display extends AbstractCommand {

	/**
	 * C'tor
	 * @param presenter
	 */
	public Display(Presenter presenter) {
		super(presenter);
	}

	@Override
	public void doCommand(String command) {
		String[] tempArr = command.split(" ");
		String name;
		if (tempArr.length > 5) {
			if (tempArr[0].equals("cross")) { // if the command is: display cross section by x/y/z index nameMaze
				int index = 0;
				try {
					index = Integer.parseInt(tempArr[4]);
				} catch (NumberFormatException e) {
					presenter.getView().display("Invalid parameters");
				}
				String by = tempArr[3];
				name = tempArr[5];
				presenter.getModel().getCrossSectionBy(name, by, index);
			}
		} else if (tempArr.length > 1) {
			if (tempArr[0].equals("solution")) { // if the command is: display solution nameMaze
				name = tempArr[1];
				presenter.getView().displaySolution(presenter.getModel().getMazeSolution(name));
			}
		} else {// if the command is: display mazeName
			name = tempArr[0];
			//display the maze
			presenter.getView().displayMaze(presenter.getModel().getMaze3d(name));
			//display the Start Solution
			presenter.getView().displayPosition(presenter.getModel().getMaze3d(name).getStartPosition());
		}
	}
}
