package com.example.demo.demostockexchange.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class OrderForm {
  private String action;
  private String orderType;
  private double price;
  private int quantity;
  private long totalOrderValue;
}
