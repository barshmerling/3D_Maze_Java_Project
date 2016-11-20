package presenter;
/**
 * this class define the optional moves by switch/case
 * @author BAR
 */
public class Moves extends AbstractCommand {

	/**
	 * C'tor
	 * @param presenter
	 */
	public Moves(Presenter presenter) {
		super(presenter);
	}

	@Override
	public void doCommand(String command) {
		switch (command){
		case "up":
			presenter.getModel().moveUp();
			break;
		case "down":
			presenter.getModel().moveDown();
			break;
		case "right":
			presenter.getModel().moveRight();
			break;
		case "left":
			presenter.getModel().moveLeft();
			break;
		case "forward":
			presenter.getModel().moveForward();
			break;
		case "backward":
			presenter.getModel().moveBackward();
			break;
		default: 
			presenter.getView().display("move does not exist!");
			break;
		}
	}

}
