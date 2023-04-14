package org.example;

import java.util.Comparator;
import java.util.List;

public class MeetingCalculator {

    public long calcOverlapped(List<Meeting> meetings) {
        meetings.sort(Comparator.comparingLong(Meeting::getStart));
        boolean[] overlaps = new boolean[meetings.size()];

        for (int i = 0; i < meetings.size() - 1; i++) {
            if (overlaps[i]) {
                continue;
            }

            Meeting m1 = meetings.get(i);

            for (int j = i + 1; j < meetings.size(); j++) {
                Meeting m2 = meetings.get(j);

                if (overlap(m1, m2)) {
                    overlaps[i] = true;
                    overlaps[j] = true;
                } else {
                    break;
                }
            }
        }

        int count = 0;
        for (boolean overlap : overlaps) {
            if (overlap) {
                count++;
            }
        }

        return count;
    }

    public boolean overlap(Meeting m1, Meeting m2) {
        if (m1.getStart() < m2.getStart()) {
            if (m1.getEnd() < m2.getStart()) {
                return false;
            }
        }

        if (m2.getStart() < m1.getStart()) {
            if (m2.getEnd() < m1.getStart()) {
                return false;
            }
        }

        return true;
    }

    public int calcMeetingMinutes(List<Meeting> meetings) {
        meetings.sort(Comparator.comparingLong(Meeting::getEnd));
        int ans = 0;
        long start = Long.MAX_VALUE;
        long end = Long.MIN_VALUE;
        Meeting prev = null;

        for (int i = 0; i < meetings.size(); ) {
            Meeting curr = meetings.get(i);

            if (curr.getEnd() <= curr.getStart()) {
                continue;
            }

            if (prev != null) {
                if (curr.getStart() <= prev.getEnd()) {
                    start = Math.min(start, curr.getStart());
                    end = Math.max(end, curr.getEnd());
                    i++;
                } else {
                    ans += Util.ePochToMinutes(end - start);
                    start = Long.MAX_VALUE;
                    end = Long.MIN_VALUE;
                    prev = null;
                    continue;
                }
            } else {
                start = curr.getStart();
                end = curr.getEnd();
                i++;
            }

            prev = curr;
        }

        if (prev != null) {
            ans += Util.ePochToMinutes(end - start);
        }

        return ans;
    }
}

