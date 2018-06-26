import java.util.*;

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
