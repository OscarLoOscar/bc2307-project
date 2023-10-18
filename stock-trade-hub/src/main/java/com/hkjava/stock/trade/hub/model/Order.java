package com.hkjava.stock.trade.hub.model;

import java.time.LocalDateTime;
import com.hkjava.stock.trade.hub.enums.Action;
import com.hkjava.stock.trade.hub.enums.OrderType;
import lombok.Getter;

// No other lombok Constructor/ Builder, lock the id increment
@Getter

public class Order {

  private static int idCounter = 0;

  private int id;

  private String userId;

  private String symbol;

  private LocalDateTime orderDateTime;

  private Action action;

  private OrderType orderType;

  private double price;

  private int share;

  public static Order of(String userId, LocalDateTime tranDateTime,
      Action action, OrderType orderType, double price, int share) {
    return new Order(userId, tranDateTime, action, orderType, price, share);
  }

  public static Order of(String userId, Action action, OrderType orderType,
      double price, int share) {
    return new Order(userId, action, orderType, price, share);
  }

  private Order(String userId, LocalDateTime tranDateTime, Action action,
      OrderType orderType, double price, int share) {
    this.id = ++idCounter; // Review.
    this.userId = userId;
    this.orderDateTime = tranDateTime;
    this.action = action;
    this.orderType = orderType;
    this.price = price;
    this.share = share;
  }

  private Order(String userId, Action action, OrderType orderType, double price,
      int share) {
    this.id = ++idCounter; // Review.
    this.userId = userId;
    this.orderDateTime = LocalDateTime.now();
    this.action = action;
    this.orderType = orderType;
    this.price = price;
    this.share = share;
  }

}
