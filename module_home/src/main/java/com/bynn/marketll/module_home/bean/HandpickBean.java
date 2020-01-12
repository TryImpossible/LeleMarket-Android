package com.bynn.marketll.module_home.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class HandpickBean implements Parcelable {

    /**
     * id : 150
     * imgUrl : https://api.51app.cn/resource/diymall/uu20/special/b866b7de.png
     * param1 : 二合一伸缩数据线
     * param2 :
     * param3 :
     * type : 4
     * pid : 182
     * module : 1
     * url :
     */

    private int id;
    private String imgUrl;
    private String param1;
    private String param2;
    private String param3;
    private int type;
    private int pid;
    private int module;
    private String url;

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
        dest.writeInt(this.module);
        dest.writeString(this.url);
    }

    protected HandpickBean(Parcel in) {
        this.id = in.readInt();
        this.imgUrl = in.readString();
        this.param1 = in.readString();
        this.param2 = in.readString();
        this.param3 = in.readString();
        this.type = in.readInt();
        this.pid = in.readInt();
        this.module = in.readInt();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<HandpickBean> CREATOR = new Parcelable.Creator<HandpickBean>() {
        @Override
        public HandpickBean createFromParcel(Parcel source) {
            return new HandpickBean(source);
        }

        @Override
        public HandpickBean[] newArray(int size) {
            return new HandpickBean[size];
        }
    };

    public static List<String> getHandPickImageList(List<HandpickBean> handpick) {
        List<String> list = new ArrayList<>();
        for (HandpickBean bean : handpick) {
            list.add(bean.getImgUrl());
        }
        return list;
    }
}
