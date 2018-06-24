import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/ip-to-cidr/description/
 *
 * Let's start with a simple example: say we want to cover 8 ips starting from 8, so the range is 8 - 15, i.e. starting
 * at 1000 and going up to 1111. We can see that the 3 0s on the right contains the entire range: 255.0.0.1000/29. The
 * mask is calculated as 32-log(8) where 8 is the first 1 from right.
 *
 * Now lets say we only want to go up to 14, i.e. 1000 - 1110. We have to pick the min of log(8) and log(7), hence the
 * mask is calcuate as 32 - min(3, 2), and we get our first range 255.0.9.1000/30 which adds 3 ips to our range, and
 * our next start ip is 8 + 3 + 1 = 12, i.e. 1100. Now we repeat from this new start ip till we get to 14.
 */
public class Cidr {

    private long ipToLong(String ipStr) {
        long[] ip = new long[4];
        String[] parts = ipStr.split("\\.");
        for (int i = 0; i < 4; i++) {
            ip[i] = Long.valueOf(parts[i]);
        }

        return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
    }

    private String longToIp(long longIP) {
        return String.valueOf(longIP >>> 24) +
                "." +
                String.valueOf((longIP & 0x00FFFFFF) >>> 16) +
                "." +
                String.valueOf((longIP & 0x0000FFFF) >>> 8) +
                "." +
                String.valueOf(longIP & 0x000000FF);
    }

    public List<String> ipToCIDR(String startIp, int n) {
        List<String> ret = new ArrayList<>();

        long start = ipToLong(startIp);
        long end = start + n - 1;

        while (start <= end) {
            long firstOne = start & -start;

            int diff = (int) (Math.log(firstOne) / Math.log(2));
            int currDiff = (int) (Math.log(end - start + 1) / Math.log(2));

            int minDiff = Math.min(diff, currDiff);
            int mask = 32 - minDiff;

            String ip = longToIp(start);
            ret.add(ip + "/" + mask);

            start += Math.pow(2, minDiff);
        }

        return ret;
    }
}