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
    private float             now_price;
    private float             org_price;
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
    private int               amount;

    public GoodsPropertyBean() {
        this.amount = 1;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        if (amount <= 0) {
            this.amount = 1;
        } else {
            this.amount = amount;
        }
    }

    @Data
    public static class TextureBean implements Parcelable {
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.texture_ids);
            dest.writeString(this.org_price);
            dest.writeString(this.now_price);
            dest.writeString(this.pre_url);
            dest.writeString(this.coverImg);
            dest.writeString(this.carousel);
            dest.writeString(this.save_size);
            dest.writeString(this.origin);
            dest.writeString(this.wh_size);
            dest.writeString(this.cover_size);
        }

        protected TextureBean(Parcel in) {
            this.texture_ids = in.readString();
            this.org_price = in.readString();
            this.now_price = in.readString();
            this.pre_url = in.readString();
            this.coverImg = in.readString();
            this.carousel = in.readString();
            this.save_size = in.readString();
            this.origin = in.readString();
            this.wh_size = in.readString();
            this.cover_size = in.readString();
        }

        public static final Creator<TextureBean> CREATOR = new Creator<TextureBean>() {
            @Override
            public TextureBean createFromParcel(Parcel source) {
                return new TextureBean(source);
            }

            @Override
            public TextureBean[] newArray(int size) {
                return new TextureBean[size];
            }
        };
    }

    @Data
    public static class ListBeanX implements Parcelable {
        /**
         * title : 颜色分类
         * list : [{"id":378,"gname":"白色","title":"颜色分类"},{"id":379,"gname":"粉色","title":"颜色分类"},{"id":380,"gname":"蓝色","title":"颜色分类"}]
         */

        private String         title;
        private List<ListBean> list;

        @Data
        public static class ListBean implements Parcelable {
            /**
             * id : 378
             * gname : 白色
             * title : 颜色分类
             */

            private int     id;
            private String  gname;
            private String  title;
            private boolean selected;

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.id);
                dest.writeString(this.gname);
                dest.writeString(this.title);
                dest.writeByte(this.selected ? (byte) 1 : (byte) 0);
            }

            protected ListBean(Parcel in) {
                this.id = in.readInt();
                this.gname = in.readString();
                this.title = in.readString();
                this.selected = in.readByte() != 0;
            }

            public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
                @Override
                public ListBean createFromParcel(Parcel source) {
                    return new ListBean(source);
                }

                @Override
                public ListBean[] newArray(int size) {
                    return new ListBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.title);
            dest.writeList(this.list);
        }

        protected ListBeanX(Parcel in) {
            this.title = in.readString();
            this.list = new ArrayList<ListBean>();
            in.readList(this.list, ListBean.class.getClassLoader());
        }

        public static final Creator<ListBeanX> CREATOR = new Creator<ListBeanX>() {
            @Override
            public ListBeanX createFromParcel(Parcel source) {
                return new ListBeanX(source);
            }

            @Override
            public ListBeanX[] newArray(int size) {
                return new ListBeanX[size];
            }
        };
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
        dest.writeTypedList(this.texture);
        dest.writeTypedList(this.list);
        dest.writeInt(this.amount);
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
        this.texture = in.createTypedArrayList(TextureBean.CREATOR);
        this.list = in.createTypedArrayList(ListBeanX.CREATOR);
        this.amount = in.readInt();
    }

    public static final Creator<GoodsPropertyBean> CREATOR = new Creator<GoodsPropertyBean>() {
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
