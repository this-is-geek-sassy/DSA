// link: https://leetcode.com/problems/intersection-of-two-linked-lists/description/

import java.util.*;
// import java.util.Scanner;

public class IntersectionOfTwoLL {

    public static void createIntersection(ListNode head1, ListNode head2, int indexInHead1) {
        if (head1 == null || head2 == null)
            return;

        // Find the node in head1 where intersection should begin.
        ListNode intersection = head1;
        for (int i = 0; i < indexInHead1 && intersection != null; i++) {
            intersection = intersection.next;
        }

        if (intersection == null)
            return; // Invalid index

        // Find the tail of head2.
        ListNode tail = head2;
        while (tail.next != null) {
            tail = tail.next;
        }

        // Join head2 to head1.
        tail.next = intersection;
    }

    public static ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        
        Set<ListNode> set = new HashSet<>();
        ListNode node = headA;
        while (node != null) {
            set.add(node);
            node = node.next;
        }

        node = headB;
        while (node != null) {
            if (set.contains(node))
                return node;
            node = node.next;
        }
        return null;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String ip = sc.nextLine();
        String[] ip_arr = ip.trim().substring(1, ip.length()-1).split(",");
        int[] arr = new int[ip_arr.length];
        int i = 0;

        for (String s: ip_arr) {
            arr[i++] = Integer.parseInt(s);
        }

        ListNode head1 = SinglyLinkedList.arrayToLL(arr);
        // int n = sc.nextInt();
        
        ip = sc.nextLine();
        ip_arr = ip.trim().substring(1, ip.length()-1).split(",");
        arr = new int[ip_arr.length];
        i = 0;
        for (String s: ip_arr) {
            arr[i++] = Integer.parseInt(s);
        }

        ListNode head2 = SinglyLinkedList.arrayToLL(arr);

        SinglyLinkedList.printList(head1);
        SinglyLinkedList.printList(head2);

        int skipA = sc.nextInt();

        createIntersection(head1, head2, skipA);
        SinglyLinkedList.printList(head1);
        SinglyLinkedList.printList(head2);
        sc.close();
    }
}
