package ru.bsu.webdev1_9;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;

public class GameMap extends JPanel implements KeyListener {
	private static final long serialVersionUID = -6839529341001166798L;
	public static int WIDTH = 600;
	public static int HEIGHT = 500;
	public static int H = 10;
	public static int M = 40;
	public static int N = 40;
	protected ArrayList<Tank> snake;
	protected Tank apple;
	protected Timer timer;
	protected HashMap<Integer, Boolean> pressedKeys;
	// начальное направление змеи
	public int dx = 0;
	public int dy = -1;

	public GameMap() {
		System.out.println("GameMap instance created...");
//		this.tank = new Tank(5, M-2);
//		this.tank.setColor(Color.BLUE);
		
		this.apple = new Tank(10, 3);
		
		this.snake = new ArrayList<Tank>();
		this.snake.add(new Tank(20, 20));
		this.snake.add(new Tank(20, 21));
		this.snake.add(new Tank(20, 22));

		this.pressedKeys = new HashMap<Integer, Boolean>();
		this.pressedKeys.put(KeyEvent.VK_W, false);
		this.pressedKeys.put(KeyEvent.VK_A, false);
		this.pressedKeys.put(KeyEvent.VK_S, false);
		this.pressedKeys.put(KeyEvent.VK_D, false);

		timer = new Timer(1000 / 18, e -> {
            update();
            repaint();
        });
        timer.start();
	}

	protected void update() {	
		if (pressedKeys.get(KeyEvent.VK_W)) { // up
			if (dy != 1) { dx = 0; dy = -1;}
		}
		if (pressedKeys.get(KeyEvent.VK_A)) { // left
			if (dx != 1) { dx = -1; dy = 0;}
		}
		if (pressedKeys.get(KeyEvent.VK_S)) { // down
			if (dy != -1) { dx = 0; dy = 1;}
		}
		if (pressedKeys.get(KeyEvent.VK_D)) { // right
			if (dx != -1) { dx = 1; dy = 0;}
		}
		
		
		// передвижение змеи
		if (snake.get(0).x > 1 && snake.get(0).x < M) {
			System.out.println("if x (" + 1 + " - " + snake.get(0).x + " - " + M + ")");
			if (snake.get(0).y > 1 && snake.get(0).y < H) {
				System.out.println("(" + 1 + " - " + snake.get(0).y + " - " + N + ")");
				snakeMovement(dx, dy);
			} else {
				System.out.println("else y (" + 1 + " - " + snake.get(0).y + " - " + N + ")");
			}
		} else {
			System.out.println("else x (" + 1 + " - " + snake.get(0).x + " - " + M + ")");
		}
		
		if (apple.x == snake.get(0).x && apple.y == snake.get(0).y) {
			snake.add(0, new Tank(apple.x + dx, apple.y + dy));
			apple.x = Randomizer.getInt(1, M);
			apple.y = Randomizer.getInt(1, N);
		}
	}
	
	//метод для передвижения змеи
	protected void snakeMovement(int dx, int dy) {
		
		for (int i = snake.size() - 1; i >= 1; i-- ) {
//			System.out.println("(" + "i = " + i + ")");
			snake.get(i).x = snake.get(i - 1).x;
			snake.get(i).y = snake.get(i - 1).y;
		}
		snake.get(0).x += dx;
		snake.get(0).y += dy;
	}

	protected void render(Graphics g) {
		int y = 0;
		for (int j = 1; j <= M; j++) { // up->down

			y = j;
			for (int i = 1; i <= N; i++) { // left->right
				int x = i;
				drawSquare(g, x, y, Color.YELLOW, Color.BLACK);
			}
		}
		for (Tank snake : snake) {
			snake.render(g);
		}
		this.apple.render(g);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.render(g);
	}

	protected void drawSquare(Graphics g, int x, int y, Color lineColor, Color fillColor) {
		g.setColor(lineColor);
		g.fillRect(x * H, y * H, H, H);
		g.setColor(fillColor);
		g.drawRect(x * H, y * H, H, H);
	}

	public void keyPressed(KeyEvent e) {
		// char c = e.getKeyChar();
		// int code = e.getKeyCode();
		// String s = "keyPressed: [" + c + "] (" + String.valueOf(code) + ")";
		// System.out.println(s);
		
		int code = e.getKeyCode();
		System.out.println("keyCode: " + code);

		switch (code) {
		case KeyEvent.VK_W:
			this.pressedKeys.put(KeyEvent.VK_W, true);
			System.out.println(pressedKeys);
			break;
		case KeyEvent.VK_A:
			this.pressedKeys.put(KeyEvent.VK_A, true);
			break;
		case KeyEvent.VK_S:
			this.pressedKeys.put(KeyEvent.VK_S, true);
			break;
		case KeyEvent.VK_D:
			this.pressedKeys.put(KeyEvent.VK_D, true);
			break;
		}
	}
	
	public void keyReleased(KeyEvent e) {
		// char c = e.getKeyChar();
		// System.out.println("keyReleased: " + c);

		int code = e.getKeyCode();
		switch (code) {
		case KeyEvent.VK_W:
			this.pressedKeys.put(KeyEvent.VK_W, false);
			break;
		case KeyEvent.VK_A:
			this.pressedKeys.put(KeyEvent.VK_A, false);
			break;
		case KeyEvent.VK_S:
			this.pressedKeys.put(KeyEvent.VK_S, false);
			break;
		case KeyEvent.VK_D:
			this.pressedKeys.put(KeyEvent.VK_D, false);
			break;
		}
	}

	public void keyTyped(KeyEvent e) {
		// char c = e.getKeyChar();
		// System.out.println("keyTyped: " + c);
	}
}
