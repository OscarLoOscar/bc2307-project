package com.example.demo.demostockexchange.model;

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
public class OrderRequest {
  String type; // 'Bid','Ask'

  // @JsonFormat(locale = "zh", timezone = "GMT+8",
  // pattern = "yyyy-MM-dd HH:mm:ss")
  // @DateTimeFormat
  // @Column(name = "placedAt")
  // private LocalDate tradeDate = LocalDate.now();
  String stockId;

  Double price;

  private Integer quantity;

}
