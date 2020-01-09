package com.bynn.marketll.module_shopping_cart.bean;

import java.util.List;

import lombok.Data;

@Data
public class ShoppingCartBean {
    private List<CouponBean>   couponList;
    private List<ShopBean>     shopList;
    private double             transportfee;
    private double             enoughMoney;
    private List<ActivityBean> activityList;
    private int                companyId;
    private double             remotefee;
    private String             companyName;
}
