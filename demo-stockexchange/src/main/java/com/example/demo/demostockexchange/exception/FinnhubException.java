package com.example.demo.demostockexchange.exception;

import com.example.demo.demostockexchange.infra.BusinessException;
import com.example.demo.demostockexchange.infra.Code;

public class FinnhubException extends BusinessException {

  public FinnhubException(Code code) {
    super(code);
  }

}
