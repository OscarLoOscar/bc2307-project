package com.hkjava.demo.demofinnhubdb.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hkjava.demo.demofinnhubdb.entity.Stock;
import com.hkjava.demo.demofinnhubdb.entity.StockPrice;
import com.hkjava.demo.demofinnhubdb.exception.FinnhubException;
import com.hkjava.demo.demofinnhubdb.infra.Code;
import com.hkjava.demo.demofinnhubdb.model.APImodel.CompanyProfile2DTO;
import com.hkjava.demo.demofinnhubdb.model.APImodel.Quote;
import com.hkjava.demo.demofinnhubdb.model.dto.Response.StockDTO;
import com.hkjava.demo.demofinnhubdb.model.dto.Response.StockGetFromDBDTO;
import com.hkjava.demo.demofinnhubdb.model.mapper.FinnhubMapper;
import com.hkjava.demo.demofinnhubdb.repository.StockPriceRepository;
import com.hkjava.demo.demofinnhubdb.repository.StockRepository;
import com.hkjava.demo.demofinnhubdb.service.AdminService;
import com.hkjava.demo.demofinnhubdb.service.WebStockService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WebStockServiceImpl implements WebStockService {

  @Autowired
  AdminService adminService;

  @Autowired
  StockRepository stockRepository;

  @Autowired
  StockPriceRepository stockPriceRepository;

  @Autowired
  FinnhubMapper finnhubMapper;

  @Override
  public StockDTO stockInfo(String symbol) throws FinnhubException {
    CompanyProfile2DTO profile = adminService.getCompanyProfile(symbol);
    Quote quote = adminService.getQuote(symbol);
    if (profile == null && quote == null)
      throw new FinnhubException(Code.THIRD_PARTY_SERVER_UNAVAILABLE);
    return finnhubMapper.map(profile, quote);
  }

  @Override
  public List<StockGetFromDBDTO> stockInfo() throws FinnhubException {
    List<StockPrice> stockPrices = stockPriceRepository.findAll();
    List<Stock> stocks = stockRepository.findAll();
    if (stockPrices == null || stocks == null)
      throw new FinnhubException(Code.THIRD_PARTY_SERVER_UNAVAILABLE);
    return finnhubMapper.map(stocks, stockPrices);
  }

  @Override
  public void addStock(Stock stock) throws FinnhubException {
    stockRepository.save(stock);
    log.info("Stock Add Success");
  }

}
