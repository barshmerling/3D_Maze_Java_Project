package presenter;
/**
 * This method apply the method of mazeSize witch display the size of the maze.
 */
public class MazeSize extends AbstractCommand {

	/**
	 * C'tor
	 * @param presenter
	 */
	public MazeSize(Presenter presenter) {
		super(presenter);
	}

	@Override
	public void doCommand(String command) {
		String[] tempArr = command.split(" ");
		if (tempArr.length != 2) {
			presenter.getView().display("Invalid parameters");
		} else {
			String mazeName = tempArr[1];
			presenter.getModel().mazeSize(mazeName);
		}
	}
}
