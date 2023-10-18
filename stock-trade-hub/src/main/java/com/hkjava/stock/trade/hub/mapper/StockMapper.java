package com.hkjava.stock.trade.hub.mapper;

import java.time.LocalDateTime;
import com.hkjava.stock.trade.hub.dto.req.PlaceOrderDTO;
import com.hkjava.stock.trade.hub.dto.resp.OrderBookDTO;
import com.hkjava.stock.trade.hub.model.Order;
import com.hkjava.stock.trade.hub.model.Stock;

public class StockMapper {

  // TBC. Sort by Price
  public static OrderBookDTO map(Stock stock) {
    return OrderBookDTO.builder() //
        .symbol(stock.getSymbol()) //
        .buyBook(stock.getBuyBook().reversed()) //
        .sellBook(stock.getSellBook().reversed()) //
        .build();
  }

  public static Order map(String uesrId, PlaceOrderDTO orderDTO) {
    return Order.of(uesrId, LocalDateTime.now(), orderDTO.getAction(),
        orderDTO.getOrderType(), orderDTO.getPrice(), orderDTO.getShare());

  }

}
