package com.bynn.marketll.module_home.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class CustomizationBean implements Parcelable {

    /**
     * id : 60
     * imgUrl : https://api.51app.cn/resource/diymall/uu20/special/aeba69a1.jpg
     * type : 1
     * pid : 15
     * url : https://api.51app.cn/diyMall/v3.0.0/subjects/special-phoneShell.html
     * goods : [{"id":18,"imgUrl":"https://api.51app.cn/resource/diymall/uu20/special/9dcf69fd.jpg","param1":"3D定制手机壳","param2":"","param3":"","type":2,"pid":19,"layout":1},{"id":21,"imgUrl":"https://api.51app.cn/resource/diymall/uu20/special/79cd0ed9.jpg","param1":"DIY定制手机壳","param2":"","param3":"","type":2,"pid":1,"layout":1},{"id":19,"imgUrl":"https://api.51app.cn/resource/diymall/uu20/special/38bca84a.jpg","param1":"木质手机壳","param2":"","param3":"","type":4,"pid":487,"layout":1},{"id":25,"imgUrl":"https://api.51app.cn/resource/diymall/uu20/special/ac31c8dd.jpg","param1":"DIY大肚保温杯","param2":"","param3":"","type":2,"pid":52,"layout":1}]
     */

    private int             id;
    private String          imgUrl;
    private int             type;
    private int             pid;
    private String          url;
    private List<GoodsBean> goods;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.imgUrl);
        dest.writeInt(this.type);
        dest.writeInt(this.pid);
        dest.writeString(this.url);
        dest.writeList(this.goods);
    }

    protected CustomizationBean(Parcel in) {
        this.id = in.readInt();
        this.imgUrl = in.readString();
        this.type = in.readInt();
        this.pid = in.readInt();
        this.url = in.readString();
        this.goods = new ArrayList<GoodsBean>();
        in.readList(this.goods, GoodsBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<CustomizationBean> CREATOR = new Parcelable.Creator<CustomizationBean>() {
        @Override
        public CustomizationBean createFromParcel(Parcel source) {
            return new CustomizationBean(source);
        }

        @Override
        public CustomizationBean[] newArray(int size) {
            return new CustomizationBean[size];
        }
    };
}
