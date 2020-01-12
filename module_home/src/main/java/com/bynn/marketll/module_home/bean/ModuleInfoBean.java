package com.bynn.marketll.module_home.bean;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Data;

@Data
public class ModuleInfoBean implements Parcelable {

    /**
     * name : 定制手机壳系列
     * introduce : 以前，我从不加掩饰，以为所有大牌，只要把logo大大的露在外面就是阔气。
     现在，我更喜欢给他们穿上外衣，时尚，低调，而有品。
     * imgUrl : https://api.51app.cn/resource/diymall/app/module/1f2bf1e5.jpg
     */

    private String name;
    private String introduce;
    private String imgUrl;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.introduce);
        dest.writeString(this.imgUrl);
    }

    protected ModuleInfoBean(Parcel in) {
        this.name = in.readString();
        this.introduce = in.readString();
        this.imgUrl = in.readString();
    }

    public static final Parcelable.Creator<ModuleInfoBean> CREATOR = new Parcelable.Creator<ModuleInfoBean>() {
        @Override
        public ModuleInfoBean createFromParcel(Parcel source) {
            return new ModuleInfoBean(source);
        }

        @Override
        public ModuleInfoBean[] newArray(int size) {
            return new ModuleInfoBean[size];
        }
    };
}
