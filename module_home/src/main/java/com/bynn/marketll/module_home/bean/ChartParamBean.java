package com.bynn.marketll.module_home.bean;

import java.util.List;

import lombok.Data;

@Data
public class ChartParamBean {
    
    /**
     * textureId : 378
     * imgs : ["https://api.51app.cn/resource/diymall/wp/IntegralMall2_0/2d312e0a.jpg","https://api.51app.cn/resource/diymall/wp/IntegralMall2_0/c5ed35e7.jpg","https://api.51app.cn/resource/diymall/wp/IntegralMall2_0/17977c73.jpg","https://api.51app.cn/resource/diymall/wp/IntegralMall2_0/abb27281.jpg"]
     * serviceImg : https://api.51app.cn/resource/diymall/wp/IntegralMall/c618b6b6.jpg
     * goodsURLStr : https://api.51app.cn/diyMall/v3.0.0/goodsDetails.html?id=27&type=4&userId
     * detailURLStr : https://api.51app.cn/diyMall/v3.0.0/diyDetails.html?id=27&type=4
     * shareTitle : 活动分享：乐乐定制，一家专门做定制的平台
     * appraiseURLStr : https://api.51app.cn/diyMall/v3.0.0/comment.html?id=27&goodsType=0
     * shareUrl : https://api.51app.cn/diyMall/v3.0.0/others/download-lele.html
     * shareLogo : https://api.51app.cn/resource/diymall/wp/IntegralMall2_0/2d312e0a.jpg
     * newgoodid : 177
     */

    private String       textureId;
    private String       serviceImg;
    private String       goodsURLStr;
    private String       detailURLStr;
    private String       shareTitle;
    private String       appraiseURLStr;
    private String       shareUrl;
    private String       shareLogo;
    private int          newgoodid;
    private List<String> imgs;
}
