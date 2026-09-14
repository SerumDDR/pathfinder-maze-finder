/*
    Name: Donnie Ranjel
    Date: 4/20/2026
    File Name: MazeException.java
    Assignment: Final Programming Project
    Program Name: Pathfinder
    File Description: Custom exception class for handling maze-related errors.
    Inputs: A string message describing the error.
    Outputs: A MazeException object.
*/

public class MazeException extends Exception {
    public MazeException(String message) {
        super(message);
    }
}