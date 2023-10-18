package com.hkjava.stock.trade.hub.model;

import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@AllArgsConstructor
@ToString
public class Stock {

  // by symbol
  private static Map<String, Stock> stocks = new HashMap<>();


  // first version :
  // private Deque<Entry> buyBook;

  // private Deque<Entry> sellBook;
  private String symbol;

  private Book buyBook;

  private Book sellBook;

  public Stock(String symbol) {
    this.symbol = symbol;
    this.buyBook = getBuyBook(symbol);
    this.sellBook = getSellBook(symbol);
  }
public void processOrder(Order order){
  Deque<Entry> buyDeque = this.buyBook.toDescDeque();
  
}

  public static Stock getStock(String symbol) {
    return stocks.get(symbol);
  }

  public static Book getBuyBook(String symbol) {
    return stocks.get(symbol).getBuyBook();
  }

  public static Book getSellBook(String symbol) {
    return stocks.get(symbol).getSellBook();
  }
}
