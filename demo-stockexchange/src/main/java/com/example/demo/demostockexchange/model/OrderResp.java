package com.example.demo.demostockexchange.model;

import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Builder
@AllArgsConstructor
@Getter
@ToString
@Setter
public class OrderResp {

  String type; // 'Bid','Ask'

  private String localTime;

  Double price;

  private Integer quantity;

}
