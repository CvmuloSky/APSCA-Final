import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Maze {
    private static Player player;
    private static final int FPMS = 0;
    private JFrame frame;
    private Cell[][] cells;

    public Maze(JFrame f, Player p, Cell[][] c) {
        frame = f;
        player = p;
        cells = c;
    }

    public void move() {
        for (Cell[] cA : cells) {
            for (Cell c : cA) {
                c.setColor(Color.BLACK);
            }
        }
        cells[cells.length - 2][1].setColor(Color.WHITE); // start/first white cell that establishes a seeker and
                                                          // continues until no more start cells are found
        Cell start = cells[cells.length - 2][3];
        while (start != null) {
            remove(start.getX(), start.getY());
            start = startCell(start);
        }
        setStart();
        setEnd();
        player.setX(15);
        player.setY(15);
    }

    public void remove(int ix, int iy) {
        ArrayList<Cell> tail = new ArrayList<Cell>(); // keeps track of cells tht have been visited
        ArrayList<Integer> moveStack = new ArrayList<Integer>(); // Logs the directions of the moves and used for
                                                                 // reversing
        Random rand = new Random(); //chooses directions
        int x = ix / Cell.getWidth(); //convert pixel x-coordinate to index
        int y = iy / Cell.getHeight(); //convert pixel y-coordinate to index
        tail.add(cells[y][x]); //adds the starting cell to the tail
        cells[y][x].setColor(Color.GREEN); //mark the initial cell as part of the path
        moveStack.add(-1); //initial
        while (true) {
            // chooses cells on previous stack of cells
            ArrayList<Integer> possibleCells = new ArrayList<Integer>();
            int prev = moveStack.get(moveStack.size() - 1);

            // Check for possible moves and avoid backtracking
            if (x - 2 > 0 && prev != 1) // Left
                possibleCells.add(0);
            if (x + 2 < cells[0].length - 1 && prev != 0) // Right
                possibleCells.add(1);
            if (y - 2 > 0 && prev != 3) // Up
                possibleCells.add(2);
            if (y + 2 < cells.length - 1 && prev != 2) // Down
                possibleCells.add(3);
            int move = possibleCells.get(rand.nextInt(possibleCells.size())); // Randomly move

            // Moves 2 spaces so I wont need to use my brain to create walls its also
            // similar to Prims algorithm that I found online
            for (int i = 0; i < 2; i++) {
                if (move == 0) // Move left
                    x--;
                if (move == 1) // Move right
                    x++;
                if (move == 2) // Move up
                    y--;
                if (move == 3) // Move down
                    y++;
                tail.add(cells[y][x]); // adds a new cell to the tail;
                cells[y][x].setColor(Color.GREEN); // to identify part of the tail
                boolean looped = false;
                // look for loops in the tail and backtracks
                if (cells[y][x + 1].getColor().equals(Color.GREEN) && move != 0) {
                    x++;
                    looped = true;
                } else if (cells[y][x - 1].getColor().equals(Color.GREEN) && move != 1) {
                    x--;
                    looped = true;
                } else if (cells[y + 1][x].getColor().equals(Color.GREEN) && move != 2) {
                    y++;
                    looped = true;
                } else if (cells[y - 1][x].getColor().equals(Color.GREEN) && move != 3) {
                    y--;
                    looped = true;
                } else if (cells[y + 1][x + 1].getColor().equals(Color.GREEN)
                        && cells[y + 1][x + 1] != tail.get(tail.size() - 3)) {
                    x++;
                    y++;
                    looped = true;
                } else if (cells[y - 1][x - 1].getColor().equals(Color.GREEN)
                        && cells[y - 1][x - 1] != tail.get(tail.size() - 3)) {
                    x--;
                    y--;
                    looped = true;
                } else if (cells[y - 1][x + 1].getColor().equals(Color.GREEN)
                        && cells[y - 1][x + 1] != tail.get(tail.size() - 3)) {
                    x++;
                    y--;
                    looped = true;
                } else if (cells[y + 1][x - 1].getColor().equals(Color.GREEN)
                        && cells[y + 1][x - 1] != tail.get(tail.size() - 3)) {
                    x--;
                    y++;
                    looped = true;
                }

                if (looped) {
                    // if there is a loop then the code will backtrack
                    int backtrack = tail.indexOf(cells[y][x]);
                    for (int j = backtrack + 1; j < tail.size(); j++) {
                        tail.get(j).setColor(Color.BLACK); //mark cells as unvisited
                    }
                    tail.subList(backtrack + 1, tail.size()).clear(); //remove the looped cells from the tail
                    moveStack.subList(backtrack / 2 + 1, moveStack.size()).clear(); //clear the move stack
                    frame.repaint();
                    break;
                }

                //If tail is adjacent to a white cell, mark the tail cells as white (part of
                // the maze path)
                if (i == 0 && (cells[y][x - 1].getColor().equals(Color.WHITE) ||
                        cells[y][x + 1].getColor().equals(Color.WHITE) ||
                        cells[y - 1][x].getColor().equals(Color.WHITE) ||
                        cells[y + 1][x].getColor().equals(Color.WHITE))) {
                    for (Cell c : tail) {
                        c.setColor(Color.WHITE);
                    }
                    frame.repaint();
                    return;
                }
            }
            moveStack.add(move); //log the move

            //animating
            frame.repaint();
            try {
                Thread.sleep(FPMS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Cell startCell(Cell lastStart) { // checks all sides of a cell to see if there are 8 adjacent black cells for
                                            // a start cell
        boolean firstLoop = true;
        for (int y = cells.length - 2; y >= 1; y--) {
            for (int x = 1; x <= cells[y].length - 2; x++) {
                if (firstLoop) {
                    x = lastStart.getX() / Cell.getWidth();
                    y = lastStart.getY() / Cell.getHeight();
                    firstLoop = false;
                }
                int adjacentBlackCounter = 0;
                if (cells[y][x - 1].getColor().equals(Color.BLACK))
                    adjacentBlackCounter++;
                if (cells[y][x + 1].getColor().equals(Color.BLACK))
                    adjacentBlackCounter++;
                if (cells[y - 1][x].getColor().equals(Color.BLACK))
                    adjacentBlackCounter++;
                if (cells[y + 1][x].getColor().equals(Color.BLACK))
                    adjacentBlackCounter++;
                if (cells[y - 1][x - 1].getColor().equals(Color.BLACK))
                    adjacentBlackCounter++;
                if (cells[y - 1][x + 1].getColor().equals(Color.BLACK))
                    adjacentBlackCounter++;
                if (cells[y + 1][x - 1].getColor().equals(Color.BLACK))
                    adjacentBlackCounter++;
                if (cells[y + 1][x + 1].getColor().equals(Color.BLACK))
                    adjacentBlackCounter++;
                if (adjacentBlackCounter == 8) {
                    return cells[y][x];
                }
            }
        }
        return null;
    }

    private void setStart() {
        cells[1][1].setColor(Color.RED);
    }

    private void setEnd() {
        Cell temp = cells[0][0];
        int r = 0;
        int c = 0;
        while (temp.getColor() == Color.BLACK && r != 1 && c != 1) {
            r = (int) (Math.random() * 30) + 31;
            c = (int) (Math.random() * 30) + 31;
            temp = cells[r][c];
        }
        cells[r][c].setColor(Color.GREEN);
    }

    public void keyDown(KeyEvent e) {
        player.addinput(e);
    }

    public void keyUp(KeyEvent e) {
        player.removeinput(e);
    }
}
