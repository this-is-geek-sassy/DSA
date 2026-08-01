// link: https://www.geeksforgeeks.org/problems/find-pairs-with-given-sum-in-doubly-linked-list/1

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PairSumInDLL {

    // public static ArrayList<ArrayList<Integer>> givenSumPairs(Node head, int target) {
    //     // code here
    //     ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
    //     Node tail = head;
    //     while (tail.next != null) {
    //         tail = tail.next;
    //     }
    //     Node left = head, right = tail;
        
    //     while (left != right && left != right.next) { 
    //         while (left.val + right.val != target) { 
    //             if (left.val + right.val < target)
    //                 left = left.next;
    //             else 
    //                 right = right.prev;
    //         }
    //         if (left.val + right.val == target) {
    //             ans.add(new ArrayList<>(List.of(left.val, right.val)));
    //             left = left.next;
    //             right = right.prev;
    //         }
    //     }
    //     return ans;
    // }

    public static ArrayList<ArrayList<Integer>> givenSumPairs(ListNodeDl head, int target) {
        // code here
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        ListNodeDl tail = head;
        while (tail.next != null) {
            tail = tail.next;
        }
        ListNodeDl left = head, right = tail;
        
        while (left != right && left != right.next) {
            int sum = left.val + right.val;

            if (sum == target) {
                ans.add(new ArrayList<>(List.of(left.val, right.val)));
                left = left.next;
                right = right.prev;
            } else if (sum < target) {
                left = left.next;
            } else {
                right = right.prev;
            }
        }
        return ans;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String ip = sc.nextLine();
        int target = sc.nextInt();
        // String[] ip_arr = ip.trim().substring(1, ip.length()-1).split(",");
        String[] ip_arr = ip.trim().split(" ");
        int[] arr = new int[ip_arr.length];
        int i = 0;

        for (String s: ip_arr) {
            arr[i++] = Integer.parseInt(s);
        }
        ListNodeDl head = DoublyLinkedList.arrayToLL(arr);
        DoublyLinkedList.printList(head);

        ArrayList<ArrayList<Integer>> ans = givenSumPairs(head, target);
        System.out.println(ans);
        sc.close();
    }
}
