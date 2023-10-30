package com.example.demo.demostockexchange.model.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
// import com.example.demo.demostockexchange.entity.Orders;
import com.example.demo.demostockexchange.entity.Transaction;
import com.example.demo.demostockexchange.infra.TransactionType;
import com.example.demo.demostockexchange.model.OrderRequest;
import com.example.demo.demostockexchange.model.OrderResp;
import com.example.demo.demostockexchange.model.StockExchange;

@Component
public class FinnhubMapper {

  public List<OrderRequest> map(List<Transaction> ordersList) {
    return ordersList.stream()//
        .map(this::mapSingleOrder)//
        .collect(Collectors.toList());
  }

  public OrderRequest mapSingleOrder(Transaction orders) {
    return OrderRequest.builder() //
        .action(orders.getTransactionType())//
        // .tradeDate(orders.getTradeDate())//
        // .stockId(orders.getStockId())//
        .price(orders.getPrice())//
        .quantity(orders.getQuantity())//
        .build();
  }
  // ---------------

  public List<OrderResp> mapToResp(List<Transaction> ordersList) {
    PriorityQueue<OrderResp> orderRequestQueue =
        new PriorityQueue<>((o1, o2) -> {
          int priceComparison = Double.compare(o2.getPrice(), o1.getPrice());
          if (priceComparison != 0) {
            // If prices are different, prioritize by price
            return priceComparison;
          } else {
            // If prices are equal, prioritize by timestamp (tradeDateTime)
            return o1.getQuantity().compareTo(o2.getQuantity());
          }
        });

    for (Transaction order : ordersList) {
      OrderResp output = this.mapSingleOrderToOrderResp(order);
      orderRequestQueue.add(output);
    }
    return new ArrayList<>(orderRequestQueue);
  }

  public OrderResp mapSingleOrderToOrderResp(Transaction orders) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    return OrderResp.builder() //
        // .type(orders.getType())//
        // .symbol(orders.getStockId())//
        .localTime(orders.getTransactionDate().toLocalTime().format(formatter))//
        .price(orders.getPrice())//
        .quantity(orders.getQuantity())//
        .build();
  }

  // -------------------
  public Transaction requestToOrdersEntity(String symbol,
      OrderRequest ordersRequest) {
    return Transaction.builder() //
        .transactionType(ordersRequest.getAction())//
        .transactionDate(LocalDateTime.now())//
        .stockSymbol(symbol)//
        .price(ordersRequest.getPrice())//
        .quantity(ordersRequest.getQuantity())//
        .build();
  }

  // --------
  public StockExchange installBidQueueAndAskQueue(List<OrderResp> Bid,
      List<OrderResp> Ask) {
    return StockExchange.builder()//
        .BidOrders(Bid)//
        .AskOrders(Ask)//
        .build();
  }
}
