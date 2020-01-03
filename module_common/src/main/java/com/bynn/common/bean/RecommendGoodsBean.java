package com.bynn.common.bean;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Data;

@Data
public class RecommendGoodsBean implements Parcelable {

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
    private int    sort;
    private int    isSale;
    private String saleTitle;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.icoUrl);
        dest.writeInt(this.type);
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeFloat(this.nowPrice);
        dest.writeString(this.sell);
        dest.writeInt(this.ispostage);
        dest.writeInt(this.speAct);
        dest.writeString(this.act);
        dest.writeInt(this.companyId);
        dest.writeString(this.isSelf);
        dest.writeString(this.title);
        dest.writeInt(this.sort);
        dest.writeInt(this.isSale);
        dest.writeString(this.saleTitle);
    }

    protected RecommendGoodsBean(Parcel in) {
        this.icoUrl = in.readString();
        this.type = in.readInt();
        this.id = in.readInt();
        this.name = in.readString();
        this.nowPrice = in.readFloat();
        this.sell = in.readString();
        this.ispostage = in.readInt();
        this.speAct = in.readInt();
        this.act = in.readString();
        this.companyId = in.readInt();
        this.isSelf = in.readString();
        this.title = in.readString();
        this.sort = in.readInt();
        this.isSale = in.readInt();
        this.saleTitle = in.readString();
    }

    public static final Creator<RecommendGoodsBean> CREATOR = new Creator<RecommendGoodsBean>() {
        @Override
        public RecommendGoodsBean createFromParcel(Parcel source) {
            return new RecommendGoodsBean(source);
        }

        @Override
        public RecommendGoodsBean[] newArray(int size) {
            return new RecommendGoodsBean[size];
        }
    };
}
