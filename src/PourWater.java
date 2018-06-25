public class PourWater {

    public int[] pourWater(int[] heights, int vol, int k) {

        while (vol > 0) {
            int left = k - 1;

            // First check if can fall on left
            int currHeight = heights[k];
            boolean filled = false;
            while (left >= -1) {
                if (left > -1 && heights[left] <= currHeight) {
                    currHeight = heights[left];
                    left--;
                } else {
                    // we either hit head of array (left == -1) or found a bump.
                    left++;

                    // find left most bump. Now going right until hit a bump because there could be several flat spots
                    while (left < k && heights[left] == heights[left + 1]) {
                        left ++;
                    }
                    if (left < k) {
                        heights[left]++;
                        filled = true;
                    }
                    // if we get here it means everything on the left is same height as heights[k].
                    break;
                }
            }

            if (filled) {
                vol--;
                continue;
            }

            // Now check right side if last drop is not filled yet.
            int right = k + 1;
            currHeight = heights[k];

            while (right <= heights.length) {
                if (right != heights.length && heights[right] <= currHeight) {
                    currHeight = heights[right];
                    right++;
                } else {
                    // hit end of array or a bump
                    right--;

                    // find left most bump. Now going right until hit a bump because there could be several flat spots
                    while (right > k && heights[right] == heights[right - 1]) {
                        right --;
                    }
                    if (right > k) {
                        heights[right]++;
                        filled = true;
                    }
                    // if we get here it means everything on the right is same height as heights[k].
                    break;
                }
            }

            if (!filled) {
                heights[k]++;
            }
            vol--;
        }

        return heights;
    }
}
