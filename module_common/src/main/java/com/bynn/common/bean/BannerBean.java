package com.bynn.common.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class BannerBean implements Parcelable {

    /**
     * id : 68
     * imgUrl : https://api.51app.cn/resource/diymall/uu20/special/752ced27.png
     * type : 2
     * about : 52
     * title : null
     * cont : null
     * sys : all
     */

    private int id;
    private String imgUrl;
    private int type;
    private String about;
    private String title;
    private String cont;
    private String sys;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.imgUrl);
        dest.writeInt(this.type);
        dest.writeString(this.about);
        dest.writeString(this.title);
        dest.writeString(this.cont);
        dest.writeString(this.sys);
    }

    protected BannerBean(Parcel in) {
        this.id = in.readInt();
        this.imgUrl = in.readString();
        this.type = in.readInt();
        this.about = in.readString();
        this.title = in.readString();
        this.cont = in.readString();
        this.sys = in.readString();
    }

    public static final Creator<BannerBean> CREATOR = new Creator<BannerBean>() {
        @Override
        public BannerBean createFromParcel(Parcel source) {
            return new BannerBean(source);
        }

        @Override
        public BannerBean[] newArray(int size) {
            return new BannerBean[size];
        }
    };

    public static List<String> getBannerImageList(List<BannerBean> banners) {
        List<String> list = new ArrayList<>();
        for (BannerBean bean : banners) {
            list.add(bean.getImgUrl());
        }
        return list;
    }
}
