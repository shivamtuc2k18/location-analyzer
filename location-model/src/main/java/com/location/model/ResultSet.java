package com.location.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;

@Builder
public class ResultSet {

  String year;
  String month;
  String date;
  String day;
  String hour;
  String minute;
  String block;
  String duration;
  String toWork;

  public static String[] getHeaders() {
    java.lang.reflect.Field[] allFields = ResultSet.class.getDeclaredFields();
    String[] record = new String[allFields.length];
    for (int ii = 0; ii < allFields.length; ii++) {
      record[ii] = allFields[ii].getName();
    }
    return record;
  }

  public static List<String> buildRecord(Commute commute) {
    List<String> record = new ArrayList<>();
    LocalDateTime date = commute.getCommuteStartTime();
    record.add(String.valueOf(date.getYear()));
    record.add(String.valueOf(date.getMonth()));
    record.add(String.valueOf(date.getDayOfMonth()));
    record.add(String.valueOf(date.getDayOfWeek()));
    record.add(String.valueOf(date.getHour()));
    record.add(String.valueOf(date.getMinute()));
    record.add(findStartTimeBlock(date.getHour(), date.getMinute()));
    record.add(String.valueOf(commute.getCommuteDuration()));
    record.add(String.valueOf(commute.isFromHomeToWork()));
    return record;
  }

  static String findStartTimeBlock(int hour, int minute) {
    int minuteBlock = roundUp(minute);
    double block = hour + (double) minuteBlock / 100;
    return String.valueOf(block);
  }

  static int roundUp(int toRound) {
    int multiple = 10;
    int rem = toRound % multiple;
    int result = toRound - rem;
    if (rem > (multiple / 2)) {
      result += multiple;
    }
    return result;
  }
}
