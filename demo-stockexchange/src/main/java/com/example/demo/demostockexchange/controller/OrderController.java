package com.example.demo.demostockexchange.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.example.demo.demostockexchange.entity.Orders;
import com.example.demo.demostockexchange.exception.ApiResponse;
import com.example.demo.demostockexchange.exception.FinnhubException;
import com.example.demo.demostockexchange.model.OrderForm;

public interface OrderController {

        @PostMapping("/trade/symbol/{symbol}")
        @ResponseStatus(HttpStatus.OK)
        public ApiResponse<Orders> placeOrder(@PathVariable String symbol,
                        @RequestBody OrderForm orderForm) throws FinnhubException;
}
