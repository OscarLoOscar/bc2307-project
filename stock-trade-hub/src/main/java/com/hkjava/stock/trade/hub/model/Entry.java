package com.hkjava.stock.trade.hub.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Entry {

    private double price;

    private int share;

    public void addShare(int share) {
        this.share += share;
    }

    public void deductShare(int share) {
        this.share -= share;
    }

    public void clearShare() {
        this.share = 0;
    }

}
