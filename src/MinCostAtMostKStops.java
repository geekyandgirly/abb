import java.util.*;

public class MinCostAtMostKStops {

    static class Stop {
        String name;
        int cost;
        int stops;

        public Stop(String name, int cost, int stops) {
            this.name = name;
            this.cost = cost;
            this.stops = stops;
        }
    }

    public static int minCost(List<String> flights, String src, String dest, int k) {
        Map<String, Set<Stop>> adjList = new HashMap<>();

        for (String flight : flights) {
            String[] part1 = flight.split(",");
            int cost = Integer.parseInt(part1[1]);
            String[] part2 = part1[0].split("->");
            String fromCity = part2[0];
            String toCity = part2[1];
            Stop stop = new Stop(toCity, cost, 0);
            Set<Stop> stops = adjList.getOrDefault(fromCity, new HashSet<Stop>());
            stops.add(stop);
            adjList.put(fromCity, stops);
        }

        Map<String, Integer> minCosts = new HashMap<>();
        Map<String, Integer> stops = new HashMap<>();
        Queue<Stop> q = new LinkedList<>();

        q.offer(new Stop(src, 0, 0));
        while(!q.isEmpty()) {
            Stop currStop = q.poll();
            if (currStop.name.equals(dest)) continue;
            if (currStop.stops == k + 1) continue;

            Set<Stop> nextStops = adjList.get(currStop.name);
            if (nextStops != null) {
                for (Stop nextStop : nextStops) {
                    int newCost = currStop.cost + nextStop.cost;
                    int newStops = currStop.stops + 1;
                    if (newCost < minCosts.getOrDefault(nextStop.name, Integer.MAX_VALUE)) {
                        q.offer(new Stop(nextStop.name, newCost, newStops));
                        minCosts.put(nextStop.name, newCost);
                    }
                    if (newStops < stops.getOrDefault(nextStop.name, Integer.MAX_VALUE)) {
                        q.offer(new Stop(nextStop.name, newCost, newStops));
                        stops.put(nextStop.name, newStops);
                    }
                }
            }
        }

        return minCosts.getOrDefault(dest, -1);
    }

    public static void main(String[] args) {
        List<String> flights = new ArrayList<>();
        flights.add("A->B,100");
        flights.add("A->C,400");
        flights.add("A->D,500");
        flights.add("B->C,100");
        flights.add("B->D,501");
        flights.add("C->D,101");
        flights.add("C->A,10");

        System.out.println("A to D 2 stops: " + minCost(flights, "A", "D", 2));
        System.out.println("A to D 1 stops: " + minCost(flights, "A", "D", 1));
        System.out.println("A to D 0 stops: " + minCost(flights, "A", "D", 0));
    }
}
