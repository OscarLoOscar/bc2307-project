package com.example.demo.demostockexchange.trade;

import java.util.Deque;
import java.util.Iterator;
import java.util.Map;
import com.example.demo.demostockexchange.infra.OrderType;
import com.example.demo.demostockexchange.infra.TransactionType;
import com.example.demo.demostockexchange.model.Order;

public class TradeFactory {

  public static Tradable produce(double price, int quantity,
      TransactionType action, OrderType orderType) {
    if (action == TransactionType.BUY && orderType == OrderType.LIMIT)
      return new BuyAndLimited(new Order(price, quantity));
    if (action == TransactionType.BUY && orderType == OrderType.MARKET)
      return new BuyAndMarket(new Order(price, quantity));
    if (action == TransactionType.SELL && orderType == OrderType.LIMIT)
      return new SellAndLimited(new Order(price, quantity));
    if (action == TransactionType.SELL && orderType == OrderType.MARKET)
      return new SellAndMarket(new Order(price, quantity));
    return null;
  }

  public static void placeBook(Map<Double, Integer> offers, Double orderPrice,
      Integer orderShare) {
    if (offers.size() == 0) {
      offers.put(orderPrice, orderShare);
    } else {
      for (Double price : offers.keySet()) {
        Integer existingShare = offers.get(price);
        if (price.equals(orderPrice)) {
          offers.put(price, existingShare + orderShare);
          return; // Exit the loop once the order is placed
        }
      }
      offers.put(orderPrice, orderShare);
    }
  }


}
