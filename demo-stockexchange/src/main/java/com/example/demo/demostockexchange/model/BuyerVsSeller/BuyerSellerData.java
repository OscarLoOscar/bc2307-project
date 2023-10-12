package com.example.demo.demostockexchange.model.BuyerVsSeller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuyerSellerData{
  private int buyerVolume;
  private int sellerVolume;

  public BuyerSellerData(int buyerVolume, int sellerVolume) {
    this.buyerVolume = buyerVolume;
    this.sellerVolume = sellerVolume;
  }
  
}
