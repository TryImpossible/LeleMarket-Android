package com.bynn.marketll.module_shopping_cart.bean;

import java.util.List;

import lombok.Data;

@Data
public class ShopBean {

    /**
     * isUnicom : 0
     * about : 0
     * typeId : 2
     * flagId : 43
     * pre_url : http://file.diy.51app.cn/wp/goodsType/999042fa.jpg
     * coverImg : http://file.diy.51app.cn/
     * origin :
     * wh_size :
     * cover_size :
     * texture_ids : 134_138
     * goodsId : 43
     * isselect : 1
     * user_id : 3
     * shopNo : G1619968259
     * sortName : 非定制
     * textureName : 粉色,iPhone5/5S/SE
     * imgUrl : http://file.diy.51app.cn/wp/goodsType/999042fa.jpg
     * fileType : xxx
     * num : 1
     * name : 超薄背夹式移动电源
     * ispostage : 1
     * free_count : 1
     * nowPrice : 96.6
     * creat_time : 1578533748000
     * actId : 6
     * type : 10
     * ischange_texture : 1
     * preImgNum : ["http://file.diy.51app.cn/wp/goodsType/999042fa.jpg"]
     * isZero : false
     */

    private int          isUnicom;
    private int          about;
    private int          typeId;
    private int          flagId;
    private String       pre_url;
    private String       coverImg;
    private String       origin;
    private String       wh_size;
    private String       cover_size;
    private String       texture_ids;
    private int          goodsId;
    private int          isselect;
    private int          user_id;
    private String       shopNo;
    private String       sortName;
    private String       textureName;
    private String       imgUrl;
    private String       fileType;
    private int          num;
    private String       name;
    private int          ispostage;
    private int          free_count;
    private double       nowPrice;
    private long         creat_time;
    private int          actId;
    private int          type;
    private int          ischange_texture;
    private boolean      isZero;
    private List<String> preImgNum;
    private boolean      isEdit;
}
