package com.bynn.marketll.module_home.bean;

import android.os.Parcel;
import android.os.Parcelable;

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

    private int id;
    private String imgUrl;
    private String param1;
    private String param2;
    private String param3;
    private int type;
    private int pid;
    private int layout;

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
    }

    public static final Parcelable.Creator<GoodsBean> CREATOR = new Parcelable.Creator<GoodsBean>() {
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
