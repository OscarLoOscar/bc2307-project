package com.example.demo.demostockexchange.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.example.demo.demostockexchange.model.Candle;
import com.example.demo.demostockexchange.services.CandleService;

public class CandleServiceImpl implements CandleService{

  @Autowired
  RestTemplate restTemplate;

  @Autowired
  @Qualifier(value = "finnhubToken")
  private String token;

  @Value(value = "${api.finnhub.domain}")
  private String domain;

  @Value(value = "${api.finnhub.base-url}")
  private String baseUrl;

  @Value(value = "${api.finnhub.endpoints.stock.profile2}")
  private String candleStickEndpoint;

  @Override
  public Candle getCandleData(String symbol, String resolution, long fromDate,
      long toDate) {
    String url = UriComponentsBuilder.newInstance()//
        .host(domain)//
        .pathSegment(baseUrl)//
        .path(candleStickEndpoint)//
        .queryParam("symbol", symbol)//
        .queryParam("resolution", resolution)//
        .queryParam("from", fromDate)//
        .queryParam("to", toDate)//
        .queryParam("token", token)//
        .build()//
        .toUriString();
    System.out.println("url : " + url);

    return restTemplate.getForObject(url, Candle.class);
  }
}
