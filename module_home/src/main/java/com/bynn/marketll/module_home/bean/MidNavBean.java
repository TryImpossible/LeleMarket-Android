package com.bynn.marketll.module_home.bean;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Data;

@Data
public class MidNavBean implements Parcelable {

    /**
     * imgUrl : https://api.51app.cn/resource/diymall/uu20/special/730fcecf.png
     * id : 108
     * platform : all
     * name : 每日优选
     * about : https://api.51app.cn/diyMall/v3.0.0/subjects/everydayPreferred.html
     * key : 3
     */

    private String imgUrl;
    private int    id;
    private String platform;
    private String name;
    private String about;
    private String key;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.imgUrl);
        dest.writeInt(this.id);
        dest.writeString(this.platform);
        dest.writeString(this.name);
        dest.writeString(this.about);
        dest.writeString(this.key);
    }

    protected MidNavBean(Parcel in) {
        this.imgUrl = in.readString();
        this.id = in.readInt();
        this.platform = in.readString();
        this.name = in.readString();
        this.about = in.readString();
        this.key = in.readString();
    }

    public static final Parcelable.Creator<MidNavBean> CREATOR = new Parcelable.Creator<MidNavBean>() {
        @Override
        public MidNavBean createFromParcel(Parcel source) {
            return new MidNavBean(source);
        }

        @Override
        public MidNavBean[] newArray(int size) {
            return new MidNavBean[size];
        }
    };
}
