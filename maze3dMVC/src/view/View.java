package view;

import java.util.HashMap;

import controller.Command;

/**
 * This interface define methods which need for the VIEW
 * @author Moran & Bar
 */
public interface View {
	void start();
	void printArr(String[] str);
	void printMessage(String message);
	void printByteArray(byte[] byteArr);
	void setCommand(HashMap<String,Command> commandMap);
	void exit();
}
