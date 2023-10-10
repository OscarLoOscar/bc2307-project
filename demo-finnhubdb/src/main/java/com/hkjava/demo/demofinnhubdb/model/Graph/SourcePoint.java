package com.hkjava.demo.demofinnhubdb.model.Graph;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

/*
 * SourcePoint：表示來自數據源的數據點。它包含有關間隔、收盤價和交易日期/時間的信息。
 * 它可以使用 toPoint() 方法將自身轉換為 Point 對象。
 * 還包含一個 sourceMap，用於按符號存儲數據點。
 */

// database ?
// -> transactions (real life)
// -> Stock Price per day per stock (Project)
@Getter
@Setter
public class SourcePoint {

  public static Map<String, List<SourcePoint>> sourceMap = new HashMap<>();

  // in real life, this should be Interval.MIN_1
  private Interval interval = Interval.DAY;

  private Price closePrice;

  private TranDayTime tranDayTime;

  public SourcePoint(double closePoint, TranDayTime tranDayTime) {
    this.closePrice = new Price(closePoint);
    this.tranDayTime = tranDayTime;
  }

  public SourcePoint(double closePoint, LocalDateTime localDateTime) {
    this.closePrice = new Price(closePoint);
    this.tranDayTime = new TranDayTime(localDateTime);
  }

  public SourcePoint(double closePoint, LocalDate localDate) {
    this.closePrice = new Price(closePoint);
    this.tranDayTime = new TranDayTime(localDate.atStartOfDay());
  }

  // 當mapper用
  public Point toPoint() {
    return Point.builder() //
        .closePrice(this.closePrice) //
        .tranDateTime(this.tranDayTime) //
        .build();
  }

  public static Map<String, List<SourcePoint>> getSourceMap() {
    return sourceMap;
  }
}
