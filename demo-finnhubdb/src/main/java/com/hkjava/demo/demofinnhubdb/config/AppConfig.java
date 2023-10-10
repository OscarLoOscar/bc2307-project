package com.hkjava.demo.demofinnhubdb.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hkjava.demo.demofinnhubdb.infra.AppleRestClient;
import com.hkjava.demo.demofinnhubdb.infra.RedisHelper;
import com.hkjava.demo.demofinnhubdb.infra.RedisObjectMapper;
import com.hkjava.demo.demofinnhubdb.infra.StockRestTemplate;

@Configuration
public class AppConfig {

  @Bean
  ModelMapper modelMapper() {
    return new ModelMapper();
  }

  @Bean
  ObjectMapper objectMapper() {
    return new ObjectMapper();
  }

  @Bean
  AppleRestClient stockService(RestTemplate restTemplate) {
    return new AppleRestClient();
  }

  @Bean
  StockRestTemplate stockRestTemplate(RestTemplate restTemplate) {// 自動去context搵
    return new StockRestTemplate(restTemplate);
  }// run code 前，要有時間行restTemplate參數

  @Bean
  RestTemplate restTemplate() {// method name -> bean name
    return new RestTemplate();
  }

  @Bean
  ObjectMapper redisObjectMapper() {
    return RedisObjectMapper.of();
  }

  @Bean
  RedisHelper redisProfileHelper(RedisConnectionFactory factory, //
      ObjectMapper redisObjectMapper) {
    return new RedisHelper(factory, redisObjectMapper);
  }
}
