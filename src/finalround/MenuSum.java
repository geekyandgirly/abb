package finalround;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuSum {
    public static List<List<Integer>> sum(float[] nums, float sum) {
        int[] intNums = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            intNums[i] = (int) (nums[i] * 100);
        }

        Arrays.sort(intNums);

        List<List<Integer>> ret = new ArrayList<>();
        sum(intNums, ret, new ArrayList<Integer>(), (int) (sum * 100), 0);
        return ret;
    }

    private static void sum(int[] nums, List<List<Integer>> ret, List<Integer> list, int remaining, int index) {
        if (remaining == 0) {
            ret.add(new ArrayList<>(list));
            return;
        }

        if (remaining < 0 || index >= nums.length) {
            return;
        }

        for (int i = index; i < nums.length; i++) {
            if (nums[i] > remaining) continue;
            list.add(nums[i]);
            sum(nums, ret, list, remaining - nums[i], i);
            if (list.size() > 0) {
                list.remove(list.size() - 1);
            }
        }
    }

    private static void printResult(List<List<Integer>> lists) {
        for (List<Integer> list : lists) {
            System.out.print("[");
            for (Integer i : list) {
                System.out.print(i + ", ");
            }
            System.out.println("]");
        }
    }


    public static void main(String[] args) {
        float[] nums = new float[] {4.11f, 3.00f, 1.33f, 2.44f, 7.55f, 6.66f, 2.72f};

        printResult(sum(nums, 5.44f));
    }

}
