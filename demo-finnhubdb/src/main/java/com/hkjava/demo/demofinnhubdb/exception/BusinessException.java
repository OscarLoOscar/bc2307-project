package com.hkjava.demo.demofinnhubdb.exception;

import com.hkjava.demo.demofinnhubdb.infra.Code;

public class BusinessException extends Exception {

  private int code;

  public int getCode() {
    return this.code;
  }
  
  public BusinessException(Code code) {
    super(code.getDesc());
    this.code = code.getCode();
  }

}
