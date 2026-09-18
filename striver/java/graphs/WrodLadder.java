package graphs;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class WrodLadder {

    public static int ladderLength(String beginWord, String endWord, List<String> wordList) {
        
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String beginWord = sc.nextLine();
        String endWord = sc.nextLine();
        String listInp = sc.nextLine();

        String[] listAsItIs = listInp.trim().substring(1, listInp.length()-1).split(",");
        List<String> realList = new ArrayList<>();

        for (String s: listAsItIs) {
            String temp = s.substring(1, s.length()-1);
            // System.out.println(realList[i-1] + " ");
            realList.add(temp);
        }
    }
}
