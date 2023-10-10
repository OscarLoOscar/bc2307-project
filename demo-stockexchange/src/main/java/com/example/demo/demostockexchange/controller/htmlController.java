package com.example.demo.demostockexchange.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.ui.Model;

@Controller
// @RequestMapping("/test")
public class htmlController {
  @GetMapping("/kLine")
  @ResponseStatus(value = HttpStatus.OK)
  public String kLine(Model model) {

    return "kLine";
  }

  @GetMapping("/atAuctionsOrders")
  @ResponseStatus(value = HttpStatus.OK)
  public String atAuctionsOrders(Model model) {
    return "atAuctionsOrders";
  }

}
