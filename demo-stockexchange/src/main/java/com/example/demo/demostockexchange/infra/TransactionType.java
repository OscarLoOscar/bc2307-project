package com.example.demo.demostockexchange.infra;

import lombok.Getter;

@Getter
public enum TransactionType {
  BUY(0), //
  SELL(1),//
  COMBINED(2),//
  ;

  int transactionType;

  private TransactionType(int transactionType) {
    this.transactionType = transactionType;
  }
}