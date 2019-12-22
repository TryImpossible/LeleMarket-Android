package com.bynn.marketll.module_home.bean;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Data;

/**
 * 首页导航条
 */
@Data
public class TopNavBean implements Parcelable {

    private int    id;
    private String name;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
    }

    protected TopNavBean(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<TopNavBean> CREATOR = new Parcelable.Creator<TopNavBean>() {
        @Override
        public TopNavBean createFromParcel(Parcel source) {
            return new TopNavBean(source);
        }

        @Override
        public TopNavBean[] newArray(int size) {
            return new TopNavBean[size];
        }
    };
}
