package game;

import game.entity.Player;
import game.input.InputHandler;
import game.logging.Log;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 120;
	public static final int HEIGHT = 120 / 12 * 9;
	public static final int SCALE = 7;
	
	private JFrame frame;
	private Player player;
	private InputHandler input;
	
	private boolean isRunning;
	private boolean debug = true;
	private BufferedImage image = new BufferedImage(WIDTH * SCALE, HEIGHT * SCALE, BufferedImage.TYPE_INT_RGB);
	public int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	public Game() {
		setMaximumSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		setMinimumSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(this, BorderLayout.CENTER);
		frame.pack();		
		
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setAlwaysOnTop(true);
		
		player = new Player(0, 0);
		input = new InputHandler(this, player);
		player.setInput(input);
		
		start();
	}
	
	public void start() {
		isRunning = true;
		new Thread(this).start();
	}
	
	public void stop() {
		isRunning = false;
	}

	public void run() {
		long last = System.nanoTime();
		double ns = 1000000000.0/60.0;
		double timeDiff = 0;
		
		while(isRunning) {
			long now = System.nanoTime();
			timeDiff += (now - last) / ns;
			last = now;
			
			while (timeDiff >= 1) {
				update();
				timeDiff--;
			}
			
			paint();
		}
	}
	
	public void update() {
		player.update();
	}
	
	public void paint() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		Graphics2D g = (Graphics2D)bs.getDrawGraphics();
		g.clearRect(0, 0, getWidth(), getHeight());
		
		g.fillRect(player.getX(), player.getY(), Player.HEIGHT, Player.WIDTH);
		if (debug) {
			g.setColor(Color.RED);
			Log.debug(g, player.getX() + ", " + player.getY(), 5, 15);
		}
		
		g.dispose();
	
		bs.show();
	}

}
