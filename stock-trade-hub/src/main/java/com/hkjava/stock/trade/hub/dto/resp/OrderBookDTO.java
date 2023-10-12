package com.hkjava.stock.trade.hub.dto.resp;

import java.util.List;

import com.hkjava.stock.trade.hub.model.Entry;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderBookDTO {

  private String symbol;

  private List<Entry> buyBook;

  private List<Entry> sellBook;

}
