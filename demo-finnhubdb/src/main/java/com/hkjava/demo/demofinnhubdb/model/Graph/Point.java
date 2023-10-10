package com.hkjava.demo.demofinnhubdb.model.Graph;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/*
 * Point：表示一個數據點，包含收盤價和交易日期/時間。
 */
@Getter
@Builder
@AllArgsConstructor
// 畫上去個點
public class Point {

  public Point(BigDecimal movingAverage) {

  }

  public Point(double closePrice, TranDayTime tranDayTime) {
    this.closePrice = new Price(closePrice);
    this.tranDateTime = tranDayTime;
  }

  private Price closePrice;

  private TranDayTime tranDateTime;// encapsulation , 唔寫LocalDay

}
