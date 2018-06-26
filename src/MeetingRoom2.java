import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Given an array of meeting time intervals consisting of start and end times [[s1,e1],[s2,e2],...] (si < ei), find the minimum number of conference rooms required.
 *
 * Example 1:
 *
 * Input: [[0, 30],[5, 10],[15, 20]]
 * Output: 2
 * Example 2:
 *
 * Input: [[7,10],[2,4]]
 * Output: 1
 */
public class MeetingRoom2 {
    static class Interval {
        int start;
        int end;
        Interval() { start = 0; end = 0; }
        Interval(int s, int e) { start = s; end = e; }
    }

    public int minMeetingRooms(Interval[] intervals) {
        if (intervals == null || intervals.length == 0) return 0;

        Arrays.sort(intervals, new Comparator<Interval>() {
            @Override
            public int compare(Interval a, Interval b) {
                return a.start - b.start;
            }
        });

        PriorityQueue<Integer> q = new PriorityQueue<>();
        q.offer(intervals[0].end);
        int room = 1;

        for (int i = 1; i < intervals.length; i++) {
            int nextFreeRoomTime = q.isEmpty() ? Integer.MAX_VALUE : q.peek();
            if (intervals[i].start < nextFreeRoomTime) {
                // no free room
                room ++;
            } else {
                // no new room added. change lastMeetingEnd time
                if (!q.isEmpty())
                    q.poll();
            }
            q.offer(intervals[i].end);
        }

        return room;
    }
}
