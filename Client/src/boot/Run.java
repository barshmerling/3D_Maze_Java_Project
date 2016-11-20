package boot;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import guiProperties.BasicWindow;
import guiProperties.MessageWindow;
import guiView.Maze3dGuiView;
import model.AbstractModel;
import model.Maze3dModel;
import presenter.Presenter;
import presenter.Properties;
import view.AbstractView;
import view.Maze3dView;

public class Run {

	public static void main(String[] args) {
		  
		Thread windowProperties = new Thread(new Runnable() {

			@Override
			public void run() {
				BasicWindow window = new MessageWindow("Properties Game", 405, 560);
				window.run();
			}
		});

		Thread windowMain = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					windowProperties.join();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

				Properties properties = new Properties();
				try {
					XMLDecoder dXml = new XMLDecoder(new BufferedInputStream(new FileInputStream("Properties.xml")));
					properties = (Properties) dXml.readObject();
					dXml.close();
				} catch (FileNotFoundException e) {
					System.out.println("file not found, default properties will be loaded");
					properties = new Properties();
					properties.defaultProperties();
				}

				AbstractModel model = new Maze3dModel();
				model.setProperties(properties);
				//AbstractView view = new Maze3dView(new BufferedReader(new InputStreamReader(System.in)),
				//		new PrintWriter(System.out));
				AbstractView view = new Maze3dGuiView("project", 700, 600, new BufferedReader(new InputStreamReader(System.in)),
						new PrintWriter(System.out));
				Presenter presenter = new Presenter(view, model);
				presenter.setProperties(properties);
				model.addObserver(presenter);
				view.addObserver(presenter);
				view.start();

			}

		});
		windowProperties.start();
		windowMain.run();
	}
}