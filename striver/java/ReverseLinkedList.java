
// link: https://leetcode.com/problems/reverse-linked-list/description/

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
}

public class ReverseLinkedList {
    private static ListNode insertAtHead (ListNode head, int newValue) {

        ListNode newNode = new ListNode(newValue);
        newNode.next = head;
        head = newNode;
        return head;
    }
    private static ListNode arrayToLL (int[] arr) {

        ListNode head = null;
        for (int i=arr.length-1; i>=0; i--) {
            head = insertAtHead(head, arr[i]);
        }
        return head;
    }
    public static ListNode reverseList(ListNode head) {
        Deque<ListNode> dq = new ArrayDeque<>();
        while (head != null) {
            dq.offerFirst(head);
            head = head.next;
        }
        head = dq.pollFirst();
        if (head == null)   // empty list
            return null;
        
        ListNode node = head;
        while (!dq.isEmpty()) {
            node.next = dq.removeFirst();
            node = node.next;
        }
        node.next = null;   // Important!
        return head;
    }
    public static void printList (ListNode head) {
        while (head != null) {
            System.out.print(head.val + " ");
            head = head.next;
        }
        System.out.println();
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
        ListNode head = arrayToLL(arr);
        printList(head);
        ListNode newHead = reverseList(head);
        printList(newHead);
        sc.close();
    }
}
