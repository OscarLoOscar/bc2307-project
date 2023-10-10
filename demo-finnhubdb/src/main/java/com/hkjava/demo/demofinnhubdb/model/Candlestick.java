package com.hkjava.demo.demofinnhubdb
.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
public class Candlestick {
  private double high;
  private double low;
  private double open;
  private double close;



}
