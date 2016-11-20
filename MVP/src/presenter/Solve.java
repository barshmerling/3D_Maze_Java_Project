package presenter;
/**
 * This class apply the method of solveMaze witch solve the maze by an algorithm
 */
public class Solve extends AbstractCommand {

	/**
	 * C'tor
	 * @param presenter
	 */
	public Solve(Presenter presenter) {
		super(presenter);
	}

	@Override
	public void doCommand(String command) {
		String[] tempArr = command.split(" ");
		if (tempArr.length != 1){
			presenter.getView().display("Invalid Input");
	}else{
		String name = tempArr[0];
		presenter.getModel().solveMaze(name);
		}
	}
}
