package com.example.demo.demostockexchange.services;

import java.util.List;
import com.example.demo.demostockexchange.exception.FinnhubException;
import com.example.demo.demostockexchange.model.OrderRequest;

public interface TradeService {

    List<OrderRequest> findOrders();

    void marketOrder(String stockName, String buySell, Integer quantity)
            throws FinnhubException;

    void limitOrder(String stockName, String buySell, Double price,
            Integer quantity) throws FinnhubException;

    List<TradeRecord> findAll();
}