package com.hkjava.demo.demofinnhubdb.model.Graph;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import lombok.Builder;

public class Line {
  // private String type : enum : DAY,WEEK,MONTH,HOUR,30_MINUTE
  private Interval interval;

  private String symbol;

  private List<Point> closePoints; // per month , ,change from Deque to List


  public Line(String symbol, Interval interval) {// DAY, WEEK, MONTH
    this.interval = interval; // month
    this.symbol = symbol;
    this.closePoints = new LinkedList<>();

    // Check if the symbol exists in the map before accessing it
    List<SourcePoint> sourcePoints = SourcePoint.sourceMap.get(symbol);
    if (sourcePoints != null) {
      Comparator<SourcePoint> reversed =
          (SourcePoint p1, SourcePoint p2) -> p1.getTranDayTime().getDatetime()
              .isBefore(p2.getTranDayTime().getDatetime()) ? -1 : 1;

      SourcePoint.sourceMap.get(symbol).stream()//
          .filter(s -> {// s= SourcePoint
            if (interval == Interval.WEEK)
              return s.getTranDayTime().isWeeklyClose();// TranDayTime.class
            else if (interval == Interval.MONTH)
              return s.getTranDayTime().isMonthlyClose();
            else // day
              return true;
          })//
          .sorted(reversed)//
          .forEach(s -> {
            closePoints.add(s.toPoint());
          });
    }
  }

  // Code A.
  // instance method -> calculate
  @Operation(summary = "滑动窗口的方法来计算移动平均线。它创建一个滑动窗口，该窗口包含前 noOfInterval 个数据点。" + //
      "然后，它将滑动窗口中所有数据点的收盘价相加，并除以 noOfInterval 来计算移动平均线。")
  public List<Point> movingAverageA(int noOfInterval) {
    int idx = 0;
    List<Point> moveAverages = new ArrayList<>();
    BigDecimal val = BigDecimal.valueOf(0L);
    Point head = new Point(new Price(0.0d), null); // dummy
    int headIdx = -1;
    while (idx < closePoints.size()) {
      val = val.add(
          BigDecimal.valueOf(closePoints.get(idx).getClosePrice().getPrice()));
      if (idx + 1 >= noOfInterval) {
        val = val.subtract(BigDecimal.valueOf(head.getClosePrice().getPrice())) //
            .divide(BigDecimal.valueOf(noOfInterval));
        // add to MA
        moveAverages.add(new Point(val.doubleValue(),
            closePoints.get(idx).getTranDateTime()));
        // update the head
        head = closePoints.get(++headIdx);
      }
      idx++;
    }
    return moveAverages;
  }

  // Code B.
  // instance method -> calculate
  @Operation(summary = "它遍历所有数据点，并计算每个数据点的移动平均线。" + //
      "每个数据点的移动平均线是前 noOfInterval 个数据点的收盘价的平均值。")
  public List<Point> movingAverageB(int noOfInterval) {
    List<Point> movingAveragePoints = new ArrayList<>();
    Iterator<Point> iterator = closePoints.iterator();

    // Calculate the moving average for each point in the line
    for (int i = 0; i < closePoints.size(); i++) {
      BigDecimal sum = BigDecimal.ZERO;
      int count = 0;

      // Add the closing prices of the previous noOfInterval points to the sum
      for (int j = i; j > i - noOfInterval && j >= 0; j--) {
        if (iterator.hasNext()) {
          Point currentPoint = iterator.next();
          sum = sum
              .add(BigDecimal.valueOf(currentPoint.getClosePrice().getPrice()));
          count++;
        } else {
          break; // Ensure we don't go out of bounds
        }
      }

      // Calculate the moving average by dividing the sum by the number of points
      if (count > 0) {
        BigDecimal movingAverage =
            sum.divide(BigDecimal.valueOf(count), 2, RoundingMode.HALF_UP);
        movingAveragePoints.add(new Point(movingAverage)); // Create a new Point object with the moving average
      }
    }

    return movingAveragePoints;
  }

  // Override the Getter
  public List<Point> getClosePoints(Deque<Point> closePoints) {
    List<Point> points = new ArrayList<>();
    points.addAll(this.closePoints);
    return points;
  }

  public List<Point> getClosePoints() {
    List<Point> points = new ArrayList<>();
    points.addAll(this.closePoints);
    return points;
  }

}

