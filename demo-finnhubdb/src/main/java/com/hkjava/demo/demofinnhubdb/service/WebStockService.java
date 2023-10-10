package com.hkjava.demo.demofinnhubdb.service;

import java.util.List;
import com.hkjava.demo.demofinnhubdb.entity.Stock;
import com.hkjava.demo.demofinnhubdb.exception.FinnhubException;
import com.hkjava.demo.demofinnhubdb.model.dto.Response.StockDTO;
import com.hkjava.demo.demofinnhubdb.model.dto.Response.StockGetFromDBDTO;
import io.swagger.v3.oas.annotations.Operation;

public interface WebStockService {
  @Operation(summary = "map the data from API")
  StockDTO stockInfo(String symbol) throws FinnhubException;

  List<StockGetFromDBDTO> stockInfo() throws FinnhubException;

  void addStock(Stock stock) throws FinnhubException;
}
