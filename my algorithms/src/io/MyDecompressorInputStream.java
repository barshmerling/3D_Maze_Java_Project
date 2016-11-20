package io;

import java.io.IOException;
import java.io.InputStream;

/**
 * class MyDecompressorInputStream extends InputStream 
 * @author Moran Hazom
 *
 */
public class MyDecompressorInputStream extends InputStream {

	InputStream  in;
	int counter;
	int lastByte;
	
	/**
	 * C'tor
	 * @param in
	 */
	public MyDecompressorInputStream(InputStream in) {
		super();
		this.in = in;
		this.counter=1;
	}
	/**
	 * Override read
	 * checks if counter is bigger then 1. if it is, returns the last byte.
	 * otherwise, reads the last byte and how many times we should write it
	 */
	@Override
	public int read() throws IOException {
		if(counter > 1){
			counter--;
			return this.lastByte;
		}
		this.lastByte= in.read();
		this.counter= in.read();
		return this.lastByte;
	}

}
