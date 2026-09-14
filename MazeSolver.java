/*
    Name: Donnie Ranjel
    Date: 5/1/2026
    File Name: MazeSolver.java
    Assignment: Final Programming Project
    Program Name: Pathfinder
    File Description: Loads a maze from a text file, constructs the tile grid, and applies either a breadth-first search or depth-first search to find a valid route from the start tile to the exit tile. Once the exit is reached, the solver reconstructs the winning path by walking backward through the parent pointers.
    Inputs: A filename containing the maze layout.
    Outputs: A linked list representing the discovered path, or null if no valid route exists.

    Change Log:
    ---------------------------------------------------------------------------------------------------------
    |  Date   |   Description
    ---------------------------------------------------------------------------------------------------------
    |  4/20   |   Initial creation of the solver engine with Depth-First Search implementation.
    |  5/10   |   Replaced fail-safe clearing logic with clearMaze() method.
    |         |   Implemented Breadth-First Search (BFS) using MyQueue.
    |         |   Added getter methods to expose grid for GUI rendering.
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MazeSolver {

    private MazeTile[][] grid; // 2D map of all tiles in the maze
    private MazeTile startTile; // Entry point where the solver begins
    private MazeTile exitTile; // Target tile the solver must reach

    // Dimensions of the maze
    private int rows;
    private int cols;

    // Reads the maze file, measures its dimensions, and generates tile objects
    public void loadMaze(String filename) throws MazeException, FileNotFoundException {

        // Initialize size to 0
        rows = 0;
        cols = 0;

        try (Scanner fileScanner = new Scanner(new File(filename))) {
            // Measures the maze to determine grid size
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if (line.length() > cols) {
                    cols = line.length(); // tracks the longest row
                }
                rows++;
            }
        }

        // Build maze with known dimensions
        grid = new MazeTile[rows][cols];

        // Initialize counters
        int startCount = 0;
        int exitCount = 0;

        try (Scanner fileScanner = new Scanner(new File(filename))) {
            // Build the tile objects
            for (int r = 0; r < rows; r++) {
                String line = fileScanner.nextLine();

                // Extract each character to translate into the correct tile type
                for (int c = 0; c < cols; c++) {
                    char ch = (c < line.length()) ? line.charAt(c) : '#';

                    // Wall become WallTile objects
                    if (ch == '#') {
                        grid[r][c] = new WallTile(r, c);

                        // Any non-wall becomes a PathTile
                    } else {
                        grid[r][c] = new PathTile(r, c);

                        // Mark the start tile
                        if (ch == 'S') {
                            startTile = grid[r][c];
                            startCount++;
                        }

                        // Mark the exit tile
                        if (ch == 'E') {
                            exitTile = grid[r][c];
                            exitCount++;
                        }
                    }
                }
            }
        }

        // Validate that the maze contains exactly one start and one exit
        if (startCount != 1 || exitCount != 1) {
            throw new MazeException("Invalid maze: Must have exit and start.");
        }
        System.out.println("Maze loaded successfully.");
    }

    // Applies a depth-first search to hunt for a route from S to E
    public MyLinkedList<MazeTile> solveDFS() {

        // If no start tile was found, the maze cannot be solved
        if (startTile == null) {
            return null;
        }

        // Clean maze before starting a new search
        clearMaze();

        MyStack<MazeTile> stack = new MyStack<>();

        // Drop the start tile on the stack and mark it as visited
        stack.push(startTile);
        startTile.setVisited(true);

        // Movement vectors to explore
        int[][] dirs = {
            {-1, 0}, // North
            {1, 0}, // South
            {0, -1}, // West
            {0, 1} // East
        };

        // Continue exploring until the stack is empty
        while(!stack.isEmpty()) {

            // Peel off the next tile to explore
            MazeTile current = stack.pop();

            // If we've reached the exit, reconstruct the winning path
            if (current == exitTile) {
                return reconstructPath(current);
            }

            // Probe each neighboring tile
            for(int[] d : dirs) {
                int nr = current.getRow() + d[0];
                int nc = current.getCol() + d[1];

                // Ensure the neighbor is within the maze bounds
                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols) {
                    MazeTile next = grid[nr][nc];

                    // Traversal onto tiles that are passable and unvisited
                    if (next.isPassable() && !next.isVisited()) {
                        next.setVisited(true);
                        next.setParent(current); // Record the path
                        stack.push(next); // Continue the DFS from this tile
                    }
                }
            }
        }

        // No path was found
        return null;
    }

    // Applies a breadth-first search to hunt for a route from S to E
    public MyLinkedList<MazeTile> solveBFS() {
        
        // If no start tile was found, the maze cannot be solved
        if (startTile == null) {
        return null;
        }

        // Clean maze before starting a new search
        clearMaze();

        // Queue for BFS
        MyQueue<MazeTile> queue = new MyQueue<>();

        // Drop the start tile on the queue and mark it as visited
        queue.enqueue(startTile);
        startTile.setVisited(true);

        // Movement vectors to explore
        int[][] dirs = { {-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        // Continue exploring until the queue is empty
        while (!queue.isEmpty()) {
            MazeTile current = queue.dequeue();

            // If we've reached the exit, reconstruct the winning path
            if (current == exitTile) {
                return reconstructPath(current);
            }

            // Probe each neighboring tile
            for (int[] d : dirs) {
                int nr = current.getRow() + d[0];
                int nc = current.getCol() + d[1];

                // Ensure the neighbor is within the maze bounds
                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols) {
                    MazeTile next = grid[nr][nc];

                    // Traversal onto tiles that are passable and unvisited
                    if (next.isPassable() && !next.isVisited()) {
                        next.setVisited(true);
                        next.setParent(current);

                        // Continue the BFS from this tile
                        queue.enqueue(next);
                    }
                }
            }
        }
        return null;
    }

    // Walks backward from the exit tile to the start tile using parent pointers
    private MyLinkedList<MazeTile> reconstructPath(MazeTile end) {
        MyLinkedList<MazeTile> path = new MyLinkedList<>();
        MazeTile curr = end;

        // Follow the chain of parents until we reach the start
        while (curr != null) {
            path.addFirst(curr);
            curr = curr.getParent();
        }

        // The path is currently reversed
        return path;
    }

    // Resets the visited and parent states of all tiles in te grid
    public void clearMaze() {
        if (grid == null) {
            return;
        }
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] != null) {
                    grid[r][c].setVisited(false);
                    grid[r][c].setParent(null);
                }
            }
        }
    }

    // Getter method to retrieve the 2D grid of tiles
    public MazeTile[][] getGrid() {
        return grid;
    }

    // Getter method to retrieve the number of rows in the maze
    public int getRows() {
        return rows;
    }

    // Getter method to retrieve the number of columns in the maze
    public int getCols() {
        return cols;
    }
    
}
