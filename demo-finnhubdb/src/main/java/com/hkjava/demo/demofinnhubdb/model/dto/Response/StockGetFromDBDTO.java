package com.hkjava.demo.demofinnhubdb.model.dto.Response;

import com.hkjava.demo.demofinnhubdb.entity.Stock;
import com.hkjava.demo.demofinnhubdb.entity.StockPrice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StockGetFromDBDTO {
  public Stock stock;
  public StockPrice stockPrice;

}
