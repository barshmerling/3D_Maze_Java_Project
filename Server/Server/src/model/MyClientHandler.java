package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import algorithms.mazeGenerators.Maze3d;

/**
 * This class extends AbstractClientHandler and handle the commands which received from the client
 * @author Moran
 */
public class MyClientHandler extends AbstractClientHandler {

	/**
	 * This method handle the commands which received from the client
	 */
	@Override
	public void handleClient(InputStream inFromClient, OutputStream outToClient) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(inFromClient));
			ObjectOutputStream out = new ObjectOutputStream(outToClient);

			String line;
			
			// read commands from the client
			while (!(line = in.readLine()).endsWith("exit")) {
				if (line.equals("generate")) {
					CheckGenerate(in, out);
					out.writeObject("done");
					out.flush();
				}
				if (line.equals("get maze")) {
					checkGetMaze(in, out);
					out.writeObject("done");
					out.flush();
				}
				if (line.equals("solve")) {
					checkSolve(in, out);
					out.writeObject("done");
					out.flush();
				}
				if (line.equals("get solution")) {
					checkSolution(in, out);
					out.writeObject("done");
					out.flush();
				}
			}
			out.writeObject("good bye");
			out.flush();
			in.close();
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method start protocol with the client in order to create a maze3d
	 * @param inFromClient
	 * @param outToClient
	 */
	private void CheckGenerate(BufferedReader inFromClient, ObjectOutputStream outToClient) {
		String mazeName, temp;
		int x, y, z;
		try {
			// ask client for maze name
			outToClient.writeObject("What is the name of the maze?");
			outToClient.flush();
			mazeName = inFromClient.readLine().split(": ")[1];

			// ask client for number of floors
			outToClient.writeObject("What is the namber of floors");
			outToClient.flush();
			temp = inFromClient.readLine().split(": ")[1];
			x = Integer.parseInt(temp);

			// ask client for number of rows
			outToClient.writeObject("What is the namber of rows");
			outToClient.flush();
			temp = inFromClient.readLine().split(": ")[1];
			y = Integer.parseInt(temp);

			// ask client for number of columns
			outToClient.writeObject("What is the namber of columns");
			outToClient.flush();
			temp = inFromClient.readLine().split(": ")[1];
			z = Integer.parseInt(temp);

			controller.update("generate " + mazeName + " " + x + " " + y + " " + z);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * This method start protocol with the client in order to send him the maze
	 * @param inFromClient
	 * @param outToClient
	 */
	private void checkGetMaze(BufferedReader inFromClient, ObjectOutputStream outToClient ){
		String mazeName;
		try {
			// ask client for maze name
			outToClient.writeObject("What is the name of the maze?");
			outToClient.flush();
			mazeName = inFromClient.readLine();
			System.out.println(mazeName);

			Maze3d maze3d = controller.getMaze3d(mazeName);

			if (maze3d != null) {
				/*byte[] byteArr = maze3d.toByteArray();
				for (byte currentByte : byteArr) {
					outToClient.write(currentByte);
					outToClient.flush();
				}
				outToClient.write(-1);
				outToClient.flush();*/
				
				outToClient.writeObject(maze3d);
			} else {
				System.out.println("error to GetMaze");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method start protocol with the client in order to solve the maze
	 * @param inFromClient
	 * @param outToClient
	 */
	private void checkSolve(BufferedReader inFromClient, ObjectOutputStream outToClient) {
		String mazeName, algorithm;

		try {
			// ask client for maze name
			outToClient.writeObject("What is the name of the maze?");
			outToClient.flush();
			mazeName = inFromClient.readLine().split(": ")[1];
			System.out.println(mazeName);

			// ask client for algorithm
			outToClient.writeObject("What is the name of the algorithm?");
			outToClient.flush();
			algorithm = inFromClient.readLine().split(": ")[1];
			System.out.println(algorithm);

			controller.update("solve " + mazeName + " " + algorithm);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method start protocol with the client in order to send him the solution.
	 * @param inFromClient
	 * @param outToClient
	 */
	private void checkSolution(BufferedReader inFromClient, ObjectOutputStream outToClient) {
		String mazeName, solution, line;

		try {
			// ask client for maze name
			outToClient.writeObject("What is the name of the maze?");
			outToClient.flush();
			mazeName = inFromClient.readLine().split(": ")[1];
			System.out.println(mazeName);

			solution = controller.getSolution(mazeName);

			String[] solutionArr = solution.split(" ");
			if (solution != null) {
				// send for client the solution from the end position to the start
				for (int i = 0; i < solutionArr.length; i++) {
					line = solutionArr[i];
					outToClient.writeObject(line);
					outToClient.flush();
				}
				outToClient.writeObject("end");
				outToClient.flush();
			} else {
				System.out.println("error with the solution");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
