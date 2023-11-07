package com.example.demo.demostockexchange.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SymbolValidator
    implements ConstraintValidator<SymbolCheck, String> {

  @Override
  public boolean isValid(String symbol, ConstraintValidatorContext context) {
    // Check if the symbol is either "BID" or "ASK"
    return "BUY".equals(symbol) || "SELL".equals(symbol)
        || "MARKET".equals(symbol) || "LIMIT".equals(symbol);
  }
}
