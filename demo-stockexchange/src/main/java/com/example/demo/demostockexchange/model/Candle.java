package com.example.demo.demostockexchange.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

@Getter
@Setter
@ToString
public class Candle {

  @JsonProperty(value = "c")
  List<Double> closePrice;

  @JsonProperty(value = "h")
  List<Double> highPrice;

  @JsonProperty(value = "l")
  List<Double> lowPrice;

  @JsonProperty(value = "o")
  List<Double> openPrice;

  @JsonProperty(value = "s")
  String status;

  @JsonProperty(value = "t")
  List<Long> timestamp;

  @JsonProperty(value = "v")
  List<Long> volume;



}
