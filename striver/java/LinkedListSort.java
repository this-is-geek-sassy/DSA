
// link: https://leetcode.com/problems/sort-list/

import java.util.Scanner;

public class LinkedListSort {

    private static ListNode merge (ListNode head1, ListNode head2) {

        ListNode head = new ListNode(Integer.MIN_VALUE), temp1 = head1, temp2 = head2, temp = head;

        while (temp1 != null && temp2 != null)
        {
            if (temp1.val <= temp2.val) {
                temp.next = temp1;
                temp1 = temp1.next;
                temp = temp.next;
            } else {
                temp.next = temp2;
                temp2 = temp2.next;
                temp = temp.next;
            }
        }
        temp.next = (temp1 != null) ? temp1 : temp2;
        return head.next;
    }
    
    public static ListNode MergeSort(ListNode head /*, ListNode tail */) {

        if (head.next == null)
            return head;
        // mid calculation:
        ListNode slow = head, fast = head;

        while (fast.next != null && fast.next.next != null) {
            // if (fast.next.next.next == null)
            //     break;
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode head2 = slow.next;
        slow.next = null;
        ListNode newHead = MergeSort(head);
        ListNode newHead2 = MergeSort(head2);

        return merge(newHead, newHead2);

    }
    public static ListNode sortList(ListNode head) {
        
        if (head == null || head.next == null)
            return head;

        // tail pointer: 
        // ListNode tail = head;
        // while (tail.next != null) 
        //     tail = tail.next;
        return MergeSort(head);
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
        
        head = sortList(head);
        SinglyLinkedList.printList(head);

        sc.close();
    }
}
