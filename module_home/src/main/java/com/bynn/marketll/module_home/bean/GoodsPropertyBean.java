package com.bynn.marketll.module_home.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class GoodsPropertyBean implements Parcelable {

    /**
     * texture : [{"texture_ids":"378","org_price":"129.00","now_price":"89.00","pre_url":"https://api.51app.cn/resource/diymall/wp/goodsType/6e335c17.jpg","coverImg":"","carousel":"","save_size":"","origin":"","wh_size":"","cover_size":""},{"texture_ids":"379","org_price":"129.00","now_price":"89.00","pre_url":"https://api.51app.cn/resource/diymall/wp/goodsType/bae17df3.jpg","coverImg":"","carousel":"","save_size":"","origin":"","wh_size":"","cover_size":""},{"texture_ids":"380","org_price":"129.00","now_price":"89.00","pre_url":"https://api.51app.cn/resource/diymall/wp/goodsType/91bfc9d6.jpg","coverImg":"","carousel":"","save_size":"","origin":"","wh_size":"","cover_size":""}]
     * list : [{"title":"颜色分类","list":[{"id":378,"gname":"白色","title":"颜色分类"},{"id":379,"gname":"粉色","title":"颜色分类"},{"id":380,"gname":"蓝色","title":"颜色分类"}]}]
     * activityId : 6
     * activity : 爱上生活，爱上定制
     * now_price : 89.00
     * org_price : 129.00
     * origin :
     * wh_size :
     * cover_size :
     * pre_url : https://api.51app.cn/resource/diymall/wp/goodsType/6e335c17.jpg
     * id : 27
     * storeId : 5
     * dfTexture : 378
     * type : 1
     * isFree : false
     */

    private String            activityId;
    private String            activity;
    private float            now_price;
    private float            org_price;
    private String            origin;
    private String            wh_size;
    private String            cover_size;
    private String            pre_url;
    private int               id;
    private String            storeId;
    private String            dfTexture;
    private int               type;
    private boolean           isFree;
    private List<TextureBean> texture;
    private List<ListBeanX>   list;



    @Data
    public static class TextureBean {
        /**
         * texture_ids : 378
         * org_price : 129.00
         * now_price : 89.00
         * pre_url : https://api.51app.cn/resource/diymall/wp/goodsType/6e335c17.jpg
         * coverImg :
         * carousel :
         * save_size :
         * origin :
         * wh_size :
         * cover_size :
         */

        private String texture_ids;
        private String org_price;
        private String now_price;
        private String pre_url;
        private String coverImg;
        private String carousel;
        private String save_size;
        private String origin;
        private String wh_size;
        private String cover_size;
    }

    @Data
    public static class ListBeanX {
        /**
         * title : 颜色分类
         * list : [{"id":378,"gname":"白色","title":"颜色分类"},{"id":379,"gname":"粉色","title":"颜色分类"},{"id":380,"gname":"蓝色","title":"颜色分类"}]
         */

        private String         title;
        private List<ListBean> list;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 378
             * gname : 白色
             * title : 颜色分类
             */

            private int    id;
            private String gname;
            private String title;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.activityId);
        dest.writeString(this.activity);
        dest.writeFloat(this.now_price);
        dest.writeFloat(this.org_price);
        dest.writeString(this.origin);
        dest.writeString(this.wh_size);
        dest.writeString(this.cover_size);
        dest.writeString(this.pre_url);
        dest.writeInt(this.id);
        dest.writeString(this.storeId);
        dest.writeString(this.dfTexture);
        dest.writeInt(this.type);
        dest.writeByte(this.isFree ? (byte) 1 : (byte) 0);
        dest.writeList(this.texture);
        dest.writeList(this.list);
    }

    protected GoodsPropertyBean(Parcel in) {
        this.activityId = in.readString();
        this.activity = in.readString();
        this.now_price = in.readFloat();
        this.org_price = in.readFloat();
        this.origin = in.readString();
        this.wh_size = in.readString();
        this.cover_size = in.readString();
        this.pre_url = in.readString();
        this.id = in.readInt();
        this.storeId = in.readString();
        this.dfTexture = in.readString();
        this.type = in.readInt();
        this.isFree = in.readByte() != 0;
        this.texture = new ArrayList<TextureBean>();
        in.readList(this.texture, TextureBean.class.getClassLoader());
        this.list = new ArrayList<ListBeanX>();
        in.readList(this.list, ListBeanX.class.getClassLoader());
    }

    public static final Parcelable.Creator<GoodsPropertyBean> CREATOR = new Parcelable.Creator<GoodsPropertyBean>() {
        @Override
        public GoodsPropertyBean createFromParcel(Parcel source) {
            return new GoodsPropertyBean(source);
        }

        @Override
        public GoodsPropertyBean[] newArray(int size) {
            return new GoodsPropertyBean[size];
        }
    };
}
