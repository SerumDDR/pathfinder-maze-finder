/*
    Name: Donnie Ranjel
    Date: 5/6/2026
    File Name: MazeSolverGUI.java
    Assignment: Final Programming Project
    Program Name: Pathfinder
    Program Description: Pathfinder loads or generates a maze, represents it as a grid of the tile objects, and solves it using either DFS or BFS to find a route from the start to the exit. Custom data structures (linked list, stack, queue, node) support searching and path reconstruction. The program includes a random maze generator and a GUI that lets the user load mazes, generate new ones, run either solver and reset the maze. The interface renders the maze and highlights the solution path, providing a clear visual demonstration of classic search algorithms.
    Inputs: Maze file loaded by the solver engine and the solution path returned by DFS or BFS.
    Outputs: A fully rendered maze window displaying walls, open paths, and the traced solution route produced by DFS or BFS.

    Change Log:
    ---------------------------------------------------------------------------------------------------------
    |  Date   |   Description
    ---------------------------------------------------------------------------------------------------------
    |  5/6    |   Initial creation of the GUI.
    |  5/9    |   Integrated JMenuBar.
    |  5/10   |   Integrated JButton panel.
    |         |   Added state synchronization to lock/unlock UI components.
    |         |   Added DFS andBFS algorithm selection and Swing Utilities for Event Dispatch Thread.
    ---------------------------------------------------------------------------------------------------------
    <-- RUN THIS FILE -->
*/

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.SwingUtilities;

public class MazeSolverGUI extends JPanel {

    // Core solver engine that loads and processes the maze
    private MazeSolver engine;

    // Final DFS path returned by the solver
    private MyLinkedList<MazeTile> solutionPath;

    // Pixel size of each tile when drawn
    private final int TILE_SIZE = 40;

    // GUI components for menu and buttons to enable and disable solver options
    private JMenuItem solveDFSMenuItem;
    private JMenuItem solveBFSMenuItem;
    private JButton solveDFSButton;
    private JButton solveBFSButton;

    // Constructs the GUI panel. loads the maze, and runs the DFS solver
    public MazeSolverGUI() {
        engine = new MazeSolver();
        solutionPath = null;
    }

