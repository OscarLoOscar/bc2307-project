package com.example.demo.demostockexchange.services;

import com.example.demo.demostockexchange.model.Candle;

public interface CandleService {
  Candle getCandleData(String symbol,String resolution,long from , long to);
}
