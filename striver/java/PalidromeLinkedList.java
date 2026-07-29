// https://leetcode.com/problems/palindrome-linked-list/description/

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

public class PalidromeLinkedList {

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
    public static void printList (ListNode head) {
        while (head != null) {
            System.out.print(head.val + " ");
            head = head.next;
        }
        System.out.println();
    }
    public static boolean isPalindrome(ListNode head) {
        
        Deque<ListNode> dq = new ArrayDeque<>();
        ListNode node = head;

        while (node != null) {
            dq.offerLast(node);
            node = node.next;
        }
        ListNode left = null, right = null;
        while (!dq.isEmpty()) {
            if (dq.size() == 1)
                return true;
            left = dq.pollFirst();
            right = dq.pollLast();
            if (left.val != right.val)
                return false;
        }
        return left.val == right.val;
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

        boolean ans = isPalindrome(head);
        System.out.println(ans);

        sc.close();
    }
}
