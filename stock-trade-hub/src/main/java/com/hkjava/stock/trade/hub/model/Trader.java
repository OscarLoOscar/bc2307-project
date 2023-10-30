package com.hkjava.stock.trade.hub.model;

import java.util.HashMap;
import lombok.Getter;
import lombok.Setter;

public class Trader {



  @Getter
  @Setter



  // Buyer Seller
  private long id;

  private double money = 200000;

  // public static Product demoProduct = new Product("ABC", new BidAsk());

  // private HashMap<Product, Integer> products = new HashMap<>(); //

  // {
  //   products.put(demoProduct, 1000);
  // }

  // 可以做：在指定 BidAsk 做 new order 的 method



  // 市價盤

  // public void marketOrder(Product product, BuySell buySell, int quantity) throws FinnhubException {

  // if (buySell == Action.BUY) {
  // while (this.money >= product.getAsk().getPrice() * quantity && quantity > 0) {
  // // 隊->第一個sell
  // // 第一個就成交哂
  // if (product.getAsk().getQuantity() > quantity) {
  // // 隊改quantity
  // product.getAsk().setQuantity(product.getAsk().getQuantity() - quantity);
  // // account 減錢
  // if (this.money >= product.getAsk().getPrice() * quantity)
  // this.money -= product.getAsk().getPrice() * quantity;
  // else
  // throw new FinnhubException(Code.TRADER_NOTENOUGH_MONEY);
  // // 倉加貨
  // // 倉可能係null
  // this.products.put(product, this.products.get(product) == null ? quantity : this.products.get(product) + quantity);
  // // tradeRecordRepository.save(TradeRecord.builder().price(product.getAsk().getPrice()).quantity(quantity).build());
  // quantity = 0;

  // } else {
  // quantity -= product.getAsk().getQuantity();
  // // 減錢
  // if (this.money >= product.getAsk().getPrice() * quantity)
  // this.money -= product.getAsk().getQuantity() * product.getAsk().getPrice();
  // else
  // throw new FinnhubException(Code.TRADER_NOTENOUGH_MONEY);
  // // 倉加貨
  // // 倉可能係null
  // this.products.put(product, this.products.get(product) == null ? product.getAsk().getQuantity() : this.products.get(product) + product.getAsk().getQuantity());
  // // tradeRecordRepository.save(TradeRecord.builder().price(product.getAsk().getPrice()).quantity(product.getAsk().getQuantity()).build());
  // // 隊remove一個盤，去下一個繼續成交
  // product.removeAsk();
  // }
  // }
  // }

  // if (buySell == Action.SELL) {
  // if (this.products.get(product) == null || this.products.get(product) < quantity) {
  // throw new FinnhubException(Code.TRADER_NOTENOUGH_PRODUCT);
  // } else {
  // while(quantity > 0) {
  // if (product.getBid().getQuantity() > quantity) {
  // product.getBid().setQuantity(product.getBid().getQuantity() - quantity);
  // // map一定有，唔洗check null
  // this.money += quantity * product.getBid().getPrice();
  // this.products.put(product, this.products.get(product) - quantity);
  // // tradeRecordRepository.save(TradeRecord.builder().price(product.getBid().getPrice()).quantity(quantity).build());
  // quantity = 0;
  // } else {
  // quantity -= product.getBid().getQuantity();
  // this.money += product.getBid().getQuantity() * product.getBid().getPrice();
  // // map一定有，唔洗check null
  // this.products.put(product, this.products.get(product) - product.getBid().getQuantity());
  // // tradeRecordRepository.save(TradeRecord.builder().price(product.getBid().getPrice()).quantity(product.getBid().getQuantity()).build());
  // product.removeBid();
  // }
  // }
  // }
  // }
  // Collections.sort(product.getBidAsk().getOrders());
  // }

  // // 限價盤
  // public void limitOrder(Product product, BuySell buySell, double price, int quantity) throws FinnhubException{

  // // check if trader have sufficent product for sell or sufficient money for buy
  // if (buySell == Action.BUY && this.money < price * quantity)
  // throw new FinnhubException(Code.TRADER_NOTENOUGH_MONEY);
  // if (buySell == Action.SELL && (this.products.get(product) == null || this.products.get(product) < quantity))
  // throw new FinnhubException(Code.TRADER_NOTENOUGH_PRODUCT);

  // if (buySell == Action.BUY) {
  // // 太低價不成交
  // if (price < product.getAsk().getPrice()) {
  // // 直接order排隊
  // product.getBidAsk().getOrders().add(new Order(1l, buySell, price, quantity, LocalDateTime.now()));
  // quantity = 0;
  // } else {
  // // 有成交
  // // quantity 一邊成交一邊減 ,
  // while (quantity > 0 && product.getAsk().getPrice() <= price) {
  // // 直接成交哂
  // if (product.getAsk().getQuantity() > quantity) {
  // product.getAsk().setQuantity(product.getAsk().getQuantity() - quantity);
  // this.money -= product.getAsk().getPrice() * quantity;
  // this.products.put(product, this.products.get(product) == null ? quantity : this.products.get(product) + quantity);
  // // tradeRecordRepository.save(TradeRecord.builder().price(product.getAsk().getPrice()).quantity(quantity).build());
  // quantity = 0;
  // } else {
  // quantity -= product.getAsk().getQuantity();
  // this.money -= product.getAsk().getPrice() * quantity;
  // this.products.put(product, this.products.get(product) == null ? product.getAsk().getQuantity() : this.products.get(product) + product.getAsk().getQuantity());
  // // tradeRecordRepository.save(TradeRecord.builder().price(product.getAsk().getPrice()).quantity(product.getAsk().getQuantity()).build());
  // product.removeAsk();
  // }
  // }
  // }
  // }

  // if (buySell == Action.SELL) {
  // if (price > product.getBid().getPrice()) {
  // product.getBidAsk().getOrders().add(new Order(this.id, buySell, price, quantity, LocalDateTime.now()));
  // quantity = 0;
  // } else {
  // while (quantity > 0 && product.getBid().getPrice() >= price) {
  // if (product.getBid().getQuantity() > quantity) {
  // product.getBid().setQuantity(product.getBid().getQuantity() - quantity);
  // this.money += quantity * product.getBid().getPrice();
  // this.products.put(product, this.products.get(product) - quantity);
  // // tradeRecordRepository.save(TradeRecord.builder().price(product.getBid().getPrice()).quantity(quantity).build());
  // quantity = 0;
  // } else {
  // quantity -= product.getBid().getQuantity();
  // this.money += product.getBid().getQuantity() * product.getBid().getPrice();
  // this.products.put(product, this.products.get(product) - product.getBid().getQuantity());
  // // tradeRecordRepository.save(TradeRecord.builder().price(product.getBid().getPrice()).quantity(product.getBid().getQuantity()).build());
  // product.removeBid();
  // }
  // }
  // }
  // }
  // if (quantity > 0)
  // product.getBidAsk().getOrders().add(new Order(this.id, buySell, price, quantity, LocalDateTime.now()));
  // Collections.sort(product.getBidAsk().getOrders());
  // }


}
