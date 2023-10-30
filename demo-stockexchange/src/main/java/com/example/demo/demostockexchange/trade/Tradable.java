package com.example.demo.demostockexchange.trade;

import java.util.Deque;
import com.example.demo.demostockexchange.model.Entry;

//step 1 
@FunctionalInterface
public interface Tradable {
  void trade(Double orderPrice, Integer orderShare);

  default void placeBook(Deque<Entry> entries , Double orderPrice,Integer orderShare){
    TradeFactory.placeBook(entries , orderPrice ,orderShare);
  }
}
