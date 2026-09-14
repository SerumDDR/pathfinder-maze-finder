/*
    Name: Donnie Ranjel
    Date: 4/20/2026
    File Name: MyLinkedList.java
    Assignment: Final Programming Project
    Program Name: Pathfinder
    File Description: Generic linked list to record the solver's movement history. Each node stores a tile and links forward to the next step, forming a complete breadcrumb trail throughout the path taken through the maze.
    Inputs: Generic objects of type T representing each step taken through the tiles in the path.
    Outputs: A sequential chain of nodes that can be displayed to show the final path.

    Change Log:
    ---------------------------------------------------------------------------------------------------------
    |  Date   |   Description
    ---------------------------------------------------------------------------------------------------------
    |  4/20   |   Initial creation of the generic linked list structure. 
    |  5/6    |   Refactored to support visual GUI rendering via getHead and removed displayPath() method.
    |  5/10   |   Added addFirst() to optimize path reconstruction order.
*/

public class MyLinkedList<T> {
    private Node<T> head; // Pointer to the first node in the list
    private Node<T> tail; // Pointer to the last node in the list

    // Initialize an empty linked list
    public MyLinkedList() {
        this.head = this.tail = null;
    }

    // Method to add an item to the end of the linked list
    public void add(T item) {
        Node<T> newNode = new Node<>(item); // Create a new node with data

        // Check if list is empty
        if (head == null) {
            head = tail = newNode; // Set both head and tail to the new node
          
          // Otherwise, link the new node to the end and update the tail pointer
        } else {
            tail.next = newNode;
            tail = newNode;
        }
    }

    // Method to add an item to the beginning of the linked list
    public void addFirst(T item) {
        Node<T> newNode = new Node<>(item);

        if (head == null) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head = newNode;
        }
    }

    // Getter method to retrieve the head of the linked list
    public Node<T> getHead() {
        return head;
    }
}