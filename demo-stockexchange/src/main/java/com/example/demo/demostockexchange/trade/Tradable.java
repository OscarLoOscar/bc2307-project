package com.example.demo.demostockexchange.trade;

import java.util.Deque;
import java.util.Map;
import com.example.demo.demostockexchange.model.Order;

//step 1 
@FunctionalInterface
public interface Tradable {
  void trade(Order entry);

  default void placeBook(Map<Double, Integer> offers , Double orderPrice,Integer orderShare){
    TradeFactory.placeBook(offers , orderPrice ,orderShare);
  }
}
