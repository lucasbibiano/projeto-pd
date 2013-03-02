package utils;

import java.util.Iterator;

import system.core.Item;

public class ItemBufferManager {
	private static Buffer<Item> buffer;
	private static int expectedNum;
	
	static {
		buffer = new Buffer<Item>();
	}
	
	public static void setExpectedNumOfItens(int num) {
		expectedNum = num;
	}
	
	public static int getExpectedNumOfItens() {
		return expectedNum;
	}
	
	public static void add(Item data) {
		buffer.addToBuffer(data);
	}
	
	public static void clear() {
		buffer.clearBuffer();
	}
	
	public static Iterator<Item> getItens() {
		return buffer.data();
	}
	
	public static int getSize() {
		return buffer.size();
	}
}
