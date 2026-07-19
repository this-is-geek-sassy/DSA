
// link: https://leetcode.com/problems/middle-of-the-linked-list/description/

import java.util.Scanner;
class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
}
public class MiddleOfLinkedList {
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
    public static ListNode middleNode(ListNode head) {
        ListNode slow = head, fast = head;

        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        if (fast.next == null) {
            return slow;
        } else {
            return slow.next;
        }
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
        ListNode middle = middleNode(head);
        System.out.println(middle.val);
        sc.close();
    }
}
