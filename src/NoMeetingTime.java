import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Given a list of meetings (startTime, endTime), find a list of intervals that are available for meetings.
 * Basically find free time from given schedules.
 */
public class NoMeetingTime {
    static class Interval {
        int start;
        int end;
        Interval(int s, int e) { start = s; end = e; }
    }

    public static ArrayList<Interval> getFreeTime(Interval[] intervals) {
        ArrayList<Interval> ret = new ArrayList<>();
        if (intervals == null || intervals.length == 0) return ret;

        Arrays.sort(intervals, new Comparator<Interval>() {
            @Override
            public int compare(Interval a, Interval b) {
                return a.start == b.start ? a.end - b.end : a.start - b.start;
            }
        });

        for (Interval inter : intervals) {
            System.out.print("[" + inter.start + ", " + inter.end + "], ");
        }

        int nextFreeTime = intervals[0].end;

        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i].start > nextFreeTime) {
                ret.add(new Interval(nextFreeTime, intervals[i].start));
            }

            if (intervals[i].end > nextFreeTime) {
                nextFreeTime = intervals[i].end;
            }
        }

        return ret;
    }

    public static void main(String[] args) {
        Interval[] intervals = new Interval[] {
                new Interval(1,3),
                new Interval(6,7),
                new Interval(2,4),
                new Interval(2,3),
                new Interval(9,12)
        };

        ArrayList<Interval> ret = getFreeTime(intervals);
        System.out.println();
        for (Interval inter : ret) {
            System.out.print("[" + inter.start + ", " + inter.end + "], ");
        }
    }
 }
