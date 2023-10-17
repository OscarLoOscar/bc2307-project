package com.example.demo.demostockexchange.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.example.demo.demostockexchange.model.Candle;

public interface CandleStickOperation {

  @GetMapping("/getCandleData")
  @ResponseStatus(HttpStatus.OK)
  Candle getCandleData(String symbol, String resolution, String from,
      String to);
}
