package presenter;

/**
 * This interface define the 'doCommand' method.
 * Each class which implements this interface -  implements differently the methods of 'doCommand' 
 * @author BAR
 */
public interface Command {
	
	void doCommand(String command);

}
