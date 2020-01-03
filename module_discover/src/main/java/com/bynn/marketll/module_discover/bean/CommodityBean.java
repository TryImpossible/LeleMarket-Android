package com.bynn.marketll.module_discover.bean;

import lombok.Data;

@Data
public class CommodityBean {
    private int    id;
    private String title;
    private String author;
    private String count;
    private String img;
    private String headImge;
    private float  view;
    private int    type;
    private String url;
    private String ctime;
    private String sys;
}
