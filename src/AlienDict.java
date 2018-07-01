import java.util.*;

/**
 * https://leetcode.com/problems/alien-dictionary/description/
 */
public class AlienDict {
    /**
     * METHOD I: BFS
     */
    public static String alienOrderBfs(String[] words) {
        if (words == null || words.length == 0) return "";

        String prev = words[0];
        if (words.length == 1) return prev;

        Map<Character, Set<Character>> adjList = new HashMap<>();
        LinkedHashMap<Character, Integer> inDegrees = new LinkedHashMap<>();

        int i = 1;
        while (i < words.length) {
            String next = words[i];
            int len = Math.min(prev.length(), next.length());

            for (int j = 0; j < len; j++) {
                char from = prev.charAt(j);
                char to = next.charAt(j);
                if (from != to) {
                    // found first chars that are different in the two words
                    Set<Character> tos = adjList.getOrDefault(from, new HashSet<Character>());
                    tos.add(to);
                    adjList.put(from, tos);

                    inDegrees.put(to, inDegrees.getOrDefault(to, 0) + 1);

                    // Do not break if want to deal with characters whose order cannot be determine. Instead use
                    // a boolean foundFirst to control the flow.
                    break;

                } else {
                    adjList.put(from, adjList.getOrDefault(from, new HashSet<Character>()));
                    inDegrees.put(to, inDegrees.getOrDefault(to, 0));
                }
            }

            // deal with rest of prev's characters if prev is longer. Only need to do this if we need to deal with
            // character that cannot be determined (no in or out edges).
//            if (prev.length() > next.length()) {
//                for (int k = len; k < prev.length(); k++) {
//                    char c = prev.charAt(k);
//                    adjList.put(c, adjList.getOrDefault(c, new HashSet<Character>()));
//                    inDegrees.put(c, inDegrees.getOrDefault(c, 0));
//                }
//            }
//
//            if (i == words.length - 1) {
//                if (next.length() > prev.length()) {
//                    for (int k = len; k < next.length(); k++) {
//                        char c = next.charAt(k);
//                        adjList.put(c, adjList.getOrDefault(c, new HashSet<Character>()));
//                        inDegrees.put(c, inDegrees.getOrDefault(c, 0));
//                    }
//                }
//            }

            prev = next;
            i++;
        }

        StringBuilder builder = new StringBuilder();
        Queue<Character> q = new LinkedList<>();

        for (Map.Entry<Character, Integer> degree : inDegrees.entrySet()) {
            if (degree.getValue() == 0) {
                q.offer(degree.getKey());
            }
        }

        while (!q.isEmpty()) {
            char c = q.poll();
            builder.append(c);

            Set<Character> nextCs = adjList.get(c);
            if (nextCs != null) {
                for (char nextC : nextCs) {
                    int degree = inDegrees.get(nextC) - 1;
                    inDegrees.put(nextC, degree);
                    if (degree == 0) {
                        q.offer(nextC);
                    }
                }
            } else {
                // This is the last character that has no out-degrees hence no adjList.
                System.out.println("this should be last character in the dict: " + c);
            }
        }

        return builder.toString();
    }

    /**
     * METHOD II:  DFS
     *
     * DFS fashion using adjacent lists. The adjacent list is a set c1 -> {c2, c22, c222}. Have adjacent list in
     * both orders and keep checking in both directions for every node.
     *
     * This solution does not pass ["zy","zx"] test case on leetcode because z is discarded (leetcode answer keeps z).
     */
    public static String alienOrderDfs(String[] words) {
        if (words.length == 0) {
            return "";
        }

        String prev = words[0];
        if (words.length == 1) {
            return prev;
        }

        Map<Character, Set<Character>> ord = new HashMap<>();
        Map<Character, Set<Character>> rev = new HashMap<>();
        LinkedList<Character> list = new LinkedList<>();

        int i = 1;
        String next;
        while (i < words.length) {
            next = words[i];
            int len = Math.min(prev.length(), next.length());
            for (int j = 0; j < len; j++) {
                char c1 = prev.charAt(j);
                char c2 = next.charAt(j);
                if (c1 != c2) {
                    // in order
                    // first check if there already exists c2 > c1 which means invalid mapping (cycle).
                    if (ord.containsKey(c2)) {
                        Set<Character> opo = ord.get(c2);
                        if (opo.contains(c1)) {
                            return "";
                        }
                    }
                    Set<Character> set1 = ord.get(c1);
                    if (set1 == null) {
                        set1 = new HashSet<>();
                    }
                    set1.add(c2);
                    ord.put(c1, set1);

                    // reverse order
                    Set<Character> set2 = rev.get(c2);
                    if (set2 == null) {
                        set2 = new HashSet<>();
                    }
                    set2.add(c1);
                    rev.put(c2, set2);
                }
            }
            prev = next;
            i++;
        }

        // the case where every word is the same.
        if (ord.isEmpty()) {
            return words[0];
        }

        Set<Character> ordKeys = ord.keySet();
        Set<Character> revKeys = rev.keySet();

        for (Character c1 : ordKeys) {
            Set<Character> set1 = ord.get(c1);
            if (list.isEmpty()) {
                list.add(c1);
            }

            for (Character c2 : set1) {
                if (list.getLast() == c1) {
                    list.add(c2);
                    break;
                }
            }

            if (revKeys.contains(c1)) {
                Set<Character> set2 = rev.get(c1);
                for (Character c0 : set2) {
                    if (list.getFirst() == c1) {
                        list.addFirst(c0);
                        break;
                    }
                }
            }
        }

        StringBuilder b = new StringBuilder();
        for (Character c : list) {
            b.append(c);
        }
        return b.toString();
    }

    public static void main(String[] args) {
        String[] words = new String[] {"wrt", "wrf", "erx", "ett", "rftt"};

        // Not that "x" is ignored since its order cannot be determined.
        System.out.println(alienOrderBfs(words));
    }
}
