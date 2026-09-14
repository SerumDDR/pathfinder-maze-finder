/*
    Name: Donnie Ranjel
    Date: 4/20/2026
    File Name: MyQueue.java
    Assignment: Final Programming Project
    Program Name: Pathfinder
    File Description: Custom generic queue using node links for FIFO (First-In-First-Out) behavior. Elements enter through the rear and exit through the front, mirroring the order that the solver encounters the maze tiles.
    Inputs: Generic objects of type T representing queued items.
    Outputs: Items released in the exact order they were added.
*/

public class MyQueue<T> {

    // Pointers to the start and end of the queue
    private Node<T> front, rear;

    // Initialize an empty queue
    public MyQueue() {
        this.front = this.rear = null;
    }

    // Method to inject a new item to the rear of the queue
    public void enqueue(T item) {

        // Create a new node for the incoming data
        Node<T> newNode = new Node<>(item);

        // Checks if the queue is empty
        if (this.rear == null) {
            this.front = this.rear = newNode; // Set both pointers to the new node
            return;
        }
        this.rear.next = newNode; // Link the rear to the new node
        this.rear = newNode; // Update the rear pointer to the new node
    }

    // Method to remove and return the item from the front of the queue
    public T dequeue() {

        // Check if the queue is empty
        if (this.front == null) {
            return null;
        }

        T data = this.front.data; // Capture the data from the front node
        this.front = this.front.next; // Move the front pointer to the next node

        // Reset rear to null if the queue becomes empty
        if (this.front == null) {
            this.rear = null;
        }
        return data;
    }

    // Method to check if the front pointer is empty
    public boolean isEmpty() {
        return front == null;
    }
}