/*
    Name: Donnie Ranjel
    Date: 4/20/2026
    File Name: Node.java
    Assignment: Final Programming Project
    Program Name: Pathfinder
    File Description: Building block for all custom linked structures. each node stores a single data item and a pointer to the next node in the chain, allowing lists, stacks, and queues to be assembled.
    Inputs: A generic object of type T to encapsulate.
    Outputs: None.
*/

public class Node<T> {
    public T data; // The data stored in the node
    public Node<T> next; // Reference to the next node in the list

    // Constructor to initialize the node with data and set next to null
    public Node(T data) {
        this.data = data;
        this.next = null;
    }
}