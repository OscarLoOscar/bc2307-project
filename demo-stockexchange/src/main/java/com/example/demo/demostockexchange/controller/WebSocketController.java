package com.example.demo.demostockexchange.controller;

import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.demostockexchange.entity.Orders;
import com.example.demo.demostockexchange.exception.ApiResponse;
import com.example.demo.demostockexchange.exception.FinnhubException;
import com.example.demo.demostockexchange.infra.Code;
import com.example.demo.demostockexchange.infra.tradeType;
import com.example.demo.demostockexchange.model.OrderRequest;
import com.example.demo.demostockexchange.model.OrderResp;
import com.example.demo.demostockexchange.model.StockExchange;
import com.example.demo.demostockexchange.model.BuyerVsSeller.BuyerSellerData;
import com.example.demo.demostockexchange.model.mapper.FinnhubMapper;
import com.example.demo.demostockexchange.services.OrderBookService;

@RestController
@RequestMapping("/transactions")
public class WebSocketController implements WebSocketOperation {

  @Autowired
  private OrderBookService orderBookService;

  @Autowired
  private FinnhubMapper finnhubMapper;

  @Override
  public ApiResponse<List<OrderRequest>> updateOrderBook() {
    List<OrderRequest> response =
        finnhubMapper.map(orderBookService.getOrderBook());
    return ApiResponse.<List<OrderRequest>>builder()//
        .ok()//
        .data(response)//
        .build();
  }

  List<String> tradeStock = List.of("AAPL", "TSLA", "MSFT");

  // @Override
  // public ApiResponse<Orders> placeOrder(OrderRequest orderRequest)
  // throws FinnhubException {

  // // List<String> tradeStock = List.of("AAPL", "TSLA", "MSFT");
  // if (!tradeStock.contains(orderRequest.getStockId()))
  // throw new FinnhubException(Code.FINNHUB_SYMBOL_NOTFOUND);
  // if (tradeType.ASK.name().equals(orderRequest.getType().toUpperCase())
  // && tradeStock.contains(orderRequest.getStockId())) {
  // createAskOrder(orderRequest);
  // } else if (tradeType.BID.name().equals(orderRequest.getType().toUpperCase())
  // && tradeStock.contains(orderRequest.getStockId())) {
  // createBidOrder(orderRequest);
  // }
  // return ApiResponse.<Orders>builder()//
  // .ok()//
  // .message(orderRequest.getType().toUpperCase()
  // + " Order placed successfully.")//
  // .data(finnhubMapper.requestToOrdersEntity(orderRequest))//
  // .build();
  // }

  @Override
  public ApiResponse<Orders> placeOrder(OrderRequest orderRequest)
      throws FinnhubException {

    OrderRequest request = new OrderRequest();
    request.onOrder(orderRequest.getPrice(), orderRequest.getQuantity(),
        orderRequest.getType());
    return ApiResponse.<Orders>builder()//
        .ok()//
        .data(finnhubMapper.requestToOrdersEntity(request))//
        .build();
  }

  public void createAskOrder(OrderRequest orderRequest) {
    // Validate and process the Ask order request
    orderBookService.addOrder(orderRequest);
  }

  public void createBidOrder(OrderRequest orderRequest) {
    // Validate and process the Bid order request
    orderBookService.addOrder(orderRequest);
  }

  @Override
  public List<OrderResp> BidOrdersQueue(String stockId) {
    return orderBookService.getBidQueue(stockId);
  }

  @Override
  public List<OrderResp> AskOrdersQueue(String stockId) {
    return orderBookService.getAskQueue(stockId);
  }

  // @Override
  // // public Map<String, StockExchange> atAuctionOrders(String stockId)
  // public List<StockExchange> atAuctionOrders(String stockId)
  // throws FinnhubException {
  // if (!tradeStock.contains(stockId)) {
  // throw new FinnhubException(Code.NOTFOUND);
  // }
  // return orderBookService.atAuctionOrders(stockId).get(stockId);
  // }

  @Override
  public BuyerSellerData getBuyerSellerIndicator() {
    return orderBookService.calculateBuyerSellerIndicator();
  }

  // @Override
  // public String executeTrades(OrderResp orderResp) {
  //   return orderBookService.executeTrades(orderResp);
  // }


}

