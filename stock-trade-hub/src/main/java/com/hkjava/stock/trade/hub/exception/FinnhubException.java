package com.hkjava.stock.trade.hub.exception;

import com.hkjava.stock.trade.hub.infra.BusinessException;
import com.hkjava.stock.trade.hub.infra.Code;

public class FinnhubException extends BusinessException {

  public FinnhubException(Code code) {
    super(code);
  }

}
