package com.hkjava.demo.demofinnhub.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import com.hkjava.demo.demofinnhub.model.APImodel.CompanyProfile2DTO;

// simple java class , library , no state -> no Bean
// 如果想寫成Service，需要 @Autowired RestTemplate restTemplate;
public class StockRestTemplate {
  // @Autowired
  RestTemplate restTemplate;

  static final String url = " ";// 分離code

  public StockRestTemplate(RestTemplate restTemplate) {
    if (restTemplate == null)
      throw new IllegalArgumentException();
    this.restTemplate = restTemplate;
  }

  // not a library ,library is no state
  public CompanyProfile2DTO getPrice(String symbol) {
    String url = "xxxx";
    return restTemplate.getForObject(url, CompanyProfile2DTO.class);
  }
}
