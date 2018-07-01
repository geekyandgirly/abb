import java.util.*;

/**
 * Similar to Alien Dict. Give each person's preference list, output overall preference list without changing personal
 * preference order.
 *
 * input:
 *  [[3, 5, 7, 9],
 *   [2, 3, 8],
 *   [5, 8]]
 *
 * output:
 *  [2, 3, 5, 8, 7, 9]
 *
 *  Method: BFS, starting with the node with highest preference (i.e. 0 in-degree).
 */
public class PreferenceList {
    public static List<Integer> getPreference(List<List<Integer>> prefs) {
        Map<Integer, Integer> inDegrees = new HashMap<>();
        Map<Integer, Set<Integer>> adjLists = new HashMap<>();

        for (List<Integer> pref : prefs) {
            int prev = pref.get(0);
            inDegrees.put(prev, inDegrees.getOrDefault(prev, 0));

            for (int i = 1; i < pref.size(); i++) {
                int next = pref.get(i);
                Set<Integer> set = adjLists.get(prev);
                if (set == null) {
                    set = new HashSet<>();
                }
                set.add(next);
                adjLists.put(prev, set);

                // what if there's double count?
                inDegrees.put(next, inDegrees.getOrDefault(next, 0) + 1);
                prev = next;
            }
        }

        Queue<Integer> q = new LinkedList<>();
        for (Integer key : inDegrees.keySet()) {
            if (inDegrees.get(key) == 0) {
                q.offer(key);
            }
        }

        List<Integer> res = new ArrayList<>();
        while (!q.isEmpty()) {
            Integer from = q.poll();
            res.add(from);

//            System.out.println("poll " + from + " degree " + inDegrees.get(from));

            Set<Integer> tos = adjLists.get(from);
            if (tos != null) {
                for (int to : tos) {
                    int degree = inDegrees.get(to) - 1;
//                    System.out.println("adjList for " + from + ": to " + to + " degree " + inDegrees.get(from));
                    inDegrees.put(to, degree);
                    if (degree == 0) {
                        q.offer(to);
                    }
                }
            }
        }

        return res;
    }

    public static void main(String[] args) {
        List<List<Integer>> prefs = new ArrayList<>();
        prefs.add(new ArrayList<>(Arrays.asList(new Integer[]{3,5,7,9})));
        prefs.add(new ArrayList<>(Arrays.asList(new Integer[]{2,3,8})));
        prefs.add(new ArrayList<>(Arrays.asList(new Integer[]{5,8})));

        List<Integer> res = getPreference(prefs);
        for(int r : res) {
            System.out.print(r + "->");
        }
    }
}
