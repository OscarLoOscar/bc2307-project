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
import com.hkjava.stock.trade.hub.service.OrderService;

@RestController
@RequestMapping(value = "/v1")
public class StockTradeController implements StockTradeOperation {

  @Autowired
  private OrderBookService orderBookService;

  @Autowired
  private OrderService orderService;

  @Override
  public OrderBookDTO getOrderBook(String symbol) {
    return StockMapper.map(orderBookService.getStock(symbol));
  }

  @Override
  public Order createOrder(String userId, PlaceOrderDTO placeOrderDTO) {
    Order order = StockMapper.map(userId, placeOrderDTO);
    return orderService.createOrder(userId, order);
  }

  @Override
  public List<Order> getOrders(String userid) {
    return orderService.getOrders(userid);
  }


}
