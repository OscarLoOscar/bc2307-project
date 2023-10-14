package com.example.demo.demostockexchange.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.example.demo.demostockexchange.annotation.SymbolCheck;
import com.example.demo.demostockexchange.entity.Orders;
import com.example.demo.demostockexchange.exception.ApiResponse;
import com.example.demo.demostockexchange.exception.FinnhubException;
import com.example.demo.demostockexchange.model.OrderForm;
import com.example.demo.demostockexchange.model.OrderRequest;
import com.example.demo.demostockexchange.model.OrderResp;
import com.example.demo.demostockexchange.model.BuyerVsSeller.BuyerSellerData;

public interface WebSocketOperation {


        @GetMapping("/updateOrderBook")
        @ResponseStatus(value = HttpStatus.OK)
        public ApiResponse<List<OrderRequest>> updateOrderBook();

        @PostMapping("/trade/symbol/{symbol}")
        @ResponseStatus(HttpStatus.OK)
        public ApiResponse<Orders> placeOrder(@PathVariable String symbol,
        @RequestParam String Action, double price,
        int quantity) throws FinnhubException;

        @GetMapping("/bidQueue")
        @ResponseStatus(value = HttpStatus.OK)
        public List<OrderResp> BidOrdersQueue(
                        @SymbolCheck @RequestParam("stockId") String stockId);

        @GetMapping("/askQueue")
        @ResponseStatus(value = HttpStatus.OK)
        public List<OrderResp> AskOrdersQueue(
                        @SymbolCheck @RequestParam("stockId") String stockId);

        // @CrossOrigin(origins = "http://127.0.0.1:5500")
        // @GetMapping("/atAuctionOrders")
        // @ResponseStatus(value = HttpStatus.OK)
        // // public Map<String, StockExchange> atAuctionOrders(
        // public List<StockExchange> atAuctionOrders(
        // @SymbolCheck @RequestParam("stockId") String stockId)
        // throws FinnhubException;

        @CrossOrigin(origins = "http://127.0.0.1:5500")
        @GetMapping("/indicator")
        public BuyerSellerData getBuyerSellerIndicator();


        // @PostMapping("/executeTrades")
        // public String executeTrades(@RequestBody OrderResp orderResp);
}
