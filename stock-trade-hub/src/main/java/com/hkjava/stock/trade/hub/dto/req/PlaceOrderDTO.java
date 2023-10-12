package com.hkjava.stock.trade.hub.dto.req;

import com.hkjava.stock.trade.hub.enums.Action;
import com.hkjava.stock.trade.hub.enums.OrderType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PlaceOrderDTO {
  
  private Action action;

  private OrderType orderType;

  private double price;

  private int share;

}
