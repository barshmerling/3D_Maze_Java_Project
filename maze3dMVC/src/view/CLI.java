package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import controller.Command;

/**
 * Command Line Interface this class gets a command from the user and send it to
 * the Controller
 * 
 * @param in
 *            - read the input stream from user
 * @param out
 *            - Prints formatted representations of objects to a text-output
 *            stream
 * @param commandMap
 *            - storage String and Command
 * @param threadPool
 *            - system variable to forcing the thread to stop for a while
 *            between the iterations of the commands
 * @author Bar
 */
public class CLI {

	// variables
	public BufferedReader in;
	private PrintWriter out;
	private HashMap<String, Command> commandMap;
	private ExecutorService threadPool;

	/**
	 * CTOR
	 * 
	 * @param in
	 * @param out
	 * @param cm
	 */
	public CLI(BufferedReader in, PrintWriter out, HashMap<String, Command> cm) {
		super();
		this.in = in;
		this.out = out;
		this.commandMap = cm;

	}

	/**
	 * setters and getters
	 */
	public BufferedReader getIn() {
		return in;
	}

	public void setIn(BufferedReader in) {
		this.in = in;
	}

	public PrintWriter getOut() {
		return out;
	}

	public void setOut(PrintWriter out) {
		this.out = out;
	}

	public HashMap<String, Command> getCommandMap() {
		return commandMap;
	}

	public void setCommandMap(HashMap<String, Command> commandMap) {
		this.commandMap = commandMap;
	}
	
	/**
	 * 
	 */
	public void start() {
		// create a thread using anonymous class
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					// ask for a command line
					out.println("please enter a command");
					out.flush();

					// gets a commend line
					String line = in.readLine();

					// new command interface
					Command command;

					while (!(line.equals("exit"))) {
						// reading the first word of the String in the hashMap
						// according to the user command line
						command = commandMap.get(line.split(" ")[0]);
						// if the user's string commend line is available in the
						// hash map
						if (command != null) {
							// checks if user's commend line is bigger then one
							// word
							if (line.split(" ").length > 1) {
								// checks substring
								command.doCommand(line.substring(line.indexOf(' ') + 1));
//								try {
//									threadPool.awaitTermination(3, TimeUnit.SECONDS);
//								} catch (InterruptedException ex) {
//									ex.printStackTrace();
//								}
							} else {
								out.println("Invalid Parameters");
								out.flush();
							}
						} else {
							out.println("your command is not exist");
							out.flush();
						}
						out.println("please enter a new command");
						out.flush();
						line = in.readLine();
					}
					commandMap.get("exit").doCommand("");
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}).start();
	}
}
