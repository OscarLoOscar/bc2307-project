package com.example.demo.demostockexchange.model.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.example.demo.demostockexchange.entity.Orders;
import com.example.demo.demostockexchange.infra.Action;
import com.example.demo.demostockexchange.model.OrderRequest;
import com.example.demo.demostockexchange.model.OrderResp;
import com.example.demo.demostockexchange.model.StockExchange;

@Component
public class FinnhubMapper {

  public List<OrderRequest> map(List<Orders> ordersList) {
    return ordersList.stream()//
        .map(this::mapSingleOrder)//
        .collect(Collectors.toList());
  }

  public OrderRequest mapSingleOrder(Orders orders) {
    return OrderRequest.builder() //
        .action(orders.getType())//
        // .tradeDate(orders.getTradeDate())//
        // .stockId(orders.getStockId())//
        .price(orders.getPrice())//
        .quantity(orders.getQuantity())//
        .build();
  }
  // ---------------

  public List<OrderResp> mapToResp(List<Orders> ordersList) {
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

    for (Orders order : ordersList) {
      OrderResp output = this.mapSingleOrderToOrderResp(order);
      orderRequestQueue.add(output);
    }
    return new ArrayList<>(orderRequestQueue);
  }

  public OrderResp mapSingleOrderToOrderResp(Orders orders) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    return OrderResp.builder() //
        // .type(orders.getType())//
        // .symbol(orders.getStockId())//
        .localTime(orders.getTradeDateTime().toLocalTime().format(formatter))//
        .price(orders.getPrice())//
        .quantity(orders.getQuantity())//
        .build();
  }

  // -------------------
  public Orders requestToOrdersEntity(String symbol,OrderRequest ordersRequest) {
    return Orders.builder() //
        .type(ordersRequest.getAction().toString())//
        .tradeDateTime(LocalDateTime.now())//
         .stockId(symbol)//
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