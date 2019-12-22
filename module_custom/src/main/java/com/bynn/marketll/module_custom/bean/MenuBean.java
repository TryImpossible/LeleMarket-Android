package com.bynn.marketll.module_custom.bean;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Data;

@Data
public class MenuBean implements Parcelable {

    /**
     * id : 116
     * name : H热门推荐
     */

    private int id;
    private String name;
    private boolean selected;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeByte(this.selected ? (byte) 1 : (byte) 0);
    }

    protected MenuBean(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.selected = in.readByte() != 0;
    }

    public static final Creator<MenuBean> CREATOR = new Creator<MenuBean>() {
        @Override
        public MenuBean createFromParcel(Parcel source) {
            return new MenuBean(source);
        }

        @Override
        public MenuBean[] newArray(int size) {
            return new MenuBean[size];
        }
    };
}
