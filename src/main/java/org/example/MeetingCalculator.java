package org.example;

import java.util.Comparator;
import java.util.List;

public class MeetingCalculator {
    public int calcMeetingMinutes(List<Meeting> meetings) {
        meetings.sort(Comparator.comparingLong(o -> o.end));
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

