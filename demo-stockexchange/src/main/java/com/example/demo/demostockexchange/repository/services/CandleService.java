package com.example.demo.demostockexchange.repository.services;

import java.util.List;
import com.example.demo.demostockexchange.model.Candle;

public interface CandleService {
  Candle getCandleData(String symbol,String resolution,long from , long to);
}
