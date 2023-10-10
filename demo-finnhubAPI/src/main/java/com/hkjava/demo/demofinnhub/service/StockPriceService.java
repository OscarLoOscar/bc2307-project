package com.hkjava.demo.demofinnhub.service;

import com.hkjava.demo.demofinnhub.exception.FinnhubException;
import com.hkjava.demo.demofinnhub.model.APImodel.Quote;

public interface StockPriceService {
  Quote getQuote(String symbol) throws FinnhubException;
}

