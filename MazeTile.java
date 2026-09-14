/*
    Name: Donnie Ranjel
    Date: 5/1/2026
    File Name: MazeTile.java
    Assignment: Final Programming Project
    Program Name: Pathfinder
    File Description: Abstract class establishing the blueprint for all maze tiles. Stores shared navigation data and defines the passability rule that the concrete subclasses for walls and paths must implement.
    Inputs: Row and column coordinates.
    Outputs: Boolean passability checks, visited state, and parent linkage for path tracing.
*/

// Abstract base class representing every tile in the maze grid
public abstract class MazeTile {
    protected int row, col; // Coordinates of the tile in the grid
    protected boolean visited;
    protected MazeTile parent; // Pointer to the parent tile we came from

    // Constructor to initialize navigation flags and to record the tile's position
    public MazeTile(int row, int col) {
        this.row = row;
        this.col = col;
        this.visited = false;
        this.parent = null;
    }

    // Abstract method to determine if the tile can be traversed
    public abstract boolean isPassable();

    // Getter method to retrieve the visited status
    public boolean isVisited() {
        return visited;
    }

    // Setter method to set the visited status
    public void setVisited(boolean v) {
        this.visited = v;
    }

    // Getter method to retrieve the parent tile
    public MazeTile getParent() {
        return parent;
    }

    // Setter method to set the parent tile
    public void setParent(MazeTile p) {
        this.parent = p;
    }

    // Getter method to retrieve the row coordinate
    public int getRow() {
        return row;
    }

    // Getter method to retrieve the column coordinate
    public int getCol() {
        return col;
    }

    // String to display the coordinates of the tile
    @Override
    public String toString() {
        return "(" + row + "," + col + ")";
    }
}

// Concrete subclass representing a wall tile
class WallTile extends MazeTile {
    public WallTile(int r, int c) {
        super(r, c);
    }

    // Override the isPassable method to indicate that this tile cannot be traversed
    @Override
    public boolean isPassable() {
        return false;
    }
}

// Concrete subclass representing an open path tile
class PathTile extends MazeTile {
    public PathTile(int r, int c) {
        super(r, c);
    }

    // Override the isPassable method to indicate that this tile can be traversed
    @Override
    public boolean isPassable() {
        return true;
    }
}