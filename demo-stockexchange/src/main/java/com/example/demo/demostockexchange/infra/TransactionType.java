package com.example.demo.demostockexchange.infra;

import lombok.Getter;

@Getter
public enum TransactionType {
  BUY(0), //
  SELL(1),//
  ;

  int transactionType;

  private TransactionType(int transactionType) {
    this.transactionType = transactionType;
  }

  public TransactionType valurOf(String input) {
    if (input.equals(TransactionType.BUY.name())) {
      return TransactionType.BUY;
    } else if (input.equals(TransactionType.SELL.name())) {
      return TransactionType.SELL;
    }
    return null;

  }
}
