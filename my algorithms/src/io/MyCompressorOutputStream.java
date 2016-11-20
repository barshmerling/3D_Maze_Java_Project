package io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * the class MyCompressorOutputStream extends OutputStream
 * @param counter count the length of the sequence 0/1
 * @param previous indicate the previous value in the array: 0/1
 * @author Moran Hazom
 */
public class MyCompressorOutputStream extends OutputStream {

	private OutputStream out;
	int counter;
	int previous;

	/**
	 * C'tor
	 */
	public MyCompressorOutputStream(OutputStream out) {
		super();
		this.out = out;
		this.counter = 0;
		this.previous = -1;
	}
	/**
	 * Override write -
	 * gets an array of bytes 
	 */
	@Override
	public void write(byte[] b) throws IOException{
		super.write(b);
		write(previous);
		write(counter);
	}
	
	/**
	 * Override write -
	 * gets an int value and checks if this value equals to the previous one. 
	 * if they are equal, the method adds 1 to the counter.
	 * else it prints how many same parameters we had before, and starts the counting 
	 * from the beginning.
	 * 
	 */
	@Override
	public void write(int b) throws IOException {
		if (this.previous != b) {
			this.previous = b;
			if (counter != 0) {
				out.write(counter);
				counter = 1;
			} else {
				if (counter == 255) {
					out.write(b);
					out.write(counter);
					counter = 1;
				} else
					counter++;
			}
			out.write(b);
		} else {
			if (counter == 255) {
				out.write(b);
				out.write(counter);
				counter = 1;
			} else
				counter++;
		}
	}

}
