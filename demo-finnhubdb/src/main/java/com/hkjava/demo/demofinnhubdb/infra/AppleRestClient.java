package com.hkjava.demo.demofinnhubdb
.infra;

import org.springframework.web.client.RestTemplate;
import com.hkjava.demo.demofinnhubdb
.model.APImodel.CompanyProfile2DTO;

// @Service // Bean
public class AppleRestClient { // Service

  // @Autowired // Bean
  RestTemplate restTemplate;

  static final String url = "xxxx";

  public AppleRestClient() {
    this.restTemplate = new RestTemplate();
  }

  public AppleRestClient(RestTemplate restTemplate) {
    if (restTemplate == null)
      throw new IllegalArgumentException();
    this.restTemplate = restTemplate;
  }

  public CompanyProfile2DTO invokeForCompanyProfile(String url) {
    return restTemplate.getForObject(url, CompanyProfile2DTO.class);
  }

  public CompanyProfile2DTO[] invokeForCompanyProfileList(String url) {
    return restTemplate.getForObject(url, CompanyProfile2DTO[].class);
  }

  public CompanyProfile2DTO getProfile(String symbol) {
    // String url = "xxxx";
    return restTemplate.getForObject(url, CompanyProfile2DTO.class);
  }

}
