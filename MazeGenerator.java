/*
    Name: Donnie Ranjel
    Date: 5/6/2026
    File Name: MazeGenerator.java
    Assignment: Final Programming Project
    Program Name: Pathfinder
    File Description: Utility class that builds a simple randomized maze layout and writes it directly to a text file. The generator places walls along the outer boundary, scatters obstacles with controlled randomness (30%), and places a start and exit tile.
    Inputs: Target filename and the maze dimensions (rows and columns).
    Outputs: A written .txt maze file containing walls, open paths, a start tile, and an exit tile.
*/

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class MazeGenerator {

    // Creates a randomized maze and writes it to the specified file
    public static void generateMazeFile(String filename, int rows, int cols) {

        // Validate maze dimensions
        if (rows < 5 || cols < 5) {
            System.out.println("Maze dimensions too small. Defaulting to 5x5.");
            rows = 5;
            cols = 5;
        }

        char[][] newMaze = new char[rows][cols]; // 2D character grid for the maze
        Random rand = new Random(); // Random engine for wall placement

        // Builds the maze boundaries and random obstacles
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {

                // Generate walls on the outer border
                if (r == 0 || r == rows - 1 || c == 0 || c == cols - 1) {
                    newMaze[r][c] = '#';
                } else {
                    // 30% chance of a wall, otherwise open space
                    newMaze[r][c] = (rand.nextInt(100) < 30) ? '#' : ' ';
                }
            }
        }

        // Place start tile near top left corner
        newMaze[1][1] = 'S';

        // Place exit tile near bottom right corner
        newMaze[rows - 2][cols - 2] = 'E';

        // Write the completed maze layout to the output file
        try (FileWriter writer = new FileWriter(filename)) {

            // Walk through the grid row-by-row and write each character
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    writer.write(newMaze[r][c]);
                }

                // Add a newline after each row
                writer.write("\n");
            }
            System.out.println("Random maze generated: " + filename);
        } catch (IOException e) {
            System.out.println("Error writing maze file: " + e.getMessage());
        }
    } 
}
