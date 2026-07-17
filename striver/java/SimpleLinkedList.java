// link: https://takeuforward.org/linked-list/linked-list-introduction
// Node class represents a node in the linked list
class Node {
    int data;      // Data value
    Node next;     // Pointer to next node

    // Constructor with data and next
    Node(int data1, Node next1) {
        data = data1;
        next = next1;
    }

    // Constructor with only data
    Node(int data1) {
        data = data1;
        next = null;
    }

    @Override
    public String toString() {
        return "data: " + this.data + " next: " + this.next;
    }
}

public class SimpleLinkedList {
    
    public static void main(String[] args) {
        // Create an array
        int[] arr = {2, 5, 8, 7};

        // Create the first node
        Node y = new Node(arr[0]);

        // Print memory reference of node
        System.out.println(y);

        // Print data stored in node
        System.out.println(y.data);
    }
}
