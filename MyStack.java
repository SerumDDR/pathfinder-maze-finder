/*
    Name: Donnie Ranjel
    Date: 4/20/2026
    File Name: MyStack.java
    Assignment: Final Programming Project
    Program Name: Pathfinder
    File Description: Generic stack built on node links to use last-in, first-out(LIFO) behavior.
    Inputs: Generic objects of type T representing pushed items.
    Outputs: Items released in reverse order of insertion.
*/

public class MyStack<T> {

    // Pointer to the most recently pushed item
    private Node<T> top;

    // Initialize an empty stack
    public MyStack() {
        this.top = null;
    }

    // Method to add an item to the top of the stack
    public void push(T item) {
        Node<T> newNode = new Node<>(item);
        newNode.next = top;
        top = newNode;
    }

    // Method to remove and return the item at the top of the stack
    public T pop() {
        if (isEmpty()) {
            return null;
        }
        T data = top.data; // Capture the data from the top node
        top = top.next; // Move the top pointer to the next node
        return data;
    }

    // Method to check if the stack is empty
    public boolean isEmpty() {
        return top == null;
    }
}