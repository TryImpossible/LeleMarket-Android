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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeList(this.keywords);
    }

    protected KeywordBean(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.keywords = new ArrayList<KeywordBean>();
        in.readList(this.keywords, KeywordBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<KeywordBean> CREATOR = new Parcelable.Creator<KeywordBean>() {
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
