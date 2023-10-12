package com.hkjava.stock.trade.hub.controller.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hkjava.stock.trade.hub.controller.StockTradeOperation;
import com.hkjava.stock.trade.hub.dto.req.PlaceOrderDTO;
import com.hkjava.stock.trade.hub.dto.resp.OrderBookDTO;
import com.hkjava.stock.trade.hub.mapper.StockMapper;
import com.hkjava.stock.trade.hub.model.Order;
import com.hkjava.stock.trade.hub.service.OrderBookService;

@RestController
@RequestMapping(value = "/v1")
public class StockTradeController implements StockTradeOperation {

  @Autowired
  private OrderBookService orderBookService;

  @Override
  public OrderBookDTO orderBook(String symbol) {
    return StockMapper.map(symbol, orderBookService.orderBook(symbol));
  }

  @Override
  public Order order(String userId, String symbol, PlaceOrderDTO tradeDTO) {
    return orderBookService.order(userId, symbol, tradeDTO);
  }


}
