package com.hkjava.stock.trade.hub.service.impl;

import org.springframework.stereotype.Service;
import com.hkjava.stock.trade.hub.dto.req.PlaceOrderDTO;
import com.hkjava.stock.trade.hub.model.Order;
import com.hkjava.stock.trade.hub.model.OrderBook;
import com.hkjava.stock.trade.hub.model.User;
import com.hkjava.stock.trade.hub.service.OrderBookService;

@Service
public class OrderBookServiceImpl implements OrderBookService {

  @Override
  public OrderBook orderBook(String symbol) {
    return OrderBook.getData(symbol);
  }

  @Override
  public Order order(String userId, String symbol,
      PlaceOrderDTO tradeDTO) {
    return new User(userId).placeOrder(tradeDTO);
  }
}
