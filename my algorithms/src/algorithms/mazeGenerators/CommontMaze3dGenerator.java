package algorithms.mazeGenerators;

public abstract class CommontMaze3dGenerator implements Maze3dGenerator {

	@Override
	public abstract Maze3d generate(int x, int y, int z) throws IndexOutOfBoundsException;

	@Override
	public String measureAlgorithmTime(int x, int y, int z) {
		long StartTime=System.currentTimeMillis();
		generate(x, y, z);
		long finishTime=System.currentTimeMillis();
		return Long.toString(finishTime-StartTime);
	}

}
