package com.example.demo.demostockexchange.annotation;

import java.util.Objects;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SymbolValidator
    implements ConstraintValidator<SymbolCheck, String> {

  @Override
  public boolean isValid(String symbol, ConstraintValidatorContext context) {
    // Check if the symbol is either "BID" or "ASK"
    return "BID".equals(symbol) || "ASK".equals(symbol);
  }
}
