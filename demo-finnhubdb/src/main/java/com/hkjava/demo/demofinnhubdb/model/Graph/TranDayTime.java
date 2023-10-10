package com.hkjava.demo.demofinnhubdb.model.Graph;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/*
 * 表示交易日的日期/時間。
 * 提供方法來檢查日期/時間是否與每周或每月的收盤價相匹配。它還計算一個月的最後一個交易日。
 */
@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class TranDayTime {
  private LocalDateTime datetime;

  // Assume Friday is not holiday
  public boolean isWeeklyClose() {
    return datetime.getDayOfWeek() == DayOfWeek.FRIDAY;
  }

  public boolean isMonthlyClose() {
    return this.datetime.getDayOfMonth() == lastTranDayOfMOnth(
        datetime.getMonth(), datetime.getYear());
  }

  // Ignore Leap Year handling at the moment
  private static int lastTranDayOfMOnth(Month month, int year) {
    if (year < 1980 || year > 2100)
      return -1;
    int dd = switch (month) {
      case JANUARY -> 31;
      case FEBRUARY -> 28;
      case MARCH -> 31;
      case APRIL -> 30;
      case MAY -> 31;
      case JUNE -> 30;
      case JULY -> 31;
      case AUGUST -> 31;
      case SEPTEMBER -> 30;
      case OCTOBER -> 31;
      case NOVEMBER -> 30;
      case DECEMBER -> 31;
      default -> -1;
    };
    LocalDate lastTranDate = LocalDate.of(year, month.getValue(), dd);
    while (lastTranDate.getDayOfWeek() == DayOfWeek.SATURDAY
        || lastTranDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
      lastTranDate = lastTranDate.minusDays(1L);
    }
    return lastTranDate.getDayOfMonth();
  }
}
