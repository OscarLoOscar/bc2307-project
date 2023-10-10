package com.example.demo.demostockexchange.model;

public class Bard {
  
  public boolean sell(double volume, String symbol, double sl, double tp, String comment) {
    double price = getMarketPrice(symbol);

    // Check if the provided parameters are valid
    if (volume <= 0.0 || price <= 0.0 || symbol == null || symbol.isEmpty()) {
        // Invalid parameters, return false
        return false;
    }

    // Create a sell order request
    SellOrderRequest sellOrderRequest = new SellOrderRequest();
    sellOrderRequest.setSymbol(symbol);
    sellOrderRequest.setVolume(volume);
    sellOrderRequest.setPrice(price);
    sellOrderRequest.setSl(sl);
    sellOrderRequest.setTp(tp);
    sellOrderRequest.setComment(comment);

    // Send the sell order request to your trading platform
    SellOrderResponse sellOrderResponse = tradingPlatform.sendSellOrderRequest(sellOrderRequest);

    // Check the result of the trade request
    if (sellOrderResponse.getCode() != TradeResponseCode.SUCCESS) {
        // Trade request failed, return false
        return false;
    }

    // Trade request was successful, return true
    return true;
}


public boolean buy(double volume, String symbol, double price, double sl, double tp, String comment) {
    // Check if the provided parameters are valid
    if (volume <= 0.0 || price <= 0.0 || symbol == null || symbol.isEmpty()) {
        // Invalid parameters, return false
        return false;
    }

    // Create a buy order request
    BuyOrderRequest buyOrderRequest = new BuyOrderRequest();
    buyOrderRequest.setSymbol(symbol);
    buyOrderRequest.setVolume(volume);
    buyOrderRequest.setPrice(price);
    buyOrderRequest.setSl(sl);
    buyOrderRequest.setTp(tp);
    buyOrderRequest.setComment(comment);

    // Send the buy order request to your trading platform
    BuyOrderResponse buyOrderResponse = tradingPlatform.sendBuyOrderRequest(buyOrderRequest);

    // Check the result of the trade request
    if (buyOrderResponse.getCode() != TradeResponseCode.SUCCESS) {
        // Trade request failed, return false
        return false;
    }

    // Trade request was successful, return true
    return true;
}

public boolean buyStop(double volume, double price, String symbol, double sl, double tp,
                      OrderTimeType typeTime, long expiration, String comment) {
    // Check if the provided parameters are valid
    if (volume <= 0.0 || price <= 0.0 || symbol == null || symbol.isEmpty()) {
        // Invalid parameters, return false
        return false;
    }

    // Create a buy stop order request
    BuyStopOrderRequest buyStopOrderRequest = new BuyStopOrderRequest();
    buyStopOrderRequest.setSymbol(symbol);
    buyStopOrderRequest.setVolume(volume);
    buyStopOrderRequest.setPrice(price);
    buyStopOrderRequest.setSl(sl);
    buyStopOrderRequest.setTp(tp);
    buyStopOrderRequest.setTypeTime(typeTime);
    buyStopOrderRequest.setExpiration(expiration);
    buyStopOrderRequest.setComment(comment);

    // Send the buy stop order request to your trading platform
    BuyStopOrderResponse buyStopOrderResponse = tradingPlatform.sendBuyStopOrderRequest(buyStopOrderRequest);

    // Check the result of the trade request
    if (buyStopOrderResponse.getCode() != TradeResponseCode.SUCCESS) {
        // Trade request failed, return false
        return false;
    }

    // Trade request was successful, return true
    return true;
}

public boolean sellStop(double volume, double price, String symbol, double sl, double tp,
                       OrderTimeType typeTime, long expiration, String comment) {
    // Check if the provided parameters are valid
    if (volume <= 0.0 || price <= 0.0 || symbol == null || symbol.isEmpty()) {
        // Invalid parameters, return false
        return false;
    }

    // Create a sell stop order request
    SellStopOrderRequest sellStopOrderRequest = new SellStopOrderRequest();
    sellStopOrderRequest.setSymbol(symbol);
    sellStopOrderRequest.setVolume(volume);
    sellStopOrderRequest.setPrice(price);
    sellStopOrderRequest.setSl(sl);
    sellStopOrderRequest.setTp(tp);
    sellStopOrderRequest.setTypeTime(typeTime);
    sellStopOrderRequest.setExpiration(expiration);
    sellStopOrderRequest.setComment(comment);

    // Send the sell stop order request to your trading platform
    SellStopOrderResponse sellStopOrderResponse = tradingPlatform.sendSellStopOrderRequest(sellStopOrderRequest);

    // Check the result of the trade request
    if (sellStopOrderResponse.getCode() != TradeResponseCode.SUCCESS) {
        // Trade request failed, return false
        return false;
    }

    // Trade request was successful, return true
    return true;
}

---
public boolean buyLimit(double volume, double price, String symbol, double sl, double tp,
                        OrderTimeType typeTime, long expiration, String comment) {
    // Check if the provided parameters are valid
    if (volume <= 0.0 || price <= 0.0 || symbol == null || symbol.isEmpty()) {
        // Invalid parameters, return false
        return false;
    }

    // Create a buy limit order request
    BuyLimitOrderRequest buyLimitOrderRequest = new BuyLimitOrderRequest();
    buyLimitOrderRequest.setSymbol(symbol);
    buyLimitOrderRequest.setVolume(volume);
    buyLimitOrderRequest.setPrice(price);
    buyLimitOrderRequest.setSl(sl);
    buyLimitOrderRequest.setTp(tp);
    buyLimitOrderRequest.setTypeTime(typeTime);
    buyLimitOrderRequest.setExpiration(expiration);
    buyLimitOrderRequest.setComment(comment);

    // Send the buy limit order request to your trading platform
    BuyLimitOrderResponse buyLimitOrderResponse = tradingPlatform.sendBuyLimitOrderRequest(buyLimitOrderRequest);

    // Check the result of the trade request
    if (buyLimitOrderResponse.getCode() != TradeResponseCode.SUCCESS) {
        // Trade request failed, return false
        return false;
    }

    // Trade request was successful, return true
    return true;
}

public boolean sellLimit(double volume, double price, String symbol, double sl, double tp,
                        OrderTimeType typeTime, long expiration, String comment) {
    // Check if the provided parameters are valid
    if (volume <= 0.0 || price <= 0.0 || symbol == null || symbol.isEmpty()) {
        // Invalid parameters, return false
        return false;
    }

    // Create a sell limit order request
    SellLimitOrderRequest sellLimitOrderRequest = new SellLimitOrderRequest();
    sellLimitOrderRequest.setSymbol(symbol);
    sellLimitOrderRequest.setVolume(volume);
    sellLimitOrderRequest.setPrice(price);
    sellLimitOrderRequest.setSl(sl);
    sellLimitOrderRequest.setTp(tp);
    sellLimitOrderRequest.setTypeTime(typeTime);
    sellLimitOrderRequest.setExpiration(expiration);
    sellLimitOrderRequest.setComment(comment);

    // Send the sell limit order request to your trading platform
    SellLimitOrderResponse sellLimitOrderResponse = tradingPlatform.sendSellLimitOrderRequest(sellLimitOrderRequest);

    // Check the result of the trade request
    if (sellLimitOrderResponse.getCode() != TradeResponseCode.SUCCESS) {
        // Trade request failed, return false
        return false;
    }

    // Trade request was successful, return true
    return true;
}
}
