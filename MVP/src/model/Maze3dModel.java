package model;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import algorithms.demo.MazeSearch;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.Astar;
import algorithms.search.Bfs;
import algorithms.search.MazeAirDistance;
import algorithms.search.MazeManhattanDistance;
import algorithms.search.Searchable;
import algorithms.search.Searcher;
import algorithms.search.Solution;
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
	 * this method create a future maze3d in a different thread
	 */
	@Override
	public void generate(String mazeName) {
		String name = properties.getMazeName();
		int x = properties.getX();
		int y = properties.getY();
		int z = properties.getZ();
		Future<Maze3d> futureCallMaze = threadPool.submit(new Callable<Maze3d>() {
			// submit should return value of maze3d now. therefore the using of Future.
			@Override
			public Maze3d call() throws Exception {
				Maze3d maze = new MyMaze3dGenerator().generate(x, y, z);
				return maze;
			}
		});
		try {
			// futureCallMaze.get() Waits if necessary for maze3d to complete,
			// and then retrieves it.
			hashMaze.put(name, futureCallMaze.get());
			hashPosition.put(name, futureCallMaze.get().getStartPosition());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		setNotify("mazeIsReady", getMaze3d(name));
	}

	/**
	 * this method return maze by name from the hashMaze
	 */
	@Override
	public Maze3d getMaze3d(String mazeName) {
		Maze3d maze3d=hashMaze.get(mazeName);
		return maze3d;
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
	 * this method solve the maze it gets by one of the offered algorithm.
	 * @param mazeName
	 */
	@Override
	public void solveMaze(String mazeName) {
		String name = properties.getMazeName();
		String algo = properties.getAlgo();
		Maze3d maze = hashMaze.get(name);
		if (maze != null) {
			if (!(hashSolution.containsKey(maze))) {
				Future<Solution<Position>> futureCallSolution = threadPool.submit(new Callable<Solution<Position>>() {

					@Override
					public Solution<Position> call() throws Exception {
						Searcher<Position> myAlgorithm;
						Searchable<Position> mazeSearchable = new MazeSearch(maze);
						Solution<Position> solution = new Solution<Position>();

						switch (algo) {
						case "BFS":
							myAlgorithm = new Bfs<Position>();
							solution = myAlgorithm.search(mazeSearchable);
							break;
						case "Astar - AirDistance":
							myAlgorithm = new Astar<Position>(new MazeAirDistance());
							solution = myAlgorithm.search(mazeSearchable);
							break;
						case "Astar - ManhattanDistance":
							myAlgorithm = new Astar<Position>(new MazeManhattanDistance());
							solution = myAlgorithm.search(mazeSearchable);
							break;
						default:
							setChanged();
							notifyObservers("Invalid algorithm");
							return null;
						}
						return solution;
					}
				});
				try {
					hashSolution.put(maze, futureCallSolution.get());
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		}
		setNotify("solutionIsReady", name);
	}
	
	/**
	 * this method gets the Solution of maze by name it gets
	 * @param mazeName
	 * @return solution
	 */
	@Override
	public Solution<Position> getMazeSolution(String mazeName) {
		String name = properties.getMazeName();
		Maze3d maze = hashMaze.get(name);
		if (maze == null){
			setNotify("maze does not exist", name);
			return null;
		}else{
			Solution<Position> solution = hashSolution.get(maze);
			return solution;
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
			// write the two hashMaps (with all the info) to the file
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
	 * this method load the maze details from a zip file
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
