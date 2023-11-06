package com.example.demo.demostockexchange.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.demostockexchange.controller.impl.OrderControllerOperation;
import com.example.demo.demostockexchange.entity.Transaction;
import com.example.demo.demostockexchange.exception.ApiResponse;
import com.example.demo.demostockexchange.exception.FinnhubException;
import com.example.demo.demostockexchange.infra.OrderType;
import com.example.demo.demostockexchange.infra.TransactionType;
import com.example.demo.demostockexchange.model.Order;
import com.example.demo.demostockexchange.model.OrderRequest;
import com.example.demo.demostockexchange.model.mapper.FinnhubMapper;
// import com.example.demo.demostockexchange.repository.StockRepository;
import com.example.demo.demostockexchange.repository.TransactionRepository;
import com.example.demo.demostockexchange.services.TransactionService;

@RestController
@RequestMapping("/sumit")
public class OrderController implements OrderControllerOperation {

  public static List<String> tradeStock = List.of("AAPL", "TSLA", "MSFT");

  @Autowired
  private FinnhubMapper finnhubMapper;

  @Autowired
  private TransactionService transactionService;

  @Autowired
  private TransactionRepository transactionRepository;

  @Override
  public ApiResponse<Transaction> placeOrder(@PathVariable String symbol,
      @RequestParam String action, //
      @RequestParam String orderType, //
      @RequestParam double price, //
      @RequestParam int quantity//
  ) throws FinnhubException {
    if (!tradeStock.contains(symbol)) {
      return null;
    }

    OrderRequest request = OrderRequest.builder()//
        .action(TransactionType.valueOf(action))//
        .orderType(OrderType.valueOf(orderType))//
        .price(price)//
        .quantity(quantity)//
        // .totalOrderValue((long) orderForm.getPrice() * orderForm.getQuantity())//
        .build();

    Order order = Order.builder()//
        .price(price)//
        .share(quantity)//
        .build();


    request.onOrder(order, //
        TransactionType.valueOf(action), //
        OrderType.valueOf(orderType));

    transactionRepository
        .save(finnhubMapper.requestToOrdersEntity(symbol, request));

    return ApiResponse.<Transaction>builder()//
        .ok()//
        .data(finnhubMapper.requestToOrdersEntity(symbol, request))//
        .build();

  }

}
