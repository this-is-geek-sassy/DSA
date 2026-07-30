// link: https://leetcode.com/problems/delete-the-middle-node-of-a-linked-list/description/

import java.util.Scanner;

public class DeleteMiddleNodeofLL {

    public static ListNode deleteMiddle(ListNode head) {
        if (head.next == null) {
            return null;
        }
        ListNode slow = head, fast = head;

        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode temp = head;
        // slow should point to the exxact middle for odd length now
        if (fast.next == null) {
            while (temp.next != slow) {
                temp = temp.next;
            }
            temp.next = slow.next;
            return head;
        }
        else {
            slow.next = slow.next.next;
            return head;
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

        ListNode head = SinglyLinkedList.arrayToLL(arr);
        // int n = sc.nextInt();
        
        
        SinglyLinkedList.printList(head);

        sc.close();
    }
}
