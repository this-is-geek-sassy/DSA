// link: https://leetcode.com/problems/add-two-numbers/description/

import java.util.Scanner;

public class AddTwoNumbersLL {

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    
        ListNode head1 = l1, head2 = l2, tailPtr = null;
        ListNode finalList = new ListNode(Integer.MIN_VALUE);
        ListNode head = finalList;
        int carry = 0;
        while (l1!=null && l2!=null) {
            if (l1.val + l2.val + carry < 10) {
                int temp = l1.val + l2.val + carry;
                finalList.next = new ListNode(temp);
                carry = 0;
            }
            else {
                int temp = (l1.val + l2.val + carry) % 10;
                carry = (l1.val + l2.val + carry) / 10;
                finalList.next = new ListNode(temp);
            }
            tailPtr = l1;
            l1 = l1.next;
            l2 = l2.next;
            finalList = finalList.next;
        }
        if (l1 == null && carry != 0) {
            // insert 1 at the end of l1
            // tailPtr.next = new ListNode(1);
            while (carry != 0 && l2 != null) {
                if (l2.val + carry < 10) {
                    int temp = l2.val + carry;
                    finalList.next = new ListNode(temp);
                    carry = 0;
                } else {
                    int temp = (l2.val + carry) % 10;
                carry = (l2.val + carry) / 10;
                finalList.next = new ListNode(temp);
                }
                l2 = l2.next;
                finalList = finalList.next;
            }
        }
        else if (l2 == null && carry !=0) {
            while (carry != 0 && l1 != null) {
                if (l1.val + carry < 10) {
                    int temp = l1.val + carry;
                    finalList.next = new ListNode(temp);
                    carry = 0;
                } else {
                    int temp = (l1.val + carry) % 10;
                carry = (l1.val + carry) / 10;
                finalList.next = new ListNode(temp);
                }
                l1 = l1.next;
                finalList = finalList.next;
            }
            
        }
        if (carry != 0) {
            finalList.next = new ListNode(1);
        }
        if (l2!=null) {
            finalList.next = l2;
        }
        else if (l1 != null) {
            finalList.next = l1;
        }
        return head.next;
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

        // SinglyLinkedList.printList(head1);
        // SinglyLinkedList.printList(head2);
        ListNode ans = addTwoNumbers(head1, head2);
        SinglyLinkedList.printList(ans);
        sc.close();
    }
}
