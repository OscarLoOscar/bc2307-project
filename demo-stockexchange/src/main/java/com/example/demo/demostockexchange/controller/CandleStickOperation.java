package com.example.demo.demostockexchange.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.example.demo.demostockexchange.model.Candle;

public interface CandleStickOperation {

  @GetMapping("/getCandleData")
  @ResponseStatus(HttpStatus.OK)
  Candle getCandleData(@RequestParam(name = "symbol") String symbol,//
  @RequestParam(name = "resolution") String resolution, //
  @RequestParam(name = "from")String from,//
   @RequestParam(name = "to")   String to);
}
