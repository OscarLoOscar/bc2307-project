package com.hkjava.stock.trade.hub.infra;
// package com.example.demo.demostockexchange.infra;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.client.RestTemplate;

// // simple java class , library , no state -> no Bean
// // 如果想寫成Service，需要 @Autowired RestTemplate restTemplate;
// public class StockRestTemplate {
//   // @Autowired
//   RestTemplate restTemplate;

//   static final String url = " ";//分離code 

//   public StockRestTemplate(RestTemplate restTemplate) {
//     if (restTemplate == null)
//       throw new IllegalArgumentException();
//     this.restTemplate = restTemplate;
//   }

//   // not a library ,library is no state
//   public CompanyProfile getPrice(String symbol) {
//     String url = "xxxx";
//     return restTemplate.getForObject(url, CompanyProfile.class);
//   }
// }
