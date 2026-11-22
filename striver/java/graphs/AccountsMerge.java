// https://leetcode.com/problems/accounts-merge/

package graphs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class AccountsMerge {

    public static List<List<String>> accountsMerge(List<List<String>> accounts) {
        
        // String[] names = new String[accounts.size()];
        // int i=0;
        // for (List<String> account: accounts) {
        //     names[i++] = account.get(0);
        // }
        DisjointSet u = new DisjointSet(accounts.size());
        Map<String, Integer> mem = new HashMap<>();
        
        for (int i = 0; i<accounts.size(); i++) {
            String name = accounts.get(i).get(0);
            for (int j=1; j<accounts.get(i).size(); j++) {
                
                String email = accounts.get(i).get(j);
                Integer acc = mem.get(email);
                if (acc != null) {
                    // Integer acc = mem.get(email);
                    u.unionBySize(i, acc);
                }
                else {
                    mem.put(email, i);
                }
            }
        }
        Map<Integer, List<String>> groups = new HashMap<>();

        for (Map.Entry<String, Integer> m: mem.entrySet()) {
            Integer acc = m.getValue();
            String email = m.getKey();
            int root = u.findRoot(acc);
            groups.computeIfAbsent(root, k -> new ArrayList<>()).add(email);
        }
        List<List<String>> result = new ArrayList<>();
        for (Map.Entry<Integer, List<String>> g : groups.entrySet()) {
            List<String> emails = g.getValue();
            Collections.sort(emails);
            String name = accounts.get(g.getKey()).get(0);
            
            List<String> merged = new ArrayList<>(emails.size()+1);
            merged.add(name);
            merged.addAll(emails);
            result.add(merged);
        }
        return result;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine().trim();
        String[] inputParts = input.substring(1, input.length()-1).split("],");
        int i = 0, j=0;

        List<List<String>> accounts = new ArrayList<>();

        for (String inputPart: inputParts) {
            if (inputPart.endsWith("]"))
                inputPart = inputPart.substring(1, inputPart.length()-1);
            else
                inputPart = inputPart.substring(1);
            String[] mails = inputPart.split(",");
            accounts.add(new ArrayList<>());

            for (String elem: mails) {
                accounts.get(i).add(elem.substring(1, elem.length()-1));
            }
            i++;
        }
        sc.close();
        List<List<String>> ans = accountsMerge(accounts);
        System.out.println(ans);
    }
}
