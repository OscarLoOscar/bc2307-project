package com.hkjava.stock.trade.hub.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.hkjava.stock.trade.hub.model.Order;
import com.hkjava.stock.trade.hub.model.User;
import com.hkjava.stock.trade.hub.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{

  @Override
  public Order createOrder(String userId, Order order) {
return User.of(userId).placeOrder(order);
  }

  @Override
  public List<Order> getOrders(String userId) {
return User.getOrders(userId);
  }
  
}
