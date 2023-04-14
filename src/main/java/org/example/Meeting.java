package org.example;

public class Meeting {
    private final long start;
    private final long end;

    public Meeting(long start, long end) {
        this.start = start;
        this.end = end;
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }
}
