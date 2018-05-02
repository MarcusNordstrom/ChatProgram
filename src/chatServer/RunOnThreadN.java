package chatServer;

import java.util.LinkedList;


public class RunOnThreadN {
	private Buffer<Runnable> buffer = new Buffer<Runnable>();
	private LinkedList<Worker> workers;
	private int nbrOfThreads;

	public RunOnThreadN(int nbrOfThreads) {
		this.nbrOfThreads = nbrOfThreads;
	}

	/*
	 * Threds are instansiated and started. Instantialvariable contains the number of threads.
	 */
	public synchronized void start() {
		Worker worker;
		if(workers==null) {
			workers = new LinkedList<Worker>();	
			for(int i=0; i<nbrOfThreads; i++) {
				worker = new Worker();
				worker.start();
				workers.add(worker);
			}
		}
	}

	/*
	 * Threads have to be exited - This kan be done immediately (Possible objects in the buffer will not be executed).
	 */
	public synchronized void stop() {	
		if(workers!=null) {
			buffer.clear();
			for(Worker worker : workers) {
				worker.interrupt();
			}
			workers = null;
		}

	}

	public synchronized void execute(Runnable runnable) {
		buffer.put(runnable);
	}

	private class Buffer<T>{
		private LinkedList<T> buffer = new LinkedList<T>();
		
		/**
		 * adds an object to the buffer
		 * @param obj
		 */
		public synchronized void put(T obj) {
			buffer.addLast(obj);
			notifyAll();
		}

		/**
		 * returns the first element in the buffer then removes it.
		 */
		public synchronized T get() throws InterruptedException {
			while(buffer.isEmpty()) {
				wait();
			}
			return buffer.removeFirst();
		}

		/**
		 * clears the buffer
		 */
		public synchronized void clear() {
			buffer.clear();
		}
	}


	/**
	 * Gets the buffer and runs it when a thread is not interrupted.
	 * @author JakeODonnell
	 *
	 */
	private class Worker extends Thread {
		public void run() {
			while(!Thread.interrupted()) {
				try {
					buffer.get().run();
				} catch(InterruptedException e) {
					System.out.println(e);
					break;
				}
			}
		}
	}
}