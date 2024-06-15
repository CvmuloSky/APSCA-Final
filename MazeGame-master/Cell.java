import java.awt.Color;

public class Cell {
	private static final int width = 15;
	private static final int height = 15;

	private int x;
	private int y;

	private Color col;

	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
		col = Color.black;
	}

	public Color getColor() {
		return col;
	}

	public void setColor(Color newCol) {
		col = newCol;
	}

	public int getX() {
		return x;
	}

	public static int getWidth() {
		return width;
	}

	public static int getHeight() {
		return height;
	}

	public int getY() {
		return y;
	}

	public boolean collidedWith(Player player) {
        return 
            (x 	< player.getX() + player.getWidth() && 
            player.getX() < x + width &&
            y < player.getY() + player.getHeight() &&
            player.getY() < y + height);
    }
}
