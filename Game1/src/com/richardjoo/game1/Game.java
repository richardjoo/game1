package com.richardjoo.game1;

import java.awt.Canvas;  // We need inherit Canvas class for our Game class
import java.awt.Color;
import java.awt.Dimension; // Dimension class
import java.awt.Graphics;
import java.awt.image.BufferStrategy; // to handle buffer

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
	
	
	// update will handle the logic
	public void update() {
		
	}
	
	// render will handle the display
	public void render() {
		// buffer strategy is the key :-)
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			// you pretty much have it at 3. rarely 2.
			// the reason 3 = tripple buffer storage
			// we can actually store two images now by using 3
			createBufferStrategy(3);
			return;
		}
		
		// need to apply data to the buffers now
		Graphics g = bs.getDrawGraphics();
		
		// START ----- all the graphics will be done in between these two comment lines.
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		
		// END ----- all the graphics will be done in between these two comment lines.
		
		g.dispose(); // dispose of current graphics, releases all of your cpu resources
		bs.show();
	}
	
	// since we are implementing Runnable, we need run method
	public void run() {
		// in here, we need to loop the game so the game won't stop and keep running
		while (running) {
			// Buffer Strategy
			// in order for code to normalize the game to work properly for
			// any computer systems, we need to split logics and controls and
			// others.  And need timer.
			
			
			// like same as tick() in more conventional way
			// update will handle all the logics
			// update at like 60 / sec, and render at unlimited rate
			update(); 
			
			// render will handle all the graphics, display images
			render();
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
