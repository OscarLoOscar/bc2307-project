package com.hkjava.demo.demofinnhubdb.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.hkjava.demo.demofinnhubdb.entity.Stock;
import com.hkjava.demo.demofinnhubdb.exception.FinnhubException;
import com.hkjava.demo.demofinnhubdb.infra.ApiResp;
import com.hkjava.demo.demofinnhubdb.model.APImodel.Symbol;
import com.hkjava.demo.demofinnhubdb.model.dto.Response.StockDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public interface AdminOperation {

  @Operation(summary = "Get Finnhub Stock Data",
      description = "This endpoint retrieves a stock data from Finnhub Endpoints (/stock).",
      tags = "Get a Stock",
      parameters = {@Parameter(name = "SymbolReqDTO",
          description = "Stock Symbol", required = true)})
  @ApiResponses({//
      @ApiResponse(responseCode = "200",
          content = {@Content(schema = @Schema(implementation = Stock.class),
              mediaType = "application/json")}),
      @ApiResponse(responseCode = "404",
          content = {
              @Content(schema = @Schema(implementation = ApiResp.class))}),
      @ApiResponse(responseCode = "500",
          content = {
              @Content(schema = @Schema(implementation = ApiResp.class))})})
  @GetMapping(value = "/stock")
  @ResponseStatus(value = HttpStatus.OK)
  ApiResp<StockDTO> stockInfo(@RequestParam("symbol") String symbol)
      throws FinnhubException;

}