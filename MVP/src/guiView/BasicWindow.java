package guiView;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * This class implements Runnbale and  open a basic window.
 * @param display 
 * @param shell 
 * @author Moran
 */
public abstract class BasicWindow implements Runnable {

	protected Display display;
	protected Shell shell;
	
	/**
	 * C'tor
	 */
	public BasicWindow(String title, int width, int height){
		display = new Display();
		shell = new Shell(display);
		shell.setSize(width, height);
		shell.setText(title);
	}
	
	/**
	 * this is an an abstract methods which implements each widgets in a different way
	 */
	public abstract void initWidgets();
	
	/**
	 * This method Override the run method of Runnable and call to initWidgets method
	 */
	@Override
	public void run() {
		initWidgets();
		shell.open();

		// main event loop
		while (!shell.isDisposed()) { // while window isn't closed
			// 1. read events, put then in a queue.
			// 2. dispatch the assigned listener
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		} // shell is disposed
		display.dispose(); // dispose OS components
	}
}
