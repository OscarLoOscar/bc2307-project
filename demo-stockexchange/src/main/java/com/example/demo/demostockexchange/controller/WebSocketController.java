package com.example.demo.demostockexchange.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.demostockexchange.controller.impl.WebSocketOperation;
import com.example.demo.demostockexchange.exception.ApiResponse;
import com.example.demo.demostockexchange.exception.FinnhubException;
import com.example.demo.demostockexchange.model.OrderRequest;
import com.example.demo.demostockexchange.model.OrderResp;
import com.example.demo.demostockexchange.model.BuyerSellerData;
import com.example.demo.demostockexchange.model.mapper.FinnhubMapper;
import com.example.demo.demostockexchange.repository.TransactionRepository;
import com.example.demo.demostockexchange.repository.services.OrderBookService;

@RestController
@RequestMapping("/transactions")
public class WebSocketController implements WebSocketOperation {

  @Autowired
  private OrderBookService orderBookService;

  @Autowired
  private FinnhubMapper finnhubMapper;

  @Autowired
  TransactionRepository transactionRepository;

  @Override
  public ApiResponse<List<OrderRequest>> updateOrderBook() {
    List<OrderRequest> response =
        finnhubMapper.map(orderBookService.getOrderBook());
    return ApiResponse.<List<OrderRequest>>builder()//
        .ok()//
        .data(response)//
        .build();
  }

  public static List<String> tradeStock = List.of("AAPL", "TSLA", "MSFT");

  public void createAskOrder(String symbol, OrderRequest orderRequest) {
    // Validate and process the Ask order request
    orderBookService.addOrder(symbol, orderRequest);
  }

  public void createBidOrder(String symbol, OrderRequest orderRequest) {
    // Validate and process the Bid order request
    orderBookService.addOrder(symbol, orderRequest);
  }

  @Override
  public List<OrderResp> BidOrdersQueue(String stockId) {
    return orderBookService.getBidQueue(stockId);
  }

  @Override
  public List<OrderResp> AskOrdersQueue(String stockId) {
    return orderBookService.getAskQueue(stockId);
  }

  @Override
  public BuyerSellerData getBuyerSellerIndicator() {
    return orderBookService.calculateBuyerSellerIndicator();
  }
}
