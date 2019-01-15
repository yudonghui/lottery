package com.daxiang.lottery.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/10/20 0020.
 */
public class PictureData {

    /**
     * data : {"items":[{"id":44,"title":"轮播图","category":5,"publishDate":"2016/12/26","author":"admin","content":"<p>admin<\/p>","backgroundColor":"#000000","status":0,"typeFlag":"banner-android","tagA":"","keywords":"","remarks":"1|42","logoURL":"static/upload/image/logo20161226145943552943.png"}]}
     * pageCount : 1
     * sign : 1233
     * msg : OK
     * code : 0
     * timeStamp : 131273107889147427
     */

    private DataBean data;
    private int pageCount;
    private String sign;
    private String msg;
    private int code;
    private long timeStamp;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public static class DataBean {
        private List<ItemsBean> items;

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            /**
             * id : 44
             * title : 轮播图
             * category : 5
             * publishDate : 2016/12/26
             * author : admin
             * content : <p>admin</p>
             * backgroundColor : #000000
             * status : 0
             * typeFlag : banner-android
             * tagA :
             * keywords :
             * remarks : 1|42
             * logoURL : static/upload/image/logo20161226145943552943.png
             */

            private int id;
            private String title;
            private int category;
            private String publishDate;
            private String author;
            private String content;
            private String backgroundColor;
            private int status;
            private String typeFlag;
            private String tagA;
            private String keywords;
            private String remarks;
            private String logoURL;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getCategory() {
                return category;
            }

            public void setCategory(int category) {
                this.category = category;
            }

            public String getPublishDate() {
                return publishDate;
            }

            public void setPublishDate(String publishDate) {
                this.publishDate = publishDate;
            }

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getBackgroundColor() {
                return backgroundColor;
            }

            public void setBackgroundColor(String backgroundColor) {
                this.backgroundColor = backgroundColor;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getTypeFlag() {
                return typeFlag;
            }

            public void setTypeFlag(String typeFlag) {
                this.typeFlag = typeFlag;
            }

            public String getTagA() {
                return tagA;
            }

            public void setTagA(String tagA) {
                this.tagA = tagA;
            }

            public String getKeywords() {
                return keywords;
            }

            public void setKeywords(String keywords) {
                this.keywords = keywords;
            }

            public String getRemarks() {
                return remarks;
            }

            public void setRemarks(String remarks) {
                this.remarks = remarks;
            }

            public String getLogoURL() {
                return logoURL;
            }

            public void setLogoURL(String logoURL) {
                this.logoURL = logoURL;
            }
        }
    }
}
