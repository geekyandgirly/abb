import java.util.Arrays;
import java.util.Comparator;

/**
#Input: A = [x1, x2, ..., xn]
# Sum T = Round(x1+x2+... +xn) ; 178.93E => 179-google 1point3acres # Output: B = [y1, y2, ...., yn]
# Constraint #1: y1+y2+...+yn = T
# Constraint #2: minimize sum(abs(diff(xi - yi)))
 */
public class RoundPrices {
    private static class Price {
        int floor;
        double diffWithFloor;
        int index;

        Price(double p, int i) {
            floor = (int) p;
            diffWithFloor = p - floor;
            index = i;
        }
    }

    static int[] roundUp(double[] arr) {
        int n = arr.length;
        Price[] prices = new Price[n];
        int[] ret = new int[n];

        double sum = 0;
        int floorSum = 0;

        for (int i = 0; i < n; i++) {
            double num = arr[i];
            sum += num;
            floorSum += (int) num;
            ret[i] = (int) num;
            prices[i] = new Price(num, i);
        }

        Arrays.sort(prices, new Comparator<Price>() {
            @Override
            public int compare(Price p1, Price p2) {
                return Double.compare(p2.diffWithFloor, p1.diffWithFloor);
            }
        });

        int roundSum = (int) Math.round(sum);
        int diff = roundSum - floorSum;

        int i = 0;
        while (diff > 0) {
            Price p = prices[i];
            ret[p.index]++;
            diff--;
            i++;
        }
        return ret;
    }

    public static void main(String[] args) {
        double[] arr = new double[] {1.2, 2.3, 3.4};
        int[] ret = roundUp(arr);
        for (int i : ret) {
            System.out.print(i + ", ");
        }
    }
}
