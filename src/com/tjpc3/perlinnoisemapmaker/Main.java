package com.tjpc3.perlinnoisemapmaker;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Random;

import javax.swing.JFrame;

import com.tjpc3.perlinnoisemapmaker.generation.BiomeMap;
import com.tjpc3.perlinnoisemapmaker.generation.ElevationMap;
import com.tjpc3.perlinnoisemapmaker.generation.Map;
import com.tjpc3.perlinnoisemapmaker.graphics.Screen;
import com.tjpc3.perlinnoisemapmaker.noise.Noise;
import com.tjpc3.perlinnoisemapmaker.noise.PerlinNoise;

public class Main extends Canvas implements Runnable {
	private static int scale = 1;
	private static int width = 1500 / scale;
	private static int height = 1100 / scale;
	private static String title = "Perlin Noise Map Maker";
	
	private boolean running = false;
	
	private Thread thread;
	private JFrame frame;
	private Screen screen;
	
	private Noise whiteNoise;
	private Noise basicNoise;
	private Noise perlinNoise;
	private Map map;
	
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	private Random rand = new Random();
	
	double centerMultiplier = 0.5;
	
	public Main() {
		setPreferredSize(new Dimension(width * scale, height * scale));
		
		frame = new JFrame();
		screen = new Screen(width, height);

//		whiteNoise = new WhiteNoise(width, height, rand.nextLong());
//		
//		for (int y = 0; y < height; y++) {
//			for (int x = 0; x < width; x++) {
//				whiteNoise.pixels[x + y * width] = Interpolation.bilerp(x / (double) width, y / (double) height, 1.0, 0.5, 0.0, 1.0);
//			}
//		}
//		
		//perlinNoise = new PerlinNoise(width, height, rand.nextLong());
		//perlinNoise.center(centerMultiplier);
		//perlinNoise.stretch();
		map = new BiomeMap(width, height, centerMultiplier, rand.nextLong());
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
		
		//perlinNoise.render(screen, 0, 0);
		
		map.render(screen, 0, 0);
		
		//basicNoise.render(screen, 0, 0);
		
		//whiteNoise.render(screen, 0, 0);
		
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}
	
	int count = 0;
	
	private void update() {
		count++;
		count %= 60;
		if (count == 1) {
			map = new BiomeMap(width, height, centerMultiplier,  rand.nextLong());
			//perlinNoise = new PerlinNoise(width, height, rand.nextLong());
			//perlinNoise.center(centerMultiplier);
			//perlinNoise.stretch();
		}
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
