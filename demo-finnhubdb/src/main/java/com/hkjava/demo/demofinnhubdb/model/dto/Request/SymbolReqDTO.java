package com.hkjava.demo.demofinnhubdb.model.dto.Request;

import com.hkjava.demo.demofinnhubdb.annotation.SymbolCheck;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class SymbolReqDTO {
  @SymbolCheck
  String symbol;

}
