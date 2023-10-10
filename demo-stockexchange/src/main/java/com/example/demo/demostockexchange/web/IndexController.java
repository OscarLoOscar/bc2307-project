package com.example.demo.demostockexchange.web;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.demo.demostockexchange.controller.WebSocketController;
import com.example.demo.demostockexchange.exception.FinnhubException;
import com.example.demo.demostockexchange.model.StockExchange;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/testing")
public class IndexController {

  @Autowired
  WebSocketController webSocketController;

  @GetMapping(value = "/stock/{stock}" )
  public String index(@PathVariable("stock") String stockId,Model model) 
      throws FinnhubException {
    // ModelAndView modelAndView = new ModelAndView();
    // log.info("modelAndView 1 : " + modelAndView);
    // modelAndView.setViewName("atAuctionOrders");
    // log.info("modelAndView 2 : " + modelAndView);

    // get data
    Map<String, StockExchange> response =
        webSocketController.atAuctionOrders(stockId);
            log.info("model 3 : " + response);

    // add data to object
    // modelAndView.addObject("response", response);
    // log.info("modelAndView 3 : " + modelAndView.getModel());

    // // Add more attributes if needed
    model.addAttribute("response", response);
    log.info("model 4 : " + model);

    // log.info("model 5 : " + modelAndView);
    return "atAuctionOrders";

  }
}
