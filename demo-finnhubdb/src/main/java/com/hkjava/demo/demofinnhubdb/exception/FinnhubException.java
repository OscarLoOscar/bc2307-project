package com.hkjava.demo.demofinnhubdb.exception;

import com.hkjava.demo.demofinnhubdb.infra.Code;

public class FinnhubException extends BusinessException {

  public FinnhubException(Code code) {
    super(code);
  }

}
