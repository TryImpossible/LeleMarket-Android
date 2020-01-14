package com.bynn.common.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import lombok.Data;

@Data
public class GoodsBean implements Parcelable {

    /**
     * id : 18
     * imgUrl : https://api.51app.cn/resource/diymall/uu20/special/9dcf69fd.jpg
     * param1 : 3D定制手机壳
     * param2 :
     * param3 :
     * type : 2
     * pid : 19
     * layout : 1
     */

    private int                      id;
    private String                   imgUrl;
    private String                   param1;
    private String                   param2;
    private String                   param3;
    private int                      type;
    private int                      pid;
    private int                      layout;
    private String                   pre_url;
    private int                      activity_id;
    private String                   icoUrl;
    private List<String>             paramList;
    private List<String>             lable;
    private String                   label;
    private float                    nowPrice;
    private float                    org_price;
    private List<RecommendGoodsBean> recommend;
    private int                      isBoutique;
    private int                      ispostage;
    private String                   texture_ids;
    private boolean                  isFree;
    private String                   sell;
    private String                   name;
    private String                   isSelf;
    private int                      companyId;
    private String                   activity;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.imgUrl);
        dest.writeString(this.param1);
        dest.writeString(this.param2);
        dest.writeString(this.param3);
        dest.writeInt(this.type);
        dest.writeInt(this.pid);
        dest.writeInt(this.layout);
        dest.writeString(this.pre_url);
        dest.writeInt(this.activity_id);
        dest.writeString(this.icoUrl);
        dest.writeStringList(this.paramList);
        dest.writeStringList(this.lable);
        dest.writeString(this.label);
        dest.writeFloat(this.nowPrice);
        dest.writeFloat(this.org_price);
        dest.writeTypedList(this.recommend);
        dest.writeInt(this.isBoutique);
        dest.writeInt(this.ispostage);
        dest.writeString(this.texture_ids);
        dest.writeByte(this.isFree ? (byte) 1 : (byte) 0);
        dest.writeString(this.sell);
        dest.writeString(this.name);
        dest.writeString(this.isSelf);
        dest.writeInt(this.companyId);
        dest.writeString(this.activity);
    }

    protected GoodsBean(Parcel in) {
        this.id = in.readInt();
        this.imgUrl = in.readString();
        this.param1 = in.readString();
        this.param2 = in.readString();
        this.param3 = in.readString();
        this.type = in.readInt();
        this.pid = in.readInt();
        this.layout = in.readInt();
        this.pre_url = in.readString();
        this.activity_id = in.readInt();
        this.icoUrl = in.readString();
        this.paramList = in.createStringArrayList();
        this.lable = in.createStringArrayList();
        this.label = in.readString();
        this.nowPrice = in.readFloat();
        this.org_price = in.readFloat();
        this.recommend = in.createTypedArrayList(RecommendGoodsBean.CREATOR);
        this.isBoutique = in.readInt();
        this.ispostage = in.readInt();
        this.texture_ids = in.readString();
        this.isFree = in.readByte() != 0;
        this.sell = in.readString();
        this.name = in.readString();
        this.isSelf = in.readString();
        this.companyId = in.readInt();
        this.activity = in.readString();
    }

    public static final Creator<GoodsBean> CREATOR = new Creator<GoodsBean>() {
        @Override
        public GoodsBean createFromParcel(Parcel source) {
            return new GoodsBean(source);
        }

        @Override
        public GoodsBean[] newArray(int size) {
            return new GoodsBean[size];
        }
    };
}
