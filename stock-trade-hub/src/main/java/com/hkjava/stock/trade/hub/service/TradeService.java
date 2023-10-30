package com.hkjava.stock.trade.hub.service;

import java.util.List;
import com.hkjava.stock.trade.hub.dto.resp.OrderBookDTO;
import com.hkjava.stock.trade.hub.exception.FinnhubException;
import com.hkjava.stock.trade.hub.model.Order;

public interface TradeService {

    List<OrderBookDTO> findOrders();

    void marketOrder(String stockName, String buySell, Integer quantity)
            throws FinnhubException;

    void limitOrder(String stockName, String buySell, Double price,
            Integer quantity) throws FinnhubException;

    List<Order> findAll();
}