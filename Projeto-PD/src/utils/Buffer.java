package utils;

import java.util.ArrayList;
import java.util.Iterator;

public class Buffer<T> {
	private ArrayList<T> buffer;
	
	public Buffer() {
		buffer = new ArrayList<T>();
	}
	
	public void addToBuffer(T data) {
		buffer.add(data);
	}
	
	public void clearBuffer() {
		buffer.clear();
	}
	
	public int size() {
		return buffer.size();
	}
	
	public Iterator<T> data() {
		return buffer.iterator();
	}
}
