package com.example.demo.demostockexchange.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.demostockexchange.entity.Orders;
import com.example.demo.demostockexchange.exception.ApiResponse;
import com.example.demo.demostockexchange.exception.FinnhubException;
import com.example.demo.demostockexchange.model.OrderForm;
import com.example.demo.demostockexchange.model.OrderRequest;
import com.example.demo.demostockexchange.model.mapper.FinnhubMapper;
import com.example.demo.demostockexchange.repository.StockRepository;

@RestController
@RequestMapping("/sumit")
public class OrderControllerOperation implements OrderController {

  public static List<String> tradeStock = List.of("AAPL", "TSLA", "MSFT");

  @Autowired
  private FinnhubMapper finnhubMapper;

  @Autowired
  StockRepository stockRepository;

  @Override
  public ApiResponse<Orders> placeOrder(@PathVariable String symbol,
      @RequestParam String action, //
      @RequestParam String orderType, //
      @RequestParam double price, //
      @RequestParam int quantity//
  ) throws FinnhubException {
    if (!tradeStock.contains(symbol)) {
      return null;
    }
    OrderRequest request = OrderRequest.builder()//
        .action(action)//
        .orderType(orderType)//
        .price(price)//
        .quantity(quantity)//
        // .totalOrderValue((long) orderForm.getPrice() * orderForm.getQuantity())//
        .build();
    request.onOrder(request.getPrice(), request.getQuantity(),
        request.getAction());
    stockRepository.save(finnhubMapper.requestToOrdersEntity(symbol, request));
    return ApiResponse.<Orders>builder()//
        .ok()//
        .data(finnhubMapper.requestToOrdersEntity(symbol, request))//
        .build();

  }

}
