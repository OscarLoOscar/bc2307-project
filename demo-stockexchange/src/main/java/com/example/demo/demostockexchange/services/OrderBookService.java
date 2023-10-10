package com.example.demo.demostockexchange.services;

import java.util.List;
import java.util.Map;
import com.example.demo.demostockexchange.entity.Orders;
import com.example.demo.demostockexchange.model.OrderRequest;
import com.example.demo.demostockexchange.model.OrderResp;
import com.example.demo.demostockexchange.model.StockExchange;
import com.example.demo.demostockexchange.model.BuyerVsSeller.BuyerSellerData;

public interface OrderBookService {

  public List<OrderResp> getBidQueue(String stockId);

  public List<OrderResp> getAskQueue(String stockId);

  public List<Orders> getOrderBook();

  public void addOrder(OrderRequest makeOrder);

  public Map<String, StockExchange> atAuctionOrders(String stockId);

  public BuyerSellerData calculateBuyerSellerIndicator();

  public String executeTrades(OrderResp orderResp);
}

