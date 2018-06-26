import java.util.*;

public class Caps {

   public static List<List<Integer>> getCapWays(List<List<Integer>> collections) {
        List<List<Integer>> ways = new ArrayList<>();

        getWays(collections, collections.size(), ways, new LinkedHashSet<Integer>(), 0);

        return ways;
    }

    public static void getWays(List<List<Integer>> collections, int size, List<List<Integer>> ways, LinkedHashSet<Integer> way, int i) {
//        System.out.println("getWays");

        if (way.size() == size) {
            List<Integer> tmpList = new ArrayList<>(way);
            ways.add(tmpList);
//            printList(tmpList);
            return;
        }

        if (i == size) {
            return;
        }

        List<Integer> collection = collections.get(i);
        for (int j = 0; j < collection.size(); j++) {
            if (way.add(collection.get(j))) {
                getWays(collections, size, ways, way, i + 1);

                // backtrack
                way.remove(collection.get(j));
            }
        }
    }

    private static void printListOfLists(List<List<Integer>> lists) {
        for (List<Integer> list : lists) {
            printList(list);
        }
    }

    private static void printList(List<Integer> list) {
        System.out.print("[");
        for (Integer i : list) {
            System.out.print(i + ", ");
        }
        System.out.println("]");
    }

    public static void main(String[] args) {
        List<List<Integer>> collections = new ArrayList<>();
        collections.add(new ArrayList(Arrays.asList(new Integer[] {1,2,3})));
        collections.add(new ArrayList(Arrays.asList(new Integer[] {2,3})));
        collections.add(new ArrayList(Arrays.asList(new Integer[] {1,4,5})));

        System.out.println();
        printListOfLists(getCapWays(collections));

        System.out.println("counted ways: " + getCapWays(collections));
    }
}
