package com.bynn.marketll.module_main.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class KeywordBean implements Parcelable {
    private int               id;
    private String            name;
    private List<KeywordBean> keywords;

    public KeywordBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeTypedList(this.keywords);
    }

    protected KeywordBean(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.keywords = in.createTypedArrayList(KeywordBean.CREATOR);
    }

    public static final Creator<KeywordBean> CREATOR = new Creator<KeywordBean>() {
        @Override
        public KeywordBean createFromParcel(Parcel source) {
            return new KeywordBean(source);
        }

        @Override
        public KeywordBean[] newArray(int size) {
            return new KeywordBean[size];
        }
    };
}
