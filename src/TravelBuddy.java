import java.util.*;

/**
 * You and your friends each has a list of cities you wishes to visit. If 50% of city in a friend's
 * wish list is the same as your, then you two are travel buddies. Now given a list of friends and
 * their wish list of cities to visit, output all travel buddies. Follow up: recommend up to n cities
 * from your travel buddies' wish list that's not in your wish list.
 *
 * This is basically practicing how to do intersection / exclusive of sets.
 */
public class TravelBuddy {

    public static List<Buddy> travelBuddy(Set<String> mySet, Map<String, Set<String>> friendsSets) {
        List<Buddy> buddies = new ArrayList<>();

        for (Map.Entry<String, Set<String>> f : friendsSets.entrySet()) {
            Set<String> fSet = f.getValue();
            Set<String> intersect = new HashSet<>(mySet);
            intersect.retainAll(fSet);
            if (intersect.size() > 0 && intersect.size() >= (fSet.size() + 1)/ 2) {
                buddies.add(new Buddy(f.getKey(), fSet, intersect.size()));
            }
        }

        buddies.sort(new Comparator<Buddy>() {
            @Override
            public int compare(Buddy b1, Buddy b2) {
                return b2.sim - b1.sim;
            }
        });

        return buddies;
    }

    public static LinkedHashSet<String> recommend(List<Buddy> buddies, Set<String> myCities, int n) {
        LinkedHashSet<String> rec = new LinkedHashSet<>();
        int k = 0;
        for (int i = 0; k < n && i < buddies.size(); i++) {
            Buddy b = buddies.get(i);
            Set<String> bCities = b.cities;
            Set<String> notInMyCities = new HashSet<>(bCities);
            notInMyCities.removeAll(myCities);

            if (notInMyCities.size() <= (n - k)) {
                rec.addAll(notInMyCities);
                k += rec.size();
            } else {
                for (String city : notInMyCities) {
                    if (k < n) {
                        rec.add(city);
                        k++;
                    } else {
                        break;
                    }
                }
            }
        }
        return rec;
    }

    static class Buddy {
        String name;
        Set<String> cities;
        int sim;

        Buddy(String name, Set<String> cities, int sim) {
            this.name = name;
            this.cities = cities;
            this.sim = sim;
        }
    }

    public static void main(String[] args) {
        Set<String> mySet = new HashSet<>();
        mySet.addAll(Arrays.asList(new String[]{"a", "b", "c", "d"}));

        Set<String> f2 = new HashSet<>();
        f2.addAll(Arrays.asList(new String[]{"a", "b", "f", "g"}));

        Set<String> f3 = new HashSet<>();
        f3.addAll(Arrays.asList(new String[]{"k", "p", "f", "g"}));

        Set<String> f1 = new HashSet<>();
        f1.addAll(Arrays.asList(new String[]{"a", "b", "c", "f", "p"}));

        Map<String, Set<String>> fMap = new HashMap<>();
        fMap.put("john", f1);
        fMap.put("kate", f2);
        fMap.put("pete", f3);

        List<Buddy> buddies = travelBuddy(mySet, fMap);
        System.out.print("Travel Buddies: ");
        for (Buddy b : buddies) {
            System.out.print(b.name + " ");
        }
        System.out.println();

        LinkedHashSet<String> rec = recommend(buddies, mySet, 4);
        System.out.print("Recommended Cities: ");
        for (String r : rec) {
            System.out.print(r + " ");
        }


    }
}
