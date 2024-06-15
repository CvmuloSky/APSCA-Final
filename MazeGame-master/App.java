import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class App {
	private JFrame frame;
	private Cell[][] cells;
	private Maze maze;
	private Player player;
	private Game panel;
	private static final int HorizontalCells = 61;
	private static final int VerticalCells = 61;

	public App(Player p) {
		player = p;
		frame = new JFrame("Tres Leches");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new Game();
		setPreference();
		cells = new Cell[VerticalCells][HorizontalCells];
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				cells[i][j] = new Cell(j * Cell.getHeight(), i * Cell.getWidth());
			}
		}
		maze = new Maze(frame, player, cells);
		run();

	}

	public void setPreference() {
		frame.addKeyListener(panel);
		frame.setSize(HorizontalCells * Cell.getWidth() + 20, VerticalCells * Cell.getHeight() + 34);
		frame.setLocationByPlatform(true);
		frame.setResizable(true);
		frame.getContentPane().add(panel);
		frame.setVisible(true);
	}

	private void run() {
		maze.move();

		ActionListener tick = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				player.update(cells);
				frame.repaint();
			}
		};
		Timer timer = new Timer(1000 / 400, tick);
		timer.start(); // learned from stack overflow
	}

	class Game extends JPanel implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
			// not used
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_R) {
				player.reset();
				player.setHasWon(false);
				maze.move();
			}
			maze.keyDown(e);
			frame.repaint();
		}

		@Override
		public void keyReleased(KeyEvent e) {
			maze.keyUp(e);
			frame.repaint();
		}

		@Override
		public void paint(Graphics g) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			g.setColor(Color.WHITE);
			g.fillRect(HorizontalCells * Cell.getWidth() + 10, 0, 5, Cell.getHeight() * 60);
			for (int i = 0; i < cells.length; i++) {
				for (int j = 0; j < cells[i].length; j++) {
					g.setColor(cells[j][i].getColor());
					g.fillRect(cells[j][i].getX(), cells[j][i].getY(), Cell.getWidth(), Cell.getHeight());
				}
			}
			player.draw(g);
		}
	}
}
