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

    private static void printList (Node head) {
        if (head == null) {
            System.out.println("Empty list!!");
        }
        System.out.println("Linked List:");
        Node pointer = head;
        while (pointer != null) {
            System.out.println("val: " + pointer.data);
            pointer = pointer.next;
        }
    }
    private static Node insertAtHead (Node head, int newValue) {

        Node newNode = new Node(newValue);
        newNode.next = head;
        head = newNode;
        return head;
    }
    private static Node deleteAtHead (Node head) {
        if (head == null) {
            System.out.println("Linkedlist already empty");
            return null;
        }
        Node tobeDeleted = head;
        head = head.next;
        // free(head);
        System.gc();
        return head;
    }
    private static Node deleteAtEnd (Node head) {
        if (head == null) {
            System.out.println("Linkedlist already empty");
            return null;
        }
        if (head.next == null) {
            // singleton linked list
            return null;
        }
        Node pointer = head;
        while (pointer.next.next != null)
            pointer = pointer.next;
        pointer.next = null;
        return head;
    }
    public static void main(String[] args) {
        // Create an array
        int[] arr = {2, 5, 8, 7};

        // Create the first node
        Node y = new Node(arr[0]);

        // // Print memory reference of node
        // System.out.println(y);

        // // Print data stored in node
        // System.out.println(y.data);

        Node head = y;
        printList(head);
        int newValue = 1;
        head = insertAtHead(head, newValue);
        printList(head);
        head = insertAtHead(head, 23);
        printList(head);
        head = deleteAtHead(head);
        printList(head);
    }
}
