package com.example.demo.demostockexchange.repository.services.impl;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.demostockexchange.entity.Transaction;
import com.example.demo.demostockexchange.infra.TransactionType;
import com.example.demo.demostockexchange.model.BuyerSellerData;
import com.example.demo.demostockexchange.model.OrderRequest;
import com.example.demo.demostockexchange.model.OrderResp;
import com.example.demo.demostockexchange.model.mapper.FinnhubMapper;
import com.example.demo.demostockexchange.repository.TransactionRepository;
import com.example.demo.demostockexchange.repository.services.OrderBookService;

@Service
public class OrderBookServiceImpl implements OrderBookService {

  @Autowired
  FinnhubMapper finnhubMapper;

  @Autowired
  TransactionRepository transactionRepository;;

  @Override
  public List<Transaction> getOrderBook() {
    return transactionRepository.findAll();
  }

  @Override
  public void addOrder(String symbol, OrderRequest makeOrder) {
    Transaction response =
        finnhubMapper.requestToOrdersEntity(symbol, makeOrder);
    transactionRepository.save(response);
  }

  @Override
  public List<OrderResp> getBidQueue(String stockId) {
    // Get the bidOffers from the static field
    Map<Double, Integer> bidOffers = OrderRequest.bidOffers;

    List<OrderResp> response = new ArrayList<>();
    for (Map.Entry<Double, Integer> entry : bidOffers.entrySet()) {
      if (entry.getValue() != 0) {

        double price = entry.getKey();
        int quantity = entry.getValue();

        // Create an OrderResp object with the price and quantity
        OrderResp orderResp =
            OrderResp.builder().type(TransactionType.BUY.name())
                .localTime(LocalTime.now().toString()).price(price)
                .quantity(quantity).build();

        response.add(orderResp);
      }
    }
    return response;
  }

  @Override
  public List<OrderResp> getAskQueue(String stockId) {
    // Get the askOffers from the static field
    Map<Double, Integer> askOffers = OrderRequest.askOffers;

    List<OrderResp> response = new ArrayList<>();
    for (Map.Entry<Double, Integer> entry : askOffers.entrySet()) {
      if (entry.getValue() != 0) {
        double price = entry.getKey();
        int quantity = entry.getValue();

        // Create an OrderResp object with the price and quantity
        OrderResp orderResp =
            OrderResp.builder().type(TransactionType.SELL.name())
                .localTime(LocalTime.now().toString()).price(price)
                .quantity(quantity).build();

        response.add(orderResp);
      }
    }
    return response;
  }


  @Override
  @Transactional(readOnly = true)
  public BuyerSellerData calculateBuyerSellerIndicator() {
    List<Transaction> allTrades = transactionRepository.findAll();
    int buyerVolume = 0;
    int sellerVolume = 0;

    for (Transaction trade : allTrades) {
      if (trade.getQuantity() > 0
          && ("bid".equalsIgnoreCase(trade.getTransactionType().toString()))) {
        buyerVolume += trade.getQuantity();
      } else {
        sellerVolume += Math.abs(trade.getQuantity());
      }
    }

    return new BuyerSellerData(buyerVolume, sellerVolume);
  }
}