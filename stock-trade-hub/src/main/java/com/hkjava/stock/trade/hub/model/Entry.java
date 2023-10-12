package com.hkjava.stock.trade.hub.model;

import lombok.Builder;

@Builder
public class Entry {
    
    private double price;

    private int share;

}
