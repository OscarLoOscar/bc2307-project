package com.example.demo.demostockexchange.infra;

public class BusinessException extends Exception {

  private Code code;

  public int getCode() {
    return this.code.getCode();
  }
  
  public BusinessException(Code code) {
    super(code.getDesc());
    this.code = code;
  }

}
