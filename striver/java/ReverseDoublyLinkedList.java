
import java.util.Scanner;

// link: https://takeuforward.org/data-structure/reverse-a-doubly-linked-list

public class ReverseDoublyLinkedList {

    public static ListNodeDl reverseList (ListNodeDl head) {

        if (head == null || head.next == null) {
            return head;
        }
        ListNodeDl newHead = reverseList(head.next);
        head.next.next = head;
        head.prev = head.next;
        head.next = null;
        return newHead;
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
        ListNodeDl head = DoublyLinkedList.arrayToLL(arr);
        head = reverseList(head);
        DoublyLinkedList.printList(head);
        sc.close();
    }
}
