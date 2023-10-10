package com.hkjava.demo.demofinnhubdb.model.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hkjava.demo.demofinnhubdb.entity.Stock;
import com.hkjava.demo.demofinnhubdb.entity.StockPrice;
import com.hkjava.demo.demofinnhubdb.entity.StockSymbol;
import com.hkjava.demo.demofinnhubdb.model.APImodel.CompanyProfile2DTO;
import com.hkjava.demo.demofinnhubdb.model.APImodel.Quote;
import com.hkjava.demo.demofinnhubdb.model.APImodel.Symbol;
import com.hkjava.demo.demofinnhubdb.model.dto.Response.CompanyProfileDTO;
import com.hkjava.demo.demofinnhubdb.model.dto.Response.StockDTO;
import com.hkjava.demo.demofinnhubdb.model.dto.Response.StockGetFromDBDTO;

@Component
public class FinnhubMapper {

  @Autowired
  private ModelMapper modelMapper;

  public StockDTO map(CompanyProfile2DTO companyProfile, Quote quote) {
    DateTimeFormatter formatter =
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime localDateTime =
        LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    return StockDTO.builder() //
        .symbol(companyProfile.getTicker())//
        .localdate(localDateTime.format(formatter))//
        .companyProfile(
            modelMapper.map(companyProfile, CompanyProfileDTO.class)) //
        .currentPrice(quote.getCurrentPrice()) //
        .dayHigh(quote.getDayHigh()) //
        .dayLow(quote.getDayLow()) //
        .dayOpen(quote.getDayOpen()) //
        .prevDayClose(quote.getPrevDayClose()) //
        .build();
  }

  public StockSymbol map(Symbol symbol) {
    return StockSymbol.builder()//
        .symbol(symbol.getSymbol())//
        .build();
  }

  public Stock map(CompanyProfile2DTO companyProfile) {
    return Stock.builder()//
        .country(companyProfile.getCountry())//
        .companyName(companyProfile.getCompanyName())//
        .logo(companyProfile.getLogo())//
        .marketCap(companyProfile.getMarketCap())//
        .currency(companyProfile.getCurrency())//
        .build();
  }

  public StockPrice map(Quote quote) {
    return StockPrice.builder()//
        .datetime(LocalDateTime.now())//
        .currentPrice(quote.getCurrentPrice())//
        .dayHigh(quote.getDayHigh())//
        .dayLow(quote.getDayLow())//
        .dayLow(quote.getDayLow())//
        .dayOpen(quote.getDayOpen())//
        .prevDayClose(quote.getPrevDayClose())//
        .build();
  }

  public List<StockGetFromDBDTO> map(List<Stock> stocks,
      List<StockPrice> stockPrices) {
    List<StockGetFromDBDTO> result = new ArrayList<>();
    for (int i = 0; i < stocks.size(); i++) {
      StockGetFromDBDTO dto = new StockGetFromDBDTO();
      dto.setStock(stocks.get(i));//
      dto.setStockPrice(stockPrices.get(i));
      result.add(dto);
    }
    return result;
    // StockGetFromDBDTO[].builder()//
    // .stock(modelMapper.map(stockPrices, Stock[].class))//
    // .stockPrice(modelMapper.map(stockPrices, StockPrice[].class))//
    // .build();
  }

}
