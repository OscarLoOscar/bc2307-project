package com.hkjava.demo.demofinnhubdb.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hkjava.demo.demofinnhubdb.controller.AdminOperation;
import com.hkjava.demo.demofinnhubdb.exception.FinnhubException;
import com.hkjava.demo.demofinnhubdb.infra.ApiResp;
import com.hkjava.demo.demofinnhubdb.model.dto.Response.StockDTO;
import com.hkjava.demo.demofinnhubdb.service.AdminService;
import com.hkjava.demo.demofinnhubdb.service.WebStockService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/vtxlab/v1")
public class AdminController implements AdminOperation {

  @Autowired
  AdminService adminService;

  @Autowired
  WebStockService webStockService;

  @Override
  public ApiResp<StockDTO> stockInfo(String symbol) // ""
      throws FinnhubException {
    if (symbol.isBlank())
      throw new IllegalArgumentException("Parameter Symbol is blank");
    return ApiResp.<StockDTO>builder() //
        .ok() //
        .data(webStockService.stockInfo(symbol)) //
        .build();
  }
  
}