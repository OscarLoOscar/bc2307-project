package com.example.demo.demostockexchange.services.impl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.benproject.infra.Code;
import com.example.benproject.model.trade.BuySell;
import com.example.benproject.model.trade.Order;
import com.example.benproject.model.trade.TradeRecord;
import com.example.benproject.model.trade.Trader;
import com.example.benproject.repository.TradeRecordRepository;
import com.example.benproject.service.TradeService;
import com.example.demo.demostockexchange.exception.FinnhubException;

@Service
public class TradeServiceImpl implements TradeService {

    @Autowired
    private TradeRecordRepository tradeRecordRepository;

    @Autowired
    private Trader trader;

    @Override
    public List<Order> findOrders() {
        return Trader.demoProduct.getBidAsk().getOrders();
    }

    // marketOrder
    @Override
    public void marketOrder(String stockName, String buySell, Integer quantity)
            throws FinnhubException {
        // Hard code
        // Trader trader = new Trader(); // 有bean
        if (BuySell.valueOf(buySell.toUpperCase()) == BuySell.BUY) {
            while (trader.getMoney() >= Trader.demoProduct.getAsk().getPrice() * quantity && quantity > 0) {
                // 隊->第一個sell
                // 第一個就成交哂
                if (Trader.demoProduct.getAsk().getQuantity() > quantity) {
                    // 隊改quantity
                    Trader.demoProduct.getAsk().setQuantity(Trader.demoProduct.getAsk().getQuantity() - quantity);
                    // account 減錢
                    if (trader.getMoney() >= Trader.demoProduct.getAsk().getPrice() * quantity)
                        trader.setMoney(trader.getMoney() - Trader.demoProduct.getAsk().getPrice() * quantity);
                    else
                        throw new FinnhubException(Code.TRADER_NOTENOUGH_MONEY);
                    // 倉加貨
                    // 倉可能係null
                    trader.getProducts().put(Trader.demoProduct, trader.getProducts().get(Trader.demoProduct) == null ? quantity : trader.getProducts().get(Trader.demoProduct) + quantity); 
                    tradeRecordRepository.save(TradeRecord.builder().price(Trader.demoProduct.getAsk().getPrice()).quantity(quantity).localDateTime(LocalDateTime.now()).build());
                    quantity = 0;

                } else {
                    quantity -= Trader.demoProduct.getAsk().getQuantity();
                    // 減錢
                    if (trader.getMoney() >= Trader.demoProduct.getAsk().getPrice() * quantity)
                        trader.setMoney(trader.getMoney() - Trader.demoProduct.getAsk().getQuantity() * Trader.demoProduct.getAsk().getPrice());
                    else
                        throw new FinnhubException(Code.TRADER_NOTENOUGH_MONEY);
                    // 倉加貨
                    // 倉可能係null
                    trader.getProducts().put(Trader.demoProduct, trader.getProducts().get(Trader.demoProduct) == null ? Trader.demoProduct.getAsk().getQuantity() : trader.getProducts().get(Trader.demoProduct) + Trader.demoProduct.getAsk().getQuantity()); 
                    tradeRecordRepository.save(TradeRecord.builder().price(Trader.demoProduct.getAsk().getPrice()).quantity(Trader.demoProduct.getAsk().getQuantity()).localDateTime(LocalDateTime.now()).build());
                    // 隊remove一個盤，去下一個繼續成交
                    Trader.demoProduct.removeAsk();
                }
            }
        }
        if (BuySell.valueOf(buySell.toUpperCase()) == BuySell.SELL) {
            if (trader.getProducts().get(Trader.demoProduct) == null || trader.getProducts().get(Trader.demoProduct) < quantity) {
                throw new FinnhubException(Code.TRADER_NOTENOUGH_PRODUCT);
            } else {
                while(quantity > 0) {
                    if (Trader.demoProduct.getBid().getQuantity() > quantity) {
                        Trader.demoProduct.getBid().setQuantity(Trader.demoProduct.getBid().getQuantity() - quantity);
                        // map一定有，唔洗check null
                        trader.setMoney(trader.getMoney() + quantity * Trader.demoProduct.getBid().getPrice());
                        trader.getProducts().put(Trader.demoProduct, trader.getProducts().get(Trader.demoProduct) - quantity);
                        tradeRecordRepository.save(TradeRecord.builder().price(Trader.demoProduct.getBid().getPrice()).quantity(quantity).localDateTime(LocalDateTime.now()).build());
                        quantity = 0;
                    } else {
                        quantity -= Trader.demoProduct.getBid().getQuantity();
                        trader.setMoney(trader.getMoney() + Trader.demoProduct.getBid().getQuantity() * Trader.demoProduct.getBid().getPrice());
                        // map一定有，唔洗check null
                        trader.getProducts().put(Trader.demoProduct, trader.getProducts().get(Trader.demoProduct) - Trader.demoProduct.getBid().getQuantity());
                        tradeRecordRepository.save(TradeRecord.builder().price(Trader.demoProduct.getBid().getPrice()).quantity(Trader.demoProduct.getBid().getQuantity()).localDateTime(LocalDateTime.now()).build());
                        Trader.demoProduct.removeBid();
                    }
                }
            }
        }
        Collections.sort(Trader.demoProduct.getBidAsk().getOrders());
    }

