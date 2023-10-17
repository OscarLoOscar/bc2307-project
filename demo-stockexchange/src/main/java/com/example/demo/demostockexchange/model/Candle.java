package com.example.demo.demostockexchange.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Candle {

  @JsonProperty(value = "c")
  double closePrice;

  @JsonProperty(value = "h")
  double highPrice;

  @JsonProperty(value = "l")
  double lowPrice;

  @JsonProperty(value = "o")
  double openPrice;

  @JsonProperty(value = "s")
  String status;

  @JsonProperty(value = "t")
  long timestamp;

  @JsonProperty(value = "v")
  long volume;



}
