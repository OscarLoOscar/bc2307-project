package com.example.demo.demostockexchange.infra;

import lombok.Getter;

@Getter
public enum OrderType {
  MARKET, //
  LIMIT,//
  ;

  public OrderType valurOf(String input) {
    if (input.equals(OrderType.MARKET.name())) {
      return OrderType.MARKET;
    } else if (input.equals(OrderType.LIMIT.name())) {
      return OrderType.LIMIT;
    }
    return null;

  }

}
