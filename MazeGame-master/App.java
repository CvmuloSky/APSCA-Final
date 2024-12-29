import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {
	private JFrame frame;
	private Cell[][] cells;
	private Maze maze;
	private Player player; 
	private Bot bot; 
	private Game panel;
	private static final int HorizontalCells = 61;
	private static final int VerticalCells = 61;
	private ExecutorService executor; 

	public App(Player p, boolean b) {
		player = p;
		frame = new JFrame("Tres Leches");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new Game();
		setPreference();
		cells = new Cell[VerticalCells][HorizontalCells];
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				cells[i][j] = new Cell(j * Cell.getWidth(), i * Cell.getHeight());
			}
		}
		maze = new Maze(frame, player, cells);
		maze.move();


		if (!b) {
			Cell goal = findGoalCell(); 
			if (goal != null) {
				bot = new Bot(cells, new Player(15, 15, Color.BLACK, 1)); 
				executor = Executors.newSingleThreadExecutor();
				executor.execute(() -> bot.findPathAndMove(goal)); 
			} else {
				System.out.println("Goal cell not found!");
			}
		}

		run();
	}

	private Cell findGoalCell() {
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				if (cells[i][j].getColor().equals(Color.GREEN)) {
					return cells[i][j];
				}
			}
		}
		return null; 
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

		ActionListener tick = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				player.update(cells); 
				frame.repaint();
			}
		};
		Timer timer = new Timer(1000 / 120, tick); 
		timer.start();
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

				Cell goal = findGoalCell();
				if (goal != null && bot != null) {
					executor.execute(() -> bot.findPathAndMove(goal));
				}
			}
			player.addinput(e);
			frame.repaint();
		}

		@Override
		public void keyReleased(KeyEvent e) {
			player.removeinput(e); 
			frame.repaint();
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g); 
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
			if (bot != null) {
				bot.draw(g); 
			}
		}
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		if (executor != null && !executor.isShutdown()) {
			executor.shutdownNow();
		}
	}
}
