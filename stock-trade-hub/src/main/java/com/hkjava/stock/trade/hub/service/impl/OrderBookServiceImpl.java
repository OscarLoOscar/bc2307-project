package com.hkjava.stock.trade.hub.service.impl;

import org.springframework.stereotype.Service;
import com.hkjava.stock.trade.hub.model.Stock;
import com.hkjava.stock.trade.hub.service.OrderBookService;

@Service
public class OrderBookServiceImpl implements OrderBookService {

  @Override
  public Stock getStock(String symbol) {
    return Stock.getStock(symbol);
  }
}
