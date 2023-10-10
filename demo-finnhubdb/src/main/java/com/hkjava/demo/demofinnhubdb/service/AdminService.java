package com.hkjava.demo.demofinnhubdb.service;

import java.util.List;
import com.hkjava.demo.demofinnhubdb.entity.StockSymbol;
import com.hkjava.demo.demofinnhubdb.exception.FinnhubException;
import com.hkjava.demo.demofinnhubdb.model.APImodel.CompanyProfile2DTO;
import com.hkjava.demo.demofinnhubdb.model.APImodel.Quote;
import com.hkjava.demo.demofinnhubdb.model.APImodel.Symbol;
import io.swagger.v3.oas.annotations.Operation;

public interface AdminService {
  @Operation(summary = "Get Raw data by calling API")
  Quote getQuote(String symbol) throws FinnhubException;

  @Operation(summary = "Get Raw data by calling API")
  CompanyProfile2DTO getCompanyProfile(String symbol) throws FinnhubException;

  @Operation(summary = "Get Raw data by calling API")
  List<Symbol> getStockSymbol() throws FinnhubException;

  @Operation(summary = "Used in CommandLineRunner")
  void deleteAllCompany();

  @Operation(summary = "Used in CommandLineRunner")
  void deleteAllStockSymbol();

  List<StockSymbol> save(List<Symbol> symbols);

  void refresh() throws FinnhubException;
  // return CompanyProfile又得，void 又得 , seems like PutMapping

}
