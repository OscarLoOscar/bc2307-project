package com.example.demo.demostockexchange.model;

import java.util.List;
import java.util.PriorityQueue;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class StockExchange {
  private List<OrderResp> BidOrders; // Orders with highest bid price
  private List<OrderResp> AskOrders; // Orders with lowest ask price
  /*
   * 1. whats the PriorityQueue 為你做緊？ 有人接貨，坐貨，平均值keep住 好多trader，點係畫面做實時update，似object/bean，咁多個人都touch到 一隻股點用api去更新條queue
   * 
   * 一條thread manage 另一條queue
   * 
   * 唔likely 儲落DB， static/redis/bean去儲住
   * 
   * 10個人 序 Market,得2個trade，suppose 全部人都見到畫面郁，但要不停read，可以delay
   * 
   * 我地write 會影響人地既read
   * 
   * PriorityQueue
   */

}
