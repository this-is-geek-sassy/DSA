
// https://leetcode.com/problems/linked-list-cycle/description/

import java.util.Scanner;

class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
}

public class LinkedListCycle {
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
    private static void createCycle (ListNode head, int pos) {

        ListNode theNode = head;
        while (head.next != null)
            head = head.next;

        for (int i=0; i<pos; i++) {
            theNode = theNode.next;
        }
        System.out.println("Cycle to be created at node: " + theNode.val);
        head.next = theNode;
    }

    public static void printList (ListNode head) {
        while (head != null) {
            System.out.print(head.val + " ");
            head = head.next;
        }
        System.out.println();
    }

    public static boolean hasCycle(ListNode head) {
        
        ListNode slow = head, fast = head;

        while (fast != null) {
            slow = slow.next;
            if (fast.next != null)
                fast = fast.next.next;
            else 
                return false;
            if (slow == fast)
                return true;
        }
        return false;
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
        int pos = sc.nextInt();
        createCycle(head, pos);
        boolean ans = hasCycle(head);
        System.out.println(ans);
        sc.close();
    }
}
