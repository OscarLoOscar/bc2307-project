package com.hkjava.demo.demofinnhubdb.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.hkjava.demo.demofinnhubdb.entity.Stock;
import com.hkjava.demo.demofinnhubdb.entity.StockPrice;
import com.hkjava.demo.demofinnhubdb.entity.StockSymbol;
import com.hkjava.demo.demofinnhubdb.exception.FinnhubException;
import com.hkjava.demo.demofinnhubdb.infra.Code;
import com.hkjava.demo.demofinnhubdb.infra.Protocol;
import com.hkjava.demo.demofinnhubdb.model.APImodel.CompanyProfile2DTO;
import com.hkjava.demo.demofinnhubdb.model.APImodel.Quote;
import com.hkjava.demo.demofinnhubdb.model.APImodel.Symbol;
import com.hkjava.demo.demofinnhubdb.model.mapper.FinnhubMapper;
import com.hkjava.demo.demofinnhubdb.repository.StockPriceRepository;
import com.hkjava.demo.demofinnhubdb.repository.StockRepository;
import com.hkjava.demo.demofinnhubdb.repository.SymbolRepository;
import com.hkjava.demo.demofinnhubdb.service.AdminService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AdminServiceHolder implements AdminService {

  @Autowired
  FinnhubMapper finnhubMapper;

  @Autowired
  StockRepository stockRepository;

  @Autowired
  StockPriceRepository stockPriceRepository;

  @Autowired
  SymbolRepository symbolRepository;

  @Autowired
  private RestTemplate restTemplate;

  @Value(value = "${api.finnhubAPI.domain}")
  private String domain;

  @Value(value = "${api.finnhubAPI.base-url}")
  private String baseUrl;

  @Value(value = "${api.finnhubAPI.endpoints.stock.data}")
  private String companyProfile2Endpoint;

  @Value(value = "${api.finnhubAPI.endpoints.stock.quote}")
  private String quoteEndpoint;

  @Value(value = "${api.finnhubAPI.endpoints.stock.stockSymbol}")
  private String stocksymbolEndpoint;

  @Override
  public CompanyProfile2DTO getCompanyProfile(String symbol)
      throws FinnhubException {
    String url = UriComponentsBuilder.newInstance() //
        .scheme(Protocol.HTTP.name()) //
        .host(domain) //
        .pathSegment(baseUrl) //
        .path(companyProfile2Endpoint) //
        .queryParam("symbol", symbol) //
        .build() //
        .toUriString();
    try {
      return restTemplate.getForObject(url, CompanyProfile2DTO.class);
    } catch (RestClientException e) {
      throw new FinnhubException(Code.FINNHUB_PROFILE2_NOTFOUND);
    }
  }

  @Override // missing APIRESP
  public Quote getQuote(String symbol) throws FinnhubException {
    String url = UriComponentsBuilder.newInstance() //
        .scheme(Protocol.HTTP.name()) //
        .host(domain) //
        .pathSegment(baseUrl) //
        .path(quoteEndpoint) //
        .queryParam("symbol", symbol) //
        .build() //
        .toUriString();
    try {
      return restTemplate.getForObject(url, Quote.class);
    } catch (RestClientException e) {
      throw new FinnhubException(Code.FINNHUB_QUOTE_NOTFOUND);
    }
  }

  @Override
  public List<Symbol> getStockSymbol() throws FinnhubException {

    String url = UriComponentsBuilder.newInstance() //
        .scheme(Protocol.HTTP.name()) //
        .host(domain) // f
        .pathSegment(baseUrl) //
        .path(stocksymbolEndpoint) //
        // .queryParam("exchange", "US") //
        // .queryParam("symbol", symbol) //
        .build() //
        .toUriString();
    System.out.println("StockSymbol url = " + url);
    // try {
    return Arrays.asList(restTemplate.getForObject(url, Symbol[].class));

    // } catch (RestClientException e) {
    // throw new FinnhubException(Code.FINNHUB_PROFILE2_NOTFOUND);
    // }
  }

  @Override
  public void deleteAllCompany() {
    stockRepository.deleteAll();
  }

  @Override
  public void deleteAllStockSymbol() {
    symbolRepository.deleteAll();
  }

  @Override
  public List<StockSymbol> save(List<Symbol> symbols) {
    List<StockSymbol> stockSymbols = symbols.stream()//
        .filter(s -> "Common Stock".equals(s.getType())) //
        .map(s -> finnhubMapper.map(s))// convert to Entity
        .collect(Collectors.toList());
    // save to DB
    return symbolRepository.saveAll(stockSymbols);
  }

    @Override
  public void refresh() throws FinnhubException {
    // Target :
    // getCompanyProfile(String symbol)
    symbolRepository.findAll().stream()//
        .forEach(symbol -> {
          try {
            // Get Company Profile 2 (New) and insert into database
            CompanyProfile2DTO newProfile =
                this.getCompanyProfile(symbol.getSymbol());
            // Old Stock
            Optional<Stock> oldStock =
                stockRepository.findByStockSymbol(symbol);
            // id & symbol no change
            if (oldStock.isPresent()) {
              Stock stock = oldStock.get();
              stock.setCountry(newProfile.getCountry());
              stock.setCompanyName(newProfile.getCompanyName());
              stock.setCurrency(newProfile.getCurrency());
              stock.setMarketCap(newProfile.getMarketCap());
              stock.setLogo(newProfile.getLogo());

              if (newProfile != null
                  && newProfile.getTicker().equals(symbol.getSymbol())) {
                log.info("newProfile.getTicker() : " + newProfile.getTicker());
                stock.setStockStatus('A');
              } else {
                stock.setStockStatus('I');
              }
              stockRepository.save(stock);
              log.info("complete symbol = " + symbol.getSymbol());
              // Get stock price and save a new record of price in to DB
              Quote quote = this.getQuote(symbol.getSymbol());
              StockPrice stockPrice = finnhubMapper.map(quote);
              stockPrice.setStock(stock);
              stockPriceRepository.save(stockPrice);
              log.info("complete symbol = " + symbol.getSymbol());
            } else {
              System.out.println(symbol.getSymbol() + "is NOT FOUND");
            }
          } catch (FinnhubException e) {
            log.info("RestClientException : Symbol " + symbol.getSymbol());
          }
        });
    // if normal response , findBySymbol
    // if abnormal response , patch Entity status to 'I'
    // CompanyProfile conventData = this.getCompanyProfile(symbol);
    // if (!conventData.getTicker().equals(symbol))
    // return null;
    // return conventData;

  }

}
