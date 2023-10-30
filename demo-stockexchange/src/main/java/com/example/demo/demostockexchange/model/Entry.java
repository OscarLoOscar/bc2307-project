package com.example.demo.demostockexchange.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Entry {


    private double price;

    private int share;

    public Entry(double price, int share) {
        if (price < 0.0d || share <= 0) {
            throw new IllegalArgumentException("Invalid price or share value");
        }
        this.price = price;
        this.share = share;
    }

    public void addShare(int share) {
        this.share += share;
    }

    public void deductShare(int share) {
        if( share > this.share)
        throw new IllegalArgumentException("Cannot deduct more shares than available");
        this.share -= share;
    }

    public void clearShare() {
        this.share = 0;
    }

}
