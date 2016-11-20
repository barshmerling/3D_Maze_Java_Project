package model;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import algorithms.search.State;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;

/**
 * this class extends 'AbstractModel' class and define the Maze3d Model.
 * @param hashSolution - mapping between Maze3d and Solution.
 * @param hashPosition - mapping between String and Position.
 * @author BAR
 *
 */
public class Maze3dModel extends AbstractModel {

	private HashMap<Maze3d, Solution<Position>> hashSolution;
	private HashMap<String, Position> hashPosition;

	/**
	 * C'tor
	 */
	public Maze3dModel(){
		super();
		this.hashSolution = new HashMap<Maze3d, Solution<Position>>();
		this.hashPosition = new HashMap<String, Position>();
		threadPool = Executors.newFixedThreadPool(properties.getNumOfThreads());
		// try {
		// loadFromZip();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}

	/**
	 * getters & setters
	 */
	public HashMap<Maze3d, Solution<Position>> getHashSolution() {
		return hashSolution;
	}

	public void setHashSolution(HashMap<Maze3d, Solution<Position>> hashSolution) {
		this.hashSolution = hashSolution;
	}

	public HashMap<String, Position> getHashPosition() {
		return hashPosition;
	}

	public void setHashPosition(HashMap<String, Position> hashPosition) {
		this.hashPosition = hashPosition;
	}

	/**
	 * This method get a maze3d from the server.
	 * @param mazeName
	 */
	@Override
	public void generate(String mazeName) {
		String name = properties.getMazeName();
		int x = properties.getX();
		int y = properties.getY();
		int z = properties.getZ();
		String line;
		try {
			// open a new socket to connect the server. the ip and port belongs to the server
			Socket server = new Socket("127.0.0.1", 5400);
			System.out.println("connect to server");
			
			// PrintWriter for writing to the server, BufferedReader for reading from the server
			PrintWriter outToServer = new PrintWriter(server.getOutputStream());
			ObjectInputStream inFromServer = new ObjectInputStream(server.getInputStream());
			
			// ask from the server to generate a maze
			outToServer.println("generate");
			outToServer.flush();
			
			// read the answer from the server and write the server the name of the maze
			line = (String)inFromServer.readObject();
			System.out.println(line);
			outToServer.println("The name of the maze is: " + name);
			outToServer.flush();
			System.out.println(name);

			// write the server number of floors
			line = (String)inFromServer.readObject();
			System.out.println(line);
			outToServer.println("The number of floors is: " + x);
			outToServer.flush();
			System.out.println("sizeX: " + x);

			// write the server number of rows
			line = (String)inFromServer.readObject();
			System.out.println(line);
			outToServer.println("The number of rows is: " + y);
			outToServer.flush();
			System.out.println("sizeY: " + y);

			// write the server number of columns
			line = (String)inFromServer.readObject();
			System.out.println(line);
			outToServer.println("The number of columns is: " + z);
			outToServer.flush();
			System.out.println("sizeZ: " + z);

			line = (String)inFromServer.readObject();
			System.out.println(line);
			outToServer.println("exit");
			outToServer.flush();
			
			Maze3d maze = getMaze3d(name);//
			
			// close socket
			inFromServer.close();
			outToServer.close();
			server.close();

			setNotify("mazeIsReady", maze);

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method get a maze3d from the server.
	 */
	@Override
	public Maze3d getMaze3d(String mazeName) {
		Maze3d maze3d = hashMaze.get(mazeName);
		if(maze3d != null){
			return maze3d;
		}
		
		String line = null;

		try {
			// open a socket
			Socket server = new Socket("127.0.0.1", 5400);
			System.out.println("connect to server");
			
			// PrintWriter for writing to the server, BufferedReader for reading from the server
			PrintWriter outToServer = new PrintWriter(server.getOutputStream());
			ObjectInputStream inFromServer = new ObjectInputStream(server.getInputStream());
			
			// ask from server for a maze
			outToServer.println("get maze");
			outToServer.flush(); 
			
			// write to the server the name of the maze
			line =  (String)inFromServer.readObject();
			System.out.println(line);
			outToServer.println(mazeName);
			outToServer.flush();
			System.out.println(mazeName);
			
			// calculate the size of the maze
			/*int size = ((properties.getX()*4) * (properties.getY()*4) * (properties.getZ()*4)) + 36;
			byte [] byteArr = new byte [5000];
			byte currentByte;
			int i=0;
			
			// copy byte array, byte after byte
			while((currentByte = (byte) inFromServer.read()) != -1){
				byteArr[i++] = currentByte;
			}
			
			// create maze3d
			maze3d = new Maze3d(byteArr);*/
			
			maze3d = (Maze3d) inFromServer.readObject();
			
			hashMaze.put(mazeName,maze3d);
			hashPosition.put(mazeName, maze3d.getStartPosition());
			// Returns true if this map maps the key to the specified value.
			System.out.println(hashPosition.containsValue(maze3d.getStartPosition()));
			
			System.out.println((String)inFromServer.readObject());
			outToServer.println("exit");
			outToServer.flush();
			
			// close socket
			inFromServer.close();
			outToServer.close();
			server.close();
			return maze3d;
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * this method cross the section of the maze by X/Y/Z
	 */
	@Override
	public void getCrossSectionBy(String mazeName, String by, int index) {
		String name = properties.getMazeName();
		Maze3d maze = hashMaze.get(name);
		try {
			switch (by) {
			case "X":
			case "x":
				setNotify("displayCrossSectionBy", maze.getCrossSectionByX(index));
				break;
			case "Y":
			case "y":
				setNotify("displayCrossSectionBy", maze.getCrossSectionByY(index));
				break;
			case "Z":
			case "z":
				setNotify("displayCrossSectionBy", maze.getCrossSectionByZ(index));
				break;
			default:
				setChanged();
				notifyObservers("Invalid parameters");
				return;
			}
		} catch (IndexOutOfBoundsException e) {
			setChanged();
			notifyObservers("Invalid index");
			return;
		}
	}

	/**
	 * this method save the maze into a file
	 */
	@Override
	public void saveMaze(String fileName) {
		String name = properties.getMazeName();
		Maze3d maze = hashMaze.get(name);
		try{
			// save the maze by CompressorOutputStream
			OutputStream out = new MyCompressorOutputStream(new FileOutputStream(fileName));
			out.write(maze.toByteArray());
			out.close();
			setNotify("saveMaze", fileName);
		} catch (FileNotFoundException e) { // if the file does not exist
			setNotify("Invalid file", fileName);
		} catch (IOException e) { // if the compressor failed
			setNotify("Invalid compress", name);
		}
	}

	/**
	 * this method load a maze from file
	 */
	@Override
	public void loadMaze(String fileName, String mazeName) {
		properties.setMazeName(mazeName);
		try {
			//save the maze by DeCompressorOutputStream
			InputStream in = new MyDecompressorInputStream(new FileInputStream(fileName));
			byte[] bArr = new byte[50000];
			int numByte = in.read(bArr); // get the number of the Bytes in the file 
			in.read(bArr); // read the data from inputStream to bArr
			in.close();
			// create new ByteArray according to numByte and copy Byte by Byte
			byte[] newByteArray = new byte[numByte];
			for (int i = 0; i < newByteArray.length; i++) {
				newByteArray[i] = bArr[i];
			}
			// create maze with Byte C'tor and put it in the hashMaps
			Maze3d maze = new Maze3d(bArr);
			hashMaze.put(mazeName, maze);
			hashPosition.put(mazeName, maze.getStartPosition());
			setNotify("loadMaze", getMaze3d(mazeName));

		} catch (FileNotFoundException e) { // if the file does not exist
			setNotify("Invalid file", fileName);

		} catch (IOException e) {  // if the maze does not exist
			setNotify("Invalid maze", mazeName);
		}
	}

	/**
	 * This method display the size of the maze in the memory
	 */
	@Override
	public void mazeSize(String mazeName) {
		Maze3d maze3d = hashMaze.get(mazeName);
		if(maze3d == null){
			setNotify("maze does not exist", mazeName);
		}else{
			try {
				int size = maze3d.toByteArray().length;
				setNotify("mazeSize" , size);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * This method display the size of a maze in the file
	 * @param mazeName
	 */
	@Override
	public void fileSize(String mazeName) {
		Maze3d maze3d = hashMaze.get(mazeName);
		if(maze3d == null){
			setNotify("maze does not exist", mazeName);
		}else{
			ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
			MyCompressorOutputStream compressorOut = new MyCompressorOutputStream(byteArrayOut);
			try {
				compressorOut.write(maze3d.toByteArray());
				setNotify("fileSize", byteArrayOut.size());
			} catch (IOException e) { // if the compressor was failed
				setNotify("Invalid compress", mazeName);
			}
		}
	}

	/**
	 * this method ask from the server to solve the maze by one of the offered algorithm.
	 * @param mazeName
	 */
	@Override
	public void solveMaze(String mazeName) {
		String line = null;
		String name = properties.getMazeName();
		Maze3d maze = getMaze3d(name);
		String algo = properties.getAlgo();
		try {
			// open a socket
			Socket server = new Socket("127.0.0.1", 5400);
			System.out.println("connect to server");
			
			// PrintWriter for writing to the server, BufferedReader for reading from the server
			PrintWriter outToServer = new PrintWriter(server.getOutputStream());
			ObjectInputStream inFromServer = new ObjectInputStream(server.getInputStream());
			
			// ask the server to solve a maze
			outToServer.println("solve");
			outToServer.flush();

			// write to server the name of the maze 
			line = (String)inFromServer.readObject();
			System.out.println(line);
			outToServer.println("The name of the maze is: " + name);
			outToServer.flush();
			System.out.println(name);

			// write to the server an algorithm for solve the maze
			line = (String)inFromServer.readObject();
			System.out.println(line);
			outToServer.println("The chosen algorithm is: " + algo);
			outToServer.flush();
			System.out.println(algo);

			System.out.println((String)inFromServer.readObject());
			outToServer.println("exit");
			outToServer.flush();

			Solution<Position> solution = this.getMazeSolution(name);
			
			//close socket
			inFromServer.close();
			outToServer.close();
			server.close();

			setNotify("solutionIsReady", name);

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * this method return the Solution of the maze from the server
	 * @param mazeName
	 * @return solution
	 */
	@Override
	public Solution<Position> getMazeSolution(String mazeName) {
		String line = null;
		String[] tempArr;
		int x, y, z;
		try {
			// open socket
			Socket server = new Socket("127.0.0.1", 5400);
			System.out.println("connect to server");
			
			// PrintWriter for writing to the server, BufferedReader for reading from the server
			PrintWriter outToServer = new PrintWriter(server.getOutputStream());
			ObjectInputStream inFromServer = new ObjectInputStream(server.getInputStream());
			
			// ask the server for solution
			outToServer.println("get solution");
			outToServer.flush();

			//get line from the server and print it to the screen
			line = (String)inFromServer.readObject();
			System.out.println(line);
			outToServer.println("The name of the maze is: " + mazeName);
			outToServer.flush();

			//get solution from the server
			Solution<Position> solution = new Solution<Position>();
			while (!(line = (String)inFromServer.readObject()).equals("end")) {
				tempArr = line.split(",");
				x = Integer.parseInt(tempArr[0].substring(1));
				y = Integer.parseInt(tempArr[1]);
				z = Integer.parseInt(tempArr[2].substring(0, 1));
				// insert the position to SolutionArrayList
				State<Position> positon = new State<Position>(new Position(x, y, z));
				solution.addStateToSolutionArrayList(positon);
			}

			System.out.println((String)inFromServer.readObject());
			outToServer.println("exit");
			outToServer.flush();

			//close socket
			inFromServer.close();
			outToServer.close();
			server.close();
			return solution;
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * This method save the maze to a zip file
	 */
	@Override
	public void saveToZip() {
		try {
			// save the maze to a ZIP file using GZIPOutputstream
			ObjectOutputStream mazeOut = new ObjectOutputStream(
					new GZIPOutputStream(new FileOutputStream("fileMazeZip.zip")));
			//write the two hashMaps (with all the info) to the file 
			mazeOut.writeObject(hashMaze);
			mazeOut.writeObject(hashSolution);
			mazeOut.flush();
			mazeOut.close();
			setNotify("saveZip", "fileMazeZip.zip");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * this method load a maze from a zip file
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void loadFromZip() {
		try {
			FileInputStream mazeFile = new FileInputStream("fileMazeZip.zip");
			ObjectInputStream mazeIn = new ObjectInputStream(new GZIPInputStream(mazeFile));
			hashMaze = (HashMap<String , Maze3d>) mazeIn.readObject();
			hashSolution = (HashMap<Maze3d , Solution<Position>>) mazeIn.readObject();
			mazeIn.close();
			setNotify("loadZip","fileMazeZip.zip");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method save and close the project orderly.
	 */
	@Override
	public void exit() {
		saveToZip();
		threadPool.shutdownNow();
		try {
			while (!(threadPool.awaitTermination(10, TimeUnit.SECONDS)))
				;
			setChanged();
			notifyObservers("exit");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * this method moving UP according to the position in the hashPosition
	 */
	@Override
	public void moveUp() {
		String name = properties.getMazeName();
		Maze3d maze = hashMaze.get(name);
		Position position = hashPosition.get(name);
		String[] moves = maze.getPossibleMoves(position);
		for (String possibleMove : moves){
			if (possibleMove == "Up"){
				position.setX(position.getX()+1);
				hashPosition.put(name, position);
				setNotify("move", name);
			}
		}
	}
	
	/**
	 * this method moving DOWN according to the position in the hashPosition
	 */
	@Override
	public void moveDown() {
		String name = properties.getMazeName();
		Maze3d maze = hashMaze.get(name);
		Position position = hashPosition.get(name);
		String[] moves = maze.getPossibleMoves(position);
		for (String possibleMove : moves){
			if (possibleMove == "Down"){
				position.setX(position.getX()-1);
				hashPosition.put(name, position);
				setNotify("move", name);
			}
		}
	}
	
	/**
	 * this method moving RIGHT according to the position in the hashPosition
	 */
	@Override
	public void moveRight() {
		String name = properties.getMazeName();
		Maze3d maze = hashMaze.get(name);
		Position position = hashPosition.get(name);
		String[] moves = maze.getPossibleMoves(position);
		for (String possibleMove : moves){
			if (possibleMove == "Right"){
				position.setY(position.getY()+1);
				hashPosition.put(name, position);
				setNotify("move", name);
			}
		}
	}
	
	/**
	 * this method moving LEFT according to the position in the hashPosition
	 */
	@Override
	public void moveLeft() {
		String name = properties.getMazeName();
		Maze3d maze = hashMaze.get(name);
		Position position = hashPosition.get(name);
		String[] moves = maze.getPossibleMoves(position);
		for (String possibleMove : moves){
			if (possibleMove == "Left"){
				position.setY(position.getY()-1);
				hashPosition.put(name, position);
				setNotify("move", name);
			}
		}
	}
	
	/**
	 * this method moving FORWARD according to the position in the hashPosition
	 */
	@Override
	public void moveForward() {
		String name = properties.getMazeName();
		Maze3d maze = hashMaze.get(name);
		Position position = hashPosition.get(name);
		String[] moves = maze.getPossibleMoves(position);
		for (String possibleMove : moves){
			if (possibleMove == "Forward"){
				position.setZ(position.getZ()+1);
				hashPosition.put(name, position);
				setNotify("move", name);
			}
		}
	}
	
	/**
	 * this method moving BACKWARD according to the position in the hashPosition
	 */
	@Override
	public void moveBackward() {
		String name = properties.getMazeName();
		Maze3d maze = hashMaze.get(name);
		Position position = hashPosition.get(name);
		String[] moves = maze.getPossibleMoves(position);
		for (String possibleMove : moves){
			if (possibleMove == "Backward"){
				position.setZ(position.getZ()-1);
				hashPosition.put(name, position);
				setNotify("move", name);
			}
		}
	}

	/**
	 * this method gets a maze name and return the current position 
	 */
	@Override
	public Position getPositionFromHash(String mazeName) {
		return hashPosition.get(mazeName);
	}

	
}
