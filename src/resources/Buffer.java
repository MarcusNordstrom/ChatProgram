package resources;

import java.util.LinkedList;

public class Buffer<T> {
	private LinkedList<T> buffer = new LinkedList<T>();
	
	public synchronized void put(T obj) {
		buffer.addLast(obj);
		notifyAll();
	}
	
	public synchronized T get() {
		while(buffer.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return buffer.removeFirst();
	}
	
	public int size() {
		return buffer.size();
	}
}
