import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*;

public class Player {
    private int speed;
    private final static int width = 10, height = 10;
    private ArrayList<Integer> inputs;
    private int x, y;
    private Color color;
    private boolean hasWon;

    public Player(int x, int y, Color color,int speed) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.speed = speed;
        inputs = new ArrayList<>();
        hasWon = false;
    }

    public void addinput(KeyEvent e) {
        if (inputs.indexOf(e.getKeyCode()) == -1)
            inputs.add(e.getKeyCode());
    }

    public void removeinput(KeyEvent e) {
        inputs.remove(inputs.indexOf((e.getKeyCode())));
    }

    public void update(Cell[][] cells) {
        if (!hasWon) {
            if (inputs.contains(KeyEvent.VK_W) || inputs.contains(KeyEvent.VK_UP)) {
                y = (y - speed);
                // System.out.println("y: " + y); //logging
                for (Cell[] cA : cells) {
                    for (Cell c : cA) {
                        // checking for collided
                        if (c.getColor() == Color.BLACK) {
                            while (c.collidedWith(this)) {
                                y += 1;
                            }
                        }
                        if (c.getColor() == Color.GREEN) {
                            if (c.collidedWith(this)) {
                                hasWon = true;
                                System.out.print("YOU WON you are the sigma");
                            }
                        }
                    }
                }
            }
            if (inputs.contains(KeyEvent.VK_S) || inputs.contains(KeyEvent.VK_DOWN)) {
                y = (y + speed);
                // System.out.println("y: " + y); //logging
                for (Cell[] cA : cells) {
                    for (Cell c : cA) {
                        // checking for collided
                        if (c.getColor() == Color.BLACK) {
                            while (c.collidedWith(this)) {
                                y -= 1;
                            }
                        }
                        if (c.getColor() == Color.GREEN) {
                            if (c.collidedWith(this)) {
                                hasWon = true;
                                System.out.print("YOU WON you are the sigma");
                            }
                        }
                    }
                }
            }
            if (inputs.contains(KeyEvent.VK_A) || inputs.contains(KeyEvent.VK_LEFT)) {
                x = (x - speed);
                // System.out.println("x: " + x); //logging
                for (Cell[] cA : cells) {
                    for (Cell c : cA) {
                        // checking for collided
                        if (c.getColor() == Color.BLACK) {
                            while (c.collidedWith(this)) {
                                x += 1;
                            }
                        }
                        if (c.getColor() == Color.GREEN) {
                            if (c.collidedWith(this)) {
                                hasWon = true;
                                System.out.print("YOU WON you are the sigma");
                            }
                        }
                    }
                }
            }
            if (inputs.contains(KeyEvent.VK_D) || inputs.contains(KeyEvent.VK_RIGHT)) {
                x = (x + speed);
                // System.out.println("x: " + x); //logging
                for (Cell[] cA : cells) {
                    for (Cell c : cA) {
                        // checking for collided
                        if (c.getColor() == Color.BLACK) {
                            while (c.collidedWith(this)) {
                                x -= 1;
                            }
                        }
                        if (c.getColor() == Color.GREEN) {
                            if (c.collidedWith(this)) {
                                hasWon = true;
                                System.out.print("YOU WON you are the sigma");
                            }
                        }
                    }
                }
            }
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = x;
    }

    public void reset() {
        x = 15;
        y = 15;
    }

    public boolean getHasWon() {
        return hasWon;
    }

    public void setHasWon(boolean b) {
        hasWon = b;
    }

    public ArrayList<Integer> getInputs() {
        return inputs;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }
}