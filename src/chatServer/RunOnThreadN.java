package chatServer;

import java.nio.Buffer;
import java.util.LinkedList;

import javafx.concurrent.Worker;

public class RunOnThreadN {
	private Buffer<Runnable> buffer = new Buffer<Runnable>();
	private LinkedList<Worker> workers;
	
	public RunOnThreadN() {
	}
	public synchronized void start() {	
	}
	public synchronized void stop() {	
	}
	public synchronized void execute(Runnable runnable) {
	}
	
	
	
	
	private class Buffer<T>{
		private LinkedList<Runnable> buffer;
		
		public synchronized void put(T obj) {
		}
		public synchronized T get() {
			return null;
		}
	}
	
	
	
	private class Worker extends Thread {
		
		public void run() {
		}
	}
}
