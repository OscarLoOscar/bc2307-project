package com.hkjava.stock.trade.hub.service;

import com.hkjava.stock.trade.hub.model.Stock;

public interface OrderBookService {

  // Stock orderBook(String symbol);

  // OrderBook orderAndGet(String userId, TradeDTO tradeDTO);

  // Order order(String userId, String symbol, PlaceOrderDTO tradeDTO);

   /**
   * Get Order Book per Stock Symbol 
   * @param symbol
   * @return
   */
  Stock getStock(String symbol);

}
