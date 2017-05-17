package com.tjpc3.perlinnoisemapmaker;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.tjpc3.perlinnoisemapmaker.graphics.Screen;

public class Main extends Canvas implements Runnable {
	private static int width = 350;
	private static int height = 350;
	private static int scale = 3;
	private static String title = "Picture Evolution";
	
	private boolean running = false;
	
	private Thread thread;
	private JFrame frame;
	private Screen screen;
	
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	public Main() {
		setPreferredSize(new Dimension(width * scale, height * scale));
		
		frame = new JFrame();
		screen = new Screen(width, height);
	}
	
	public static void main(String[] args) {		
		Main main = new Main();
		main.frame.setResizable(false);
		main.frame.setTitle(title);
		main.frame.add(main);
		main.frame.pack();
		main.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.frame.setLocationRelativeTo(null);
		main.frame.setVisible(true);
		
		main.start();
	}

	public void run() {
		
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60;
		double delta = 0;
		int frames = 0, updates = 0;
		requestFocus();
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();
				delta--;
				updates++;
			}
			render();
			frames++;
			
			if (System.currentTimeMillis() - timer > 1000) {
				timer +=1000;
				frame.setTitle(title + "  |  " + updates + " ups, "+ frames + " fps");
				frames = 0; 
				updates = 0;
			}
		}
		stop();
	}
	
	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		screen.clear();
		
		screen.pixels[40 + 40 * width] = 0xFFFFFF;
		
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}

	private int cyclestage = 0;
	private long generation = 0;
	
	private void update() {
		
	}

	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}

	public synchronized void stop() {
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
