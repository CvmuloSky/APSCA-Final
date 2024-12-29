import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

public class Bot {
    private int x, y;
    private Cell[][] cells;
    private Player player;

    public Bot(Cell[][] cells, Player player) {
        this.cells = cells;
        this.player = player;
        this.x = player.getX();
        this.y = player.getY();
    }
    //a* algo
    public void findPathAndMove(Cell goal) {
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(n -> n.f));
        Set<Cell> closedSet = new HashSet<>();

        Node start = new Node(cells[y / Cell.getHeight()][x / Cell.getWidth()], null, 0, estimateHeuristic(x, y, goal));
        openSet.add(start);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (current.cell == goal) {
                // found path
                moveAlongPath(current);
                return;
            }

            closedSet.add(current.cell);

            for (Node neighbor : getNeighbors(current, goal)) {
                if (closedSet.contains(neighbor.cell)) continue;

                if (!openSet.contains(neighbor)) {
                    openSet.add(neighbor);
                }
            }
        }

        System.out.println("No path found to the goal!");
    }

    private void moveAlongPath(Node node) {
        Stack<Cell> path = new Stack<>();
        while (node != null) {
            path.push(node.cell);
            node = node.parent;
        }

        while (!path.isEmpty()) {
            Cell next = path.pop();
            x = next.getX();
            y = next.getY();
            player.setX(x);
            player.setY(y);

            try {
                Thread.sleep(150); //realtime movement
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private List<Node> getNeighbors(Node current, Cell goal) {
        List<Node> neighbors = new ArrayList<>();
        int[][] directions = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}}; //directions

        int cx = current.cell.getX() / Cell.getWidth();
        int cy = current.cell.getY() / Cell.getHeight();

        for (int[] dir : directions) {
            int nx = cx + dir[0];
            int ny = cy + dir[1];

            if (nx >= 0 && ny >= 0 && nx < cells[0].length && ny < cells.length) {
                Cell neighbor = cells[ny][nx];
                if (!neighbor.getColor().equals(Color.BLACK)) { //not wall
                    int g = current.g + 1;
                    int h = estimateHeuristic(nx * Cell.getWidth(), ny * Cell.getHeight(), goal);
                    neighbors.add(new Node(neighbor, current, g, g + h));
                }
            }
        }

        return neighbors;
    }

    private int estimateHeuristic(int x1, int y1, Cell goal) {
        int x2 = goal.getX();
        int y2 = goal.getY();
        return Math.abs(x1 - x2) + Math.abs(y1 - y2); //manhattan distance
    }

    private static class Node {
        Cell cell;
        Node parent;
        int g; 
        int f;

        Node(Cell cell, Node parent, int g, int f) {
            this.cell = cell;
            this.parent = parent;
            this.g = g;
            this.f = f;
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLUE); 
        g.fillRect(x, y, player.getWidth(), player.getHeight());
    }
}