    // limitedOrder
    @Override
    public void limitOrder(String stockName, String buySell, Double price,
            Integer quantity) throws FinnhubException {

        // Trader trader = new Trader(); // 有bean
        if (BuySell.valueOf(buySell.toUpperCase()) == BuySell.BUY && trader.getMoney() < price * quantity)
                throw new FinnhubException(Code.TRADER_NOTENOUGH_MONEY);
        if (BuySell.valueOf(buySell.toUpperCase()) == BuySell.SELL && (trader.getProducts().get(Trader.demoProduct) == null || trader.getProducts().get(Trader.demoProduct) < quantity))
                throw new FinnhubException(Code.TRADER_NOTENOUGH_PRODUCT);

        if (BuySell.valueOf(buySell.toUpperCase()) == BuySell.BUY) {
            // 太低價不成交
            if (price < Trader.demoProduct.getAsk().getPrice()) {
                // 直接order排隊
                Trader.demoProduct.getBidAsk().getOrders().add(new Order(trader.getId(), BuySell.valueOf(buySell.toUpperCase()), price, quantity, LocalDateTime.now()));
                trader.setMoney(trader.getMoney() - price * quantity);
                quantity = 0;
            } else {
                // 有成交
                // quantity 一邊成交一邊減 , 
                while (quantity > 0 && Trader.demoProduct.getAsk().getPrice() <= price) {
                    // 直接成交哂
                    if (Trader.demoProduct.getAsk().getQuantity() > quantity) {
                        Trader.demoProduct.getAsk().setQuantity(Trader.demoProduct.getAsk().getQuantity() - quantity);
                        trader.setMoney(trader.getMoney() - Trader.demoProduct.getAsk().getPrice() * quantity);
                        trader.getProducts().put(Trader.demoProduct, trader.getProducts().get(Trader.demoProduct) == null ? quantity : trader.getProducts().get(Trader.demoProduct) + quantity);
                        tradeRecordRepository.save(TradeRecord.builder().price(Trader.demoProduct.getAsk().getPrice()).quantity(quantity).localDateTime(LocalDateTime.now()).build());
                        quantity = 0;
                    } else {
                        quantity -= Trader.demoProduct.getAsk().getQuantity();
                        trader.setMoney(trader.getMoney() - Trader.demoProduct.getAsk().getPrice() * quantity);
                        trader.getProducts().put(Trader.demoProduct, trader.getProducts().get(Trader.demoProduct) == null ? Trader.demoProduct.getAsk().getQuantity() : trader.getProducts().get(Trader.demoProduct) + Trader.demoProduct.getAsk().getQuantity());
                        tradeRecordRepository.save(TradeRecord.builder().price(Trader.demoProduct.getAsk().getPrice()).quantity(Trader.demoProduct.getAsk().getQuantity()).localDateTime(LocalDateTime.now()).build());
                        Trader.demoProduct.removeAsk();
                    }
                }
            }
        }

        if (BuySell.valueOf(buySell.toUpperCase()) == BuySell.SELL) {
            if (price > Trader.demoProduct.getBid().getPrice()) {
                Trader.demoProduct.getBidAsk().getOrders().add(new Order(trader.getId(), BuySell.valueOf(buySell.toUpperCase()), price, quantity, LocalDateTime.now()));
                trader.getProducts().put(Trader.demoProduct, trader.getProducts().get(Trader.demoProduct) - quantity);
                quantity = 0;
            } else {
                while (quantity > 0 && Trader.demoProduct.getBid().getPrice() >= price) {
                    if (Trader.demoProduct.getBid().getQuantity() > quantity) {
                        Trader.demoProduct.getBid().setQuantity(Trader.demoProduct.getBid().getQuantity() - quantity);
                        trader.setMoney(trader.getMoney() + quantity * Trader.demoProduct.getBid().getPrice());
                        trader.getProducts().put(Trader.demoProduct, trader.getProducts().get(Trader.demoProduct) - quantity);
                        tradeRecordRepository.save(TradeRecord.builder().price(Trader.demoProduct.getBid().getPrice()).quantity(quantity).localDateTime(LocalDateTime.now()).build());
                        quantity = 0;
                    } else {
                        quantity -= Trader.demoProduct.getBid().getQuantity();
                        trader.setMoney(trader.getMoney() + Trader.demoProduct.getBid().getQuantity() * Trader.demoProduct.getBid().getPrice());
                        trader.getProducts().put(Trader.demoProduct, trader.getProducts().get(Trader.demoProduct) - Trader.demoProduct.getBid().getQuantity());
                        tradeRecordRepository.save(TradeRecord.builder().price(Trader.demoProduct.getBid().getPrice()).quantity(Trader.demoProduct.getBid().getQuantity()).localDateTime(LocalDateTime.now()).build());
                        Trader.demoProduct.removeBid();
                    }
                }
            }
        }
        if (quantity > 0) {
            Trader.demoProduct.getBidAsk().getOrders().add(new Order(trader.getId(), BuySell.valueOf(buySell.toUpperCase()), price, quantity, LocalDateTime.now()));
            if (BuySell.valueOf(buySell.toUpperCase()) == BuySell.SELL) {
                // 放出去的盤要係倉減
                trader.getProducts().put(Trader.demoProduct, trader.getProducts().get(Trader.demoProduct) - quantity);
            }
        }
        Collections.sort(Trader.demoProduct.getBidAsk().getOrders());
    }

    @Override
    public List<TradeRecord> findAll() {
      return tradeRecordRepository.findAll();
    }

}