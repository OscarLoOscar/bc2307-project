// package com.hkjava.stock.trade.hub.model;

// import com.hkjava.stock.trade.hub.enums.Action;
// import com.hkjava.stock.trade.hub.enums.OrderType;
// import com.hkjava.stock.trade.hub.exception.FinnhubException;
// import com.hkjava.stock.trade.hub.infra.Code;
// import lombok.AllArgsConstructor;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;

// @Getter
// @Setter
// @AllArgsConstructor
// @NoArgsConstructor
// public class Product {

//     private String stockName; //

//     private OrderType bidAsk;

//     public Order getAsk() throws FinnhubException {
//         int idx = -1;
//         for (int i = 0; i < this.getBidAsk().getOrders().size(); i++) {
//             if (this.getBidAsk().getOrders().get(i)
//                     .getBuySell() == Action.SELL) {
//                 idx = i;
//                 break;
//             }
//         }
//         if (idx == -1 || idx == 0)
//             throw new FinnhubException(Code.MARKET_NOTENOUGH_PRODUCT);
//         return this.getBidAsk().getOrders().get(idx);
//     }

//     public Order getBid() throws FinnhubException {
//         int idx = -1;
//         for (int i = 0; i < this.getBidAsk().getOrders().size(); i++) {
//             if (this.getBidAsk().getOrders().get(i)
//                     .getBuySell() == Action.SELL) {
//                 idx = i;
//                 break;
//             }
//         }
//         if (idx == -1 || idx == 0)
//             throw new FinnhubException(Code.MARKET_NOTENOUGH_PRODUCT);
//         return this.getBidAsk().getOrders().get(idx - 1);
//     }

//     public void removeAsk() throws FinnhubException {
//         int idx = -1;
//         for (int i = 0; i < this.getBidAsk().getOrders().size(); i++) {
//             if (this.getBidAsk().getOrders().get(i)
//                     .getBuySell() == Action.SELL) {
//                 idx = i;
//                 break;
//             }
//         }
//         if (idx == -1 || idx == 0)
//             throw new FinnhubException(Code.MARKET_NOTENOUGH_PRODUCT);
//         this.getBidAsk().getOrders().remove(idx);
//     }

//     public void removeBid() throws FinnhubException {
//         int idx = -1;
//         for (int i = 0; i < this.getBidAsk().getOrders().size(); i++) {
//             if (this.getBidAsk().getOrders().get(i)
//                     .getBuySell() == Action.SELL) {
//                 idx = i;
//                 break;
//             }
//         }
//         if (idx == -1 || idx == 0)
//             throw new FinnhubException(Code.MARKET_NOTENOUGH_PRODUCT);
//         this.getBidAsk().getOrders().remove(idx - 1);
//     }


// }