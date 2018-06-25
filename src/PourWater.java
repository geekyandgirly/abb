public class PourWater {

    private int findLeftIndex(int[] heights, int k) {
        int left = k;
        int height = heights[k];
        for (int i = k - 1; i >= 0; i--) {
            if (heights[i] < height) {
                left = i;
                height = heights[i];
            } else if (heights[i] > height) {
                break;
            }
        }
        return left;
    }

    private int findRightIndex(int[] heights, int k) {
        int right = k;
        int height = heights[k];
        for (int i = k + 1; i < heights.length; i++) {
            if (heights[i] < height) {
                right = i;
                height = heights[i];
            } else if (heights[i] > height) {
                break;
            }
        }
        return right;
    }

    public int[] pourWater(int[] heights, int vol, int k) {

        while (vol > 0) {
            int left = findLeftIndex(heights, k);
            if (left != k) {
                heights[left]++;
            } else {
                int right = findRightIndex(heights, k);
                heights[right]++;
            }

            vol--;
        }
        return heights;
    }
}
