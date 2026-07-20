
// https://leetcode.com/problems/linked-list-cycle/description/

import java.util.*;

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

        if (pos < 0)
            return;
        ListNode theNode = head;
        while (head.next != null)
            head = head.next;

        for (int i=0; i<pos; i++) {
            theNode = theNode.next;
        }
        System.out.println("Cycle to be created at node: " + theNode.val);
        head.next = theNode;
    }

    public static ListNode detectCycle(ListNode head) {
        
        Set<ListNode> set = new HashSet<>();
        // if (!hasCycle(head))
        //     return null;

        // int pos = 0;
        while (head!= null) {
            if (set.contains(head)) {
                return head;
            }
            set.add(head);
            head = head.next;
        }
        return null;
    }

    public static ListNode detectCycleBetter (ListNode head) {

        if (head == null || head.next == null)
            return null;

        ListNode slow = head, fast = head;
        boolean hasLoop = false;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            
            if (slow == fast) {
                hasLoop = true;
                break;
            }
        }
        if (hasLoop == false)
            return null;
        System.out.println(slow.val);
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
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
            // set.add(fast);
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
        ListNode ans = detectCycleBetter(head);
        System.out.println(ans.val);
        sc.close();
    }
}
