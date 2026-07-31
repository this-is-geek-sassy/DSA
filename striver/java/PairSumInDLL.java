// link: https://www.geeksforgeeks.org/problems/find-pairs-with-given-sum-in-doubly-linked-list/1

import java.util.ArrayList;
import java.util.Scanner;

public class PairSumInDLL {

    public static ArrayList<ArrayList<Integer>> givenSumPairs(ListNodeDl head, int target) {
        // code here
        ListNodeDl tail = head;
        while (tail.next != null) {
            tail = tail.next;
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
        ListNodeDl head = DoublyLinkedList.arrayToLL(arr);
        DoublyLinkedList.printList(head);


        sc.close();
    }
}
