// https://leetcode.com/problems/odd-even-linked-list/description/


import java.util.*;


public class OddEvenLinkedList {
    
    public static ListNode oddEvenList(ListNode head) {
        
        if (head==null || head.next==null || head.next.next==null) {
            // at least 3 nodes should be there in the LL
            return head;
        }
        ListNode odd = head, even = null;
        // even = SinglyLinkedList.copyLL(head, even).next;
        even = odd.next;
        ListNode even_head = even;

        while (odd.next != null /*&& odd.next.next != null*/ && even.next!=null /*&& even.next.next!=null*/) {
            odd.next = odd.next.next;
            odd = odd.next;

            even.next = even.next.next;
            even = even.next;
        }
        odd.next = even_head;
        return head;
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

        head = oddEvenList(head);
        SinglyLinkedList.printList(head);

        sc.close();
    }
}
