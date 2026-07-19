
import java.util.Scanner;


// link: https://leetcode.com/problems/delete-node-in-a-linked-list/description/
class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
}
public class DeleteNodeInLinkedList {

    private static ListNode insertAtHead (ListNode head, int newValue) {

        ListNode newNode = new ListNode(newValue);
        newNode.next = head;
        head = newNode;
        return head;
    }
    private static ListNode arrayToLL (int[] arr) {

        ListNode head = null;
        for (int i=arr.length-1; i>=0; i++) {
            head = insertAtHead(head, arr[i]);
        }
        return head;
    }
    public void deleteNode(ListNode node) {
        
        ListNode nextNode = node.next;
        // int temp = node.val;
        node.val = nextNode.val;
        // nextNode.val = temp;
        node.next = nextNode.next;
        nextNode.next = null;
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
        int node = sc.nextInt();

        sc.close();
    }
}
