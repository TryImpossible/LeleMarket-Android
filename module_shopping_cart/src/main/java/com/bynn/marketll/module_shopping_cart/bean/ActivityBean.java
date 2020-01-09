package com.bynn.marketll.module_shopping_cart.bean;

import lombok.Data;

@Data
public class ActivityBean {

    /**
     * nowPrice : 214.5
     * discount : 0
     */

    private double nowPrice;
    private int discount;

    public double getNowPrice() {
        return nowPrice;
    }

    public void setNowPrice(double nowPrice) {
        this.nowPrice = nowPrice;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}
