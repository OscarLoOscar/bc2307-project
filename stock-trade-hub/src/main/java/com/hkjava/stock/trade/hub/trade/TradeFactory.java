package com.hkjava.stock.trade.hub.trade;

import java.util.Deque;
import java.util.Iterator;
import com.hkjava.stock.trade.hub.enums.Action;
import com.hkjava.stock.trade.hub.enums.OrderType;
import com.hkjava.stock.trade.hub.model.Entry;

public class TradeFactory {

  public static Tradable produce(Deque<Entry> buyBook, Deque<Entry> sellBook,
      Action action, OrderType orderType) {
    if (action == Action.BUY && orderType == OrderType.LIMIT)
      return new BuyAndLimited(buyBook, sellBook);
    if (action == Action.BUY && orderType == OrderType.MARKET)
      return new BuyAndMarket(buyBook, sellBook);
    if (action == Action.SELL && orderType == OrderType.LIMIT)
      return new SellAndLimited(buyBook, sellBook);
    if (action == Action.SELL && orderType == OrderType.MARKET)
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
