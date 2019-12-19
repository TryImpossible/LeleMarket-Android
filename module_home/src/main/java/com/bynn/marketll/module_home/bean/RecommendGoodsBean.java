package com.bynn.marketll.module_home.bean;

import lombok.Data;

@Data
public class RecommendGoodsBean {

    /**
     * icoUrl : https://api.51app.cn/resource/diymall/wp/IntegralMall2_0/d2d53149.jpg
     * type : 2
     * id : 397
     * name : DIY男士短款钱包
     * nowPrice : 58
     * sell : 94
     * ispostage : 1
     * speAct : 0
     * act : 无
     * companyId : 2
     * isSelf : true
     * title : null
     */

    private String icoUrl;
    private int    type;
    private int    id;
    private String name;
    private float  nowPrice;
    private String sell;
    private int    ispostage;
    private int    speAct;
    private String act;
    private int    companyId;
    private String isSelf;
    private String title;
}
