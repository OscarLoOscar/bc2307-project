package com.hkjava.stock.trade.hub.trade;

import java.util.Deque;
import com.hkjava.stock.trade.hub.model.Entry;

//step 1 
@FunctionalInterface
public interface Tradable {
  void trade(Double orderPrice, Integer orderShare);

  default void placeBook(Deque<Entry> entries , Double orderPrice,Integer orderShare){
    TradeFactory.placeBook(entries , orderPrice ,orderShare);
  }
}
