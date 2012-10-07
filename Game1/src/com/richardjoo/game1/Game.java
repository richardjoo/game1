package com.richardjoo.game1;

public class Game implements Runnable {
	public static int width = 300;
	public static int height = ( width/16 ) * 9;  // this will make it to 16:9 screen ratio
	public static int scale = 3; // how much our game would scaled up to. so, if it is 300 width, it would go up to 900
	
	// creating thread object so we can do multiple things simultaneously
	private Thread thread;
	
	// we will create a new object to manipulate the thread
	// synchronized here so we can prevent thread interferences and memory consistency errors
	// without this, wrong thread management can lock down the system as well.
	public synchronized void start() {
		//so, this here is same as new Game()
		thread = new Thread(this, "Display");
		thread.start();
	}
	
	// we need stop() to stop the thread too.
	public synchronized void stop() {
		try {
			// join is used where the main thread to wait until all the threads are completed
			// The join() method of a Thread instance can be used to "join" the start of a thread's execution to the end of another thread's execution so that a thread will not start running until another thread has ended. If join() is called on a Thread instance, the currently running thread will block until the Thread instance has finished executing.
			thread.join();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
}