    // Builds the menu bar with options for loading, generating, solving, and clearing mazes
    public JMenuBar createMenuBar(JFrame frame) {
        JMenuBar menuBar = new JMenuBar();

        // Create the File menu container
        JMenu fileMenu = new JMenu("File");

        // Option to load a maze from a text file
        JMenuItem loadItem = new JMenuItem("Load Maze from File...");

        // Option to generate a new randomized maze
        JMenuItem generateItem = new JMenuItem("Generate Random Maze");

        // Create the Action menu container
        JMenu actionMenu = new JMenu("Action");

        // Option to run the DFS solver and trace a path through the maze
        solveDFSMenuItem = new JMenuItem("Solve with DFS (Stack)");

        // Option to run the BFS solver and trace a path through the maze
        solveBFSMenuItem = new JMenuItem("Solve with BFS (Queue)");


        // Option to clear the current path and reset all tile states
        JMenuItem clearItem = new JMenuItem("Clear Path (Reset)");

        // Disable solver options until a maze is loaded
        solveDFSMenuItem.setEnabled(false);
        solveBFSMenuItem.setEnabled(false);

        // Load a maze from a user-selected text file
        loadItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionLoad(frame);
            }
        });

        // Generate a random maze and load it
        generateItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                actionGenerate(frame);
            }
        });

        // Run the DFS solver and display the solution path
        solveDFSMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                actionSolveDFS(frame);
            }
        });

        // Run the BFS solver and display the solution path
        solveBFSMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionSolveBFS(frame);
            }
        });

        // Clear the current path and reset the maze
        clearItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionClear();
            }
        });

        // Add the load and generate options under the File menu
        fileMenu.add(loadItem);
        fileMenu.add(generateItem);

        // Add the solve and reset options under the Action menu
        actionMenu.add(solveDFSMenuItem);
        actionMenu.add(solveBFSMenuItem);
        actionMenu.add(clearItem);

        // Mount both menus onto the menu bar
        menuBar.add(fileMenu);
        menuBar.add(actionMenu);

        // Return the fully assembled menu bar to the caller
        return menuBar;
    }

    // Builds the button panel for quick access to actions
    public JPanel createButtonPanel(JFrame frame) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.LIGHT_GRAY);

        // Buttons for all major actions
        JButton loadButton = new JButton("LoadFile");
        JButton generateButton = new JButton("Random Maze");
        solveDFSButton = new JButton("Solve (DFS)");
        solveBFSButton = new JButton("Solve (BFS)");
        JButton clearButton = new JButton("Clear Path");

        // Disable solver options until a maze is loaded
        solveDFSButton.setEnabled(false);
        solveBFSButton.setEnabled(false);

        // Load a maze from a user-selected text file
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionLoad(frame);
            }
        });

        // Generate a random maze and load it
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionGenerate(frame);
            }
        });

        // Run the DFS solver and display the solution path
        solveDFSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionSolveDFS(frame);
            }
        });

        // Run the BFS solver and display the solution path
        solveBFSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionSolveBFS(frame);
            }
        });

        // Clear the current path and reset the maze
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionClear();
            }
        });

        // Add the buttons to the panel
        buttonPanel.add(loadButton);
        buttonPanel.add(generateButton);
        buttonPanel.add(solveDFSButton);
        buttonPanel.add(solveBFSButton);
        buttonPanel.add(clearButton);

        return buttonPanel;
    }

    // enables or disables all solver controls
    private void setSolveEnabled(boolean state) {
        if (solveDFSMenuItem != null) {
            solveDFSMenuItem.setEnabled(state);
        }
        if (solveBFSMenuItem != null) {
            solveBFSMenuItem.setEnabled(state);
        }
        if (solveDFSButton != null) {
            solveDFSButton.setEnabled(state);
        }
        if (solveBFSButton != null) {
            solveBFSButton.setEnabled(state);
        }
    }

    // Loads a maze from a text file
    private void actionLoad(JFrame frame) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select a Maze File");
        int result = fileChooser.showOpenDialog(frame);

        // Proceed when user selects a file
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                engine.loadMaze(selectedFile.getAbsolutePath());
                solutionPath = null;
                setSolveEnabled(true);
                resizeWindow(frame);
                repaint();
            } catch (MazeException | FileNotFoundException ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Maze Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Generates a random maze and loads it
    private void actionGenerate(JFrame frame) {
        MazeGenerator.generateMazeFile("randomMaze.txt", 15, 20);
        try {
            engine.loadMaze("randomMaze.txt");
            solutionPath = null;
            setSolveEnabled(true);
            resizeWindow(frame);
            repaint();
        } catch (MazeException | FileNotFoundException ex) {
            JOptionPane.showMessageDialog(frame, "Error loading maze: " + ex.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Runs the DFS solver and displays the solution path
    private void actionSolveDFS(JFrame frame) {
        if (engine.getGrid() != null) {
            solutionPath = engine.solveDFS();
            if (solutionPath == null) {
                JOptionPane.showMessageDialog(frame, "Path Not Found!", "Result", JOptionPane.WARNING_MESSAGE);
            } else {
                setSolveEnabled(false);
            }
            repaint();
        }
    }

    // Runs the BFS solver and displays the solution path
    private void actionSolveBFS(JFrame frame) {
        if (engine.getGrid() != null) {
            solutionPath = engine.solveBFS();
            if (solutionPath == null) {
                JOptionPane.showMessageDialog(frame, "Path Not Found!", "Result", JOptionPane.WARNING_MESSAGE);
            } else {
                setSolveEnabled(false);
            }
            repaint();
        }
    }

    // Clears the current path and resets the maze
    private void actionClear() {
        solutionPath = null;
        setSolveEnabled(true);

        // Reset the maze
        engine.clearMaze();
        repaint();
    }

    // Adjusts the window size based on the maze dimensions
    private void resizeWindow(JFrame frame) {
        int windowWidth = engine.getCols() * TILE_SIZE + 15;
        windowWidth = Math.max(windowWidth, 600);

        int windowHeight = engine.getRows() * TILE_SIZE + 100;
        frame.setSize(windowWidth, windowHeight);
        frame.setLocationRelativeTo(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Retrieve the constructed tile grid from the solver
        MazeTile[][] grid = engine.getGrid();

        // If no maze loaded, show message
        if (grid == null) {
            g.setFont(new Font("SansSerif", Font.BOLD, 16));
            g.drawString("Please Load or Generate a Maze.", 50, 50);
            return;
        }

        // Build each tile in the maze
        for (int r = 0; r < engine.getRows(); r++) {
            for (int c = 0; c < engine.getCols(); c++) {

                // Convert column and row to pixel coordinates
                int x = c * TILE_SIZE;
                int y = r * TILE_SIZE;

                // Walls appear as dark block
                if (!grid[r][c].isPassable()) {
                    g.setColor(Color.DARK_GRAY);

                    // Open paths are white blocks
                } else if (grid[r][c]. isVisited()) {
                    g.setColor(new Color(255, 180, 180));
                } else {
                    g.setColor(Color.WHITE);
                }

                // Draw the tile background
                g.fillRect(x, y, TILE_SIZE, TILE_SIZE);

                // Light border for visual clarity
                g.setColor(Color.LIGHT_GRAY);
                g.drawRect(x, y, TILE_SIZE, TILE_SIZE);
            }
        }

        // Overlay the solution path, if one element exists
        if (solutionPath != null) {
            g.setColor(Color.BLUE);

            // Walk through the linked list of tiles that form the final route
            Node<MazeTile> current = solutionPath.getHead();

            while (current != null) {
                MazeTile tile = current.data;

                // Draw a smaller blue square inside the tile to mark the path
                int x = tile.getCol() * TILE_SIZE;
                int y = tile.getRow() * TILE_SIZE;

                g.fillRect(x + 10, y + 10, TILE_SIZE - 20, TILE_SIZE - 20);

                // Advance to the next tile in the path
                current = current.next;
            }
        }
    }

    public static void main(String[] args) {

        // Common Swing pattern ensures the GUI starts safely on the Event Dispatch Thread to avoid issues like timing bugs
        SwingUtilities.invokeLater(() -> {

            // Build the main application window
            JFrame frame = new JFrame("<-- Pathfinder -->");
            MazeSolverGUI panel = new MazeSolverGUI();

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            // Attach menu bar
            frame.setJMenuBar(panel.createMenuBar(frame));

            // Add the maze panel
            frame.add(panel, BorderLayout.CENTER);

            // Add the button panel
            frame.add(panel.createButtonPanel(frame), BorderLayout.SOUTH);

            // Initial window size
            frame.setSize(600, 500);

            // Center the window on the screen
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}