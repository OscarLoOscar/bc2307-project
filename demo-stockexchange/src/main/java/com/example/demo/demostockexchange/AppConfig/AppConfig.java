package com.example.demo.demostockexchange.AppConfig;

import java.time.LocalDate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

  @Value(value = "${api.finnhub.token}")
  private String token;

  @Bean
  String finnhubToken() {
    return token;
  }

  @Bean
  ModelMapper modelMapper() {
    return new ModelMapper();
  }

  @Bean
  RestTemplate restTemplate() {// method name -> bean name
    return new RestTemplate();
  }

  @Bean
  String string() {
    return new String();
  }

  @Bean
  public LocalDate localDate() {
      return LocalDate.now();
}
}