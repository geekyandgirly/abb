import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Giving a directed graph, find minimum set of vertices such that, from these vertices, you can visit all other
 * vertices in the graph.
 */
public class MinVerticesTraverseGraph {

    public static void dfs(int[][] edges, Set<Integer> res, int start, int from, Set<Integer> visited, Set<Integer> curVisited, int n) {
        visited.add(from);
        curVisited.add(from);

        for (int to = 0; to < n; to++) {
            if (edges[from][to] == 1) {
                if (res.contains(to) && to != start) {
                    res.remove(to);
                }
                if (!curVisited.contains(to)) {
                    dfs(edges, res, start, to, visited, curVisited, n);
                }
            }
        }

    }

    public static Set<Integer> getMinVertices(int[][] edges, int n) {
        Set<Integer> visited = new HashSet<>();
        Set<Integer> res = new HashSet<>();

        for (int i = 0; i < n; i++) {
            if (!visited.contains(i)) {
                res.add(i);
                visited.add(i);
                Set<Integer> curVisited = new HashSet<>();
                dfs(edges, res, i, i, visited, curVisited, n);
            }
        }
        return res;
    }

    public static void main(String args[]) {
//        int[][] edges = new int[][] {
//            new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            new int[] {0, 0, 0 ,0, 0, 0, 0, 0, 0, 1},
//            new int[] {0, 0, 0, 1, 0, 1, 0, 1, 0, 0},
//            new int[] {0, 0, 0, 0, 0, 0 ,0, 0, 1, 0},
//            new int[] {0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
//            new int[] {0, 0, 0, 0, 0, 0, 1, 0, 0 ,0},
//            new int[] {0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
//            new int[] {0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
//            new int[] {0, 0, 0, 1, 0, 0, 1, 0, 0, 0}
//        };
//
//        Set<Integer> res = getMinVertices(edges, 10);
//        for (int i : res) {
//            System.out.print(i + " ");
//        }
//        System.out.println();

        //  0->1->0, 2->3->2->1
        int[][] edges1 = new int[][] {
            new int[] {0, 1, 0, 0},
            new int[] {1, 0, 0, 0},
            new int[] {0, 1, 0, 1},
            new int[] {0, 0, 1, 0}
        };

        Set<Integer> res1 = getMinVertices(edges1, 4);
        for (int i : res1) {
            System.out.print(i + " ");
        }
    }
}
