public class PaintHouse {
    public int minCostII(int[][] costs) {
        int min1 = 0, min2 = 0;
        int minColor = -1;

        for (int i = 0; i < costs.length; i++) {
            int currMin1 = Integer.MAX_VALUE, currMin2=currMin1, currMinColor = -1;
            for (int j = 0; j < costs[i].length; j++) {
                int cost = costs[i][j];
                costs[i][j] = (minColor == j ? min2 + cost : min1 + cost);

                if (costs[i][j] < currMin1) {
                    currMin2 = currMin1;
                    currMin1 = costs[i][j];
                    currMinColor = j;
                } else if (costs[i][j] < currMin2) {
                    currMin2 = costs[i][j];
                }
            }

            min1 = currMin1;
            min2 = currMin2;
            minColor = currMinColor;
        }

        return min1;
    }
}
