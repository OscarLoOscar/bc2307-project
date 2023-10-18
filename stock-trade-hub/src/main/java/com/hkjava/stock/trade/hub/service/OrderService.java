package com.hkjava.stock.trade.hub.service;

import java.util.List;
import com.hkjava.stock.trade.hub.model.Order;

public interface OrderService {
  /*
   * Place Order by user ID
   * 
   * @param userId
   * 
   * @param tradeDTO
   * 
   * @return
   */
  Order createOrder(String userId, Order order);

  /*
   * Get All orders histories by user ID
   * 
   * @param userID
   * 
   * @return
   */
  List<Order> getOrders(String userId);
}
