// link: https://leetcode.com/problems/remove-nth-node-from-end-of-list/description/

import java.util.Scanner;

public class RemoveNthElemFromList {

    public static ListNode removeNthFromEnd(ListNode head, int n) {
        
        if (head.next==null)
            return null;
        
        int counter = 0;
        ListNode temp = head;

        while (temp!= null) {
            counter++;
            temp = temp.next;
        }
        System.out.println("counter: " + counter);
        // now counter holds the count of #eleemts in a list
        if (counter == n){
            return head.next;
        }

        int x = counter - n, y=1;
        temp = head;
        while (y!=x) {
            temp = temp.next;
            y++;
        }
        if (temp.next != null)
            temp.next = temp.next.next;
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
        int n = sc.nextInt();
        removeNthFromEnd(head, n);
        
        SinglyLinkedList.printList(head);

        sc.close();
    }
}
