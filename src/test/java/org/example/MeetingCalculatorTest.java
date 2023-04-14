package org.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

class MeetingCalculatorTest {

    static MeetingCalculator meetingCalculator;

    LocalDateTime time1 = LocalDateTime.of(2023, 4, 14, 8, 30);
    LocalDateTime time2 = LocalDateTime.of(2023, 4, 14, 9, 0);
    LocalDateTime time3 = LocalDateTime.of(2023, 4, 14, 10, 0);
    LocalDateTime time4 = LocalDateTime.of(2023, 4, 14, 10, 30);
    LocalDateTime time5 = LocalDateTime.of(2023, 4, 14, 11, 0);
    LocalDateTime time6 = LocalDateTime.of(2023, 4, 14, 12, 30);
    LocalDateTime time7 = LocalDateTime.of(2023, 4, 14, 13, 0);
    LocalDateTime time8 = LocalDateTime.of(2023, 4, 14, 13, 30);
    LocalDateTime time9 = LocalDateTime.of(2023, 4, 14, 14, 0);
    LocalDateTime time10 = LocalDateTime.of(2023, 4, 14, 16, 0);
    LocalDateTime time11 = LocalDateTime.of(2023, 4, 14, 17, 0);
    LocalDateTime time12 = LocalDateTime.of(2023, 4, 14, 17, 15);
    LocalDateTime time13 = LocalDateTime.of(2023, 4, 14, 17, 30);
    LocalDateTime time14 = LocalDateTime.of(2023, 4, 14, 18, 30);
    long eightThirty = time1.toEpochSecond(ZoneOffset.UTC);
    long nine = time2.toEpochSecond(ZoneOffset.UTC);
    long ten = time3.toEpochSecond(ZoneOffset.UTC);
    long tenThirty = time4.toEpochSecond(ZoneOffset.UTC);
    long eleven = time5.toEpochSecond(ZoneOffset.UTC);
    long twelveThirty = time6.toEpochSecond(ZoneOffset.UTC);
    long thirteen = time7.toEpochSecond(ZoneOffset.UTC);
    long thirteenThirty = time8.toEpochSecond(ZoneOffset.UTC);
    long fourteen = time9.toEpochSecond(ZoneOffset.UTC);
    long sixteen = time10.toEpochSecond(ZoneOffset.UTC);
    long seventeen = time11.toEpochSecond(ZoneOffset.UTC);
    long seventeenFifteen = time12.toEpochSecond(ZoneOffset.UTC);
    long seventeenThirty = time13.toEpochSecond(ZoneOffset.UTC);
    long eighteenThirty = time14.toEpochSecond(ZoneOffset.UTC);

    @BeforeAll
    static void name() {
        meetingCalculator = new MeetingCalculator();
    }

    @Test
    void calcMeetingHours() {
        List<Meeting> meetings = new ArrayList<>();
        meetings.add(new Meeting(eightThirty, tenThirty));
        meetings.add(new Meeting(nine, ten));
        meetings.add(new Meeting(ten, eleven));
        meetings.add(new Meeting(twelveThirty, thirteenThirty));
        meetings.add(new Meeting(thirteen, fourteen));
        meetings.add(new Meeting(sixteen, seventeen));
        meetings.add(new Meeting(seventeen, seventeenThirty));
        meetings.add(new Meeting(seventeenFifteen, eighteenThirty));
        Assertions.assertEquals(390, meetingCalculator.calcMeetingMinutes(meetings));
    }

    @Test
    void calcOneMeeting() {
        List<Meeting> meetings = new ArrayList<>();
        meetings.add(new Meeting(twelveThirty, fourteen));
        Assertions.assertEquals(90, meetingCalculator.calcMeetingMinutes(meetings));
    }

    @Test
    void overlapInversion() {
        Meeting m1 = new Meeting(eleven, fourteen);
        Meeting m2 = new Meeting(twelveThirty, sixteen);
        boolean overlap1 = meetingCalculator.overlap(m1, m2);
        boolean overlap2 = meetingCalculator.overlap(m2, m1);
        Assertions.assertTrue(overlap1 && overlap2);

        Meeting m3 = new Meeting(nine, ten);
        Meeting m4 = new Meeting(eightThirty, tenThirty);
        boolean overlap3 = meetingCalculator.overlap(m3, m4);
        boolean overlap4 = meetingCalculator.overlap(m4, m3);
        Assertions.assertTrue(overlap3 && overlap4);
    }

    @Test
    void calcOverlapped() {
        List<Meeting> meetings = new ArrayList<>();
        meetings.add(new Meeting(eightThirty, tenThirty));
        meetings.add(new Meeting(nine, ten));
        meetings.add(new Meeting(ten, eleven));
        meetings.add(new Meeting(twelveThirty, thirteenThirty));
        meetings.add(new Meeting(thirteen, fourteen));
        meetings.add(new Meeting(sixteen, seventeen));
        meetings.add(new Meeting(seventeen, seventeenThirty));
        meetings.add(new Meeting(seventeenFifteen, eighteenThirty));
        Assertions.assertEquals(7, meetingCalculator.calcOverlapped(meetings));

        List<Meeting> meetings2 = new ArrayList<>();
        meetings2.add(new Meeting(twelveThirty, fourteen));
        Assertions.assertEquals(0, meetingCalculator.calcOverlapped(meetings2));

        List<Meeting> meetings3 = new ArrayList<>();
        meetings3.add(new Meeting(eightThirty, tenThirty));
        meetings3.add(new Meeting(nine, ten));
        meetings3.add(new Meeting(ten, eleven));
        Assertions.assertEquals(3, meetingCalculator.calcOverlapped(meetings3));
    }
}