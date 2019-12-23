package com.bynn.marketll.module_mine.bean;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Data;

@Data
public class MineBean implements Parcelable {
    private int icon;
    private String title;

    public MineBean(int icon, String title) {
        this.icon = icon;
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.icon);
        dest.writeString(this.title);
    }

    protected MineBean(Parcel in) {
        this.icon = in.readInt();
        this.title = in.readString();
    }

    public static final Parcelable.Creator<MineBean> CREATOR = new Parcelable.Creator<MineBean>() {
        @Override
        public MineBean createFromParcel(Parcel source) {
            return new MineBean(source);
        }

        @Override
        public MineBean[] newArray(int size) {
            return new MineBean[size];
        }
    };
}
