package boot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import controller.Controller;
import controller.PropertiesServer;
import model.ClientHandler;
import model.Model;
import model.MyClientHandler;
import model.MyModelServer;
import view.MyViewServer;
import view.View;

public class RunServer {

	public static void main(String[] args) {
		PropertiesServer propertiesServer = new PropertiesServer();
		ClientHandler clientHandler = new MyClientHandler();
		Model model = new MyModelServer();
		Controller controller = new Controller(clientHandler, propertiesServer);
		View view = new MyViewServer(controller);
		
		controller.setView(view);
		controller.setModel(model);
		controller.setClientHandler(clientHandler);
		
		view.start();
		
		BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
		try {
			while(!(in.readLine()).equals("close the server"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		view.close();
	}
}
