package com.example.demo.demostockexchange.controller.impl;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.example.demo.demostockexchange.entity.Transaction;
import com.example.demo.demostockexchange.exception.ApiResponse;
import com.example.demo.demostockexchange.exception.FinnhubException;

public interface OrderControllerOperation {

        @PostMapping("/trade/symbol/{symbol}")
        @ResponseStatus(HttpStatus.OK)
        public ApiResponse<Transaction> placeOrder(@PathVariable String symbol,
                        @RequestParam String action,//
                        @RequestParam String orderType,//
                        @RequestParam double price,//
                        @RequestParam int quantity//
                        ) throws FinnhubException;
}
