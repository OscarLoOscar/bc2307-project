package com.hkjava.demo.demofinnhub.model.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hkjava.demo.demofinnhub.model.APImodel.CompanyProfile2DTO;
import com.hkjava.demo.demofinnhub.model.APImodel.Quote;
import com.hkjava.demo.demofinnhub.model.dto.Response.CompanyProfileDTO;
import com.hkjava.demo.demofinnhub.model.dto.Response.StockDTO;

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
}
