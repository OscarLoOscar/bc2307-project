package com.hkjava.stock.trade.hub.service;

import com.hkjava.stock.trade.hub.dto.req.PlaceOrderDTO;
import com.hkjava.stock.trade.hub.model.Order;
import com.hkjava.stock.trade.hub.model.OrderBook;

public interface OrderBookService {

  OrderBook orderBook(String symbol);

  // OrderBook orderAndGet(String userId, TradeDTO tradeDTO);

  Order order(String userId, String symbol, PlaceOrderDTO tradeDTO);

}
