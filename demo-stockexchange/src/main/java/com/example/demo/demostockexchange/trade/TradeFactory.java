package com.example.demo.demostockexchange.trade;

import java.util.Deque;
import java.util.Iterator;
import com.example.demo.demostockexchange.infra.OrderType;
import com.example.demo.demostockexchange.infra.TransactionType;
import com.example.demo.demostockexchange.model.Entry;

public class TradeFactory {

  public static Tradable produce(Deque<Entry> buyBook, Deque<Entry> sellBook,
      TransactionType action, OrderType orderType) {
    if (action == TransactionType.BUY && orderType == OrderType.LIMIT)
      return new BuyAndLimited(buyBook, sellBook);
    if (action == TransactionType.BUY && orderType == OrderType.MARKET)
      return new BuyAndMarket(buyBook, sellBook);
    if (action == TransactionType.SELL && orderType == OrderType.LIMIT)
      return new SellAndLimited(buyBook, sellBook);
    if (action == TransactionType.SELL && orderType == OrderType.MARKET)
      return new SellAndMarket(buyBook, sellBook);
    return null;
  }

  public static void placeBook(Deque<Entry> entries, Double orderPrice,
      Integer orderShare) {
    if (entries.size() == 0) {
      entries.add(new Entry(orderPrice, orderShare));
    }
    Iterator<Entry> itr = entries.iterator();
    Entry entry = null;
    while (itr.hasNext()) {
      entry = itr.next();
      if (entry.getPrice() == orderPrice) {
        entry.addShare(orderShare);
      }
    }
  }
  
}
