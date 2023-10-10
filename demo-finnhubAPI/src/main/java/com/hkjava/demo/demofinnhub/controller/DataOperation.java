package com.hkjava.demo.demofinnhub.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.hkjava.demo.demofinnhub.exception.FinnhubException;
import com.hkjava.demo.demofinnhub.model.APImodel.CompanyProfile2DTO;
import com.hkjava.demo.demofinnhub.model.dto.Request.SymbolReqDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public interface DataOperation {

  @Operation(summary = "Insert Stock Data into DataBase", //
      description = "This endpoint is to insert stock data into database", //
      tags = "Save a Stock")
  @ApiResponses({//
      @ApiResponse(responseCode = "200", //
          content = {@Content(
              schema = @Schema(implementation = CompanyProfile2DTO.class), //
              mediaType = "application/json")}), //
      @ApiResponse(responseCode = "404", //
          content = {@Content(schema = @Schema())}), //
      @ApiResponse(responseCode = "500", //
          content = {@Content(schema = @Schema())})})
  @GetMapping(value = "/data/stock")
  @ResponseStatus(value = HttpStatus.OK)
  CompanyProfile2DTO getCompanyProfile( //?symbol=xxxx
      @RequestParam("symbol") String symbol) throws FinnhubException;
}
