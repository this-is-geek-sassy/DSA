// package LinkedList;

public class SinglyLinkedList {
    public static ListNode insertAtHead (ListNode head, int newValue) {

        ListNode newNode = new ListNode(newValue);
        newNode.next = head;
        head = newNode;
        return head;
    }
    public static ListNode arrayToLL (int[] arr) {

        ListNode head = null;
        for (int i=arr.length-1; i>=0; i--) {
            head = insertAtHead(head, arr[i]);
        }
        return head;
    }
    public static void printList (ListNode head) {
        while (head != null) {
            System.out.print(head.val + " ");
            head = head.next;
        }
        System.out.println();
    }
    public static ListNode copyLL (ListNode head1, ListNode head2) {
        if (head2 == null)
            return null;
        
        head1 = new ListNode(-999);
        ListNode head1_cpy = head1;
        while (head2 != null) {
            head1.next = new ListNode(head2.val);
            head1 = head1.next;
            head2 = head2.next;
        }
        return head1_cpy.next;
    }
}
