package org.example;

public class Util {
    public static long ePochToMinutes(long epochSeconds) {
        double minutes = (double) epochSeconds / 60L;
        return Math.round(minutes);
    }
}
