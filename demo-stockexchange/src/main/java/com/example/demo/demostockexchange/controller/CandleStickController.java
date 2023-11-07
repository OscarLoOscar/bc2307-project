package com.example.demo.demostockexchange.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.demostockexchange.controller.impl.CandleStickOperation;
import com.example.demo.demostockexchange.model.Candle;
import com.example.demo.demostockexchange.services.CandleService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class CandleStickController implements CandleStickOperation {

  @Autowired
  CandleService candleService;

  @Override
  public Candle getCandleData(String symbol, String resolution, String fromDate,
      String toDate) {
    try {
      // Create SimpleDateFormat instance with the date format of your input
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Adjust the format as needed

      // Parse the 'fromDate' and 'toDate' strings to Date objects
      Date fromDateObj = dateFormat.parse(fromDate);
      log.info("fromDateObj : " + fromDateObj);
      Date toDateObj = dateFormat.parse(toDate);
      log.info("toDateObj : " + toDateObj);

      // Convert Date objects to Unix timestamps
      long fromTimestamp = fromDateObj.getTime() / 1000; // Divide by 1000 to convert to seconds
      long toTimestamp = toDateObj.getTime() / 1000; // Divide by 1000 to convert to seconds

      return candleService.getCandleData(symbol, resolution, fromTimestamp,
          toTimestamp);

    } catch (Exception e) {
      e.printStackTrace();
      // Handle parsing or other exceptions
    }

    throw new UnsupportedOperationException(
        "Unimplemented method 'getCandleData'");

  }

}
