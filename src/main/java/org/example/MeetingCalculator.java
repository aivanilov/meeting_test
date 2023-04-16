package org.example;

import lombok.extern.log4j.Log4j2;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Log4j2
public class MeetingCalculator {

    public long calcOverlapped(List<Meeting> meetings) {
        log.info(Messages.ENTER_WITH_LIST, meetings.size());
        Set<Meeting> overlaps = new HashSet<>();

        try {
            meetings.sort(Comparator.comparingLong(Meeting::getStart));
            log.debug(Messages.SORTED, meetings);
            overlaps = getOverlappedMeetings(meetings);
        } catch (Exception e) {
            log.error(Messages.ERROR, e.getMessage());
        }

        log.info(Messages.EXIT_WITH_RESULT, overlaps.size());
        return overlaps.size();
    }

    private Set<Meeting> getOverlappedMeetings(List<Meeting> meetings) {
        log.debug(Messages.ENTER_WITH_LIST, meetings.size());
        Set<Meeting> overlaps = new HashSet<>();

        try {
            for (int i = 0; i < meetings.size() - 1; i++) {
                Meeting m1 = meetings.get(i);

                if (overlaps.contains(m1)) {
                    continue;
                }

                for (int j = i + 1; j < meetings.size(); j++) {
                    Meeting m2 = meetings.get(j);

                    if (overlap(m1, m2)) {
                        overlaps.add(m1);
                        overlaps.add(m2);
                        log.debug(Messages.ARE_OVERLAPPED, m1, m2, overlaps);
                    } else {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            log.error(Messages.ERROR, e.getMessage());
        }

        log.debug(Messages.EXIT);
        return overlaps;
    }

    public boolean overlap(Meeting m1, Meeting m2) {
        log.debug(Messages.ENTER_WITH_TWO_PARAMS, m1, m2);

        if (m1 == null || m2 == null) {
            return false;
        }

        boolean overlapped = !(m1.getStart() > m2.getEnd()) && !(m2.getStart() > m1.getEnd());
        log.debug(Messages.MAYBE_OVERLAPPED, m1, m2, overlapped);
        return overlapped;
    }

    public int calcMeetingMinutes(List<Meeting> meetings) {
        log.info(Messages.ENTER_WITH_LIST, meetings.size());
        int ans = 0;

        try {
            meetings.sort(Comparator.comparingLong(Meeting::getEnd));
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
                        long period = Util.ePochToMinutes(end - start);
                        ans += period;
                        log.debug(Messages.MINUTES_ADDED, period);
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
        } catch (Exception e) {
            log.error(Messages.ERROR, e.getMessage());
        }

        log.info(Messages.EXIT_WITH_RESULT, ans);
        return ans;
    }
}

