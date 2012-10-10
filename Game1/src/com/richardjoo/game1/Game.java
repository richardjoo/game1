package com.richardjoo.game1;

import java.awt.Canvas;  // We need inherit Canvas class for our Game class
import java.awt.Dimension; // Dimension class
import javax.swing.JFrame;  // We will be using JFrame for our game window.


public class Game extends Canvas implements Runnable {
	
	/**
	 *	Setting default serialVersionUID 
	 */
	private static final long serialVersionUID = 1L;
	
	// setting up resolution
	public static int width = 300;
	public static int height = ( width/16 ) * 9;  // this will make it to 16:9 screen ratio
	public static int scale = 3; // how much our game would scaled up to. so, if it is 300 width, it would go up to 900
	
	
	// creating thread object so we can do multiple things simultaneously
	private Thread thread;
	private boolean running = false; // this will govern the entire game run or stop
	private JFrame frame;
	
	
	// Constructor
	public Game() {
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);
		
		frame = new JFrame();
	}
	
	// we will create a new object to manipulate the thread
	// synchronized here so we can prevent thread interferences and memory consistency errors
	// without this, wrong thread management can lock down the system as well.
	public synchronized void start() {
		// when the game starts, we set running to true so the game can keep on running
		running = true;
		
		//so, this here is same as new Game()
		thread = new Thread(this, "Display");
		thread.start();
	}
	
	// we need stop() to stop the thread too.
	public synchronized void stop() {
		// game stopping, meaning, we need to break out of while loop in run method
		running = false;
		try {
			// join is used where the main thread to wait until all the threads are completed
			// The join() method of a Thread instance can be used to "join" the start of a thread's execution to the end of another thread's execution so that a thread will not start running until another thread has ended. If join() is called on a Thread instance, the currently running thread will block until the Thread instance has finished executing.
			thread.join();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	// since we are implementing Runnable, we need run method
	public void run() {
		// in here, we need to loop the game so the game won't stop and keep running
		while (running) {
			System.out.println("Running...");
		}
	}
	
	public static void main(String[] args) {
		// instantiate Game
		Game game = new Game();
		
		// set the frame
		game.frame.setResizable(false);
		game.frame.setTitle("Game1");
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);
		
		// let's start the game!
		game.start();
	}
	
}
