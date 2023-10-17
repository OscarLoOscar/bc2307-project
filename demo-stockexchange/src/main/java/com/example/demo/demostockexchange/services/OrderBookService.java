package com.example.demo.demostockexchange.services;

import java.util.List;
import com.example.demo.demostockexchange.entity.Orders;
import com.example.demo.demostockexchange.model.BuyerSellerData;
import com.example.demo.demostockexchange.model.OrderRequest;
import com.example.demo.demostockexchange.model.OrderResp;

public interface OrderBookService {

  public List<OrderResp> getBidQueue(String stockId);

  public List<OrderResp> getAskQueue(String stockId);

  public List<Orders> getOrderBook();

  public void addOrder(String symbol, OrderRequest makeOrder);

  // public List<StockExchange> atAuctionOrders(String stockId);

  public BuyerSellerData calculateBuyerSellerIndicator();

  // public String executeTrades(OrderResp orderResp);
}

