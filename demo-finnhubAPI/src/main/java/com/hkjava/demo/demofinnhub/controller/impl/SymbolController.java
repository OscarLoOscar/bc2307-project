package com.hkjava.demo.demofinnhub.controller.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.hkjava.demo.demofinnhub.controller.SymbolOperation;
import com.hkjava.demo.demofinnhub.exception.FinnhubException;
import com.hkjava.demo.demofinnhub.infra.ApiResp;
import com.hkjava.demo.demofinnhub.model.APImodel.Symbol;
import com.hkjava.demo.demofinnhub.service.StockSymbolService;

@RestController
@RequestMapping(value = "/api/v1")
public class SymbolController implements SymbolOperation {

  @Autowired
  StockSymbolService stockSymbolService;

  @Override
  public List<Symbol> getStockSymbol() throws FinnhubException {
    // if (exchange.isBlank())
    // throw new IllegalArgumentException("Parameter Symbol is blank");
    return stockSymbolService.getStockSymbol();
  }

}
