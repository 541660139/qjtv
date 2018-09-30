package com.lwd.qjtv.mvp.model.entity;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/7/17.
 */

public class SearchMallBean {

    /**
     * msg : success
     * status : 1
     * data : [{"goods_id":"1","goods_name":"皇牌台球桌1","shop_price":"188.00","goods_brief":"皇牌台球桌 标准桌球美式黑8 家用乒乓球二合一高档台球","thumb_img":"http://img.ttkhj.3z.cc/slk/images/tq.png?x-oss-process=image/resize,m_fill,h_150,w_150"},{"goods_id":"2","goods_name":"皇牌台球桌2","shop_price":"288.00","goods_brief":"皇牌台球桌 标准桌球美式黑8 家用乒乓球二合一高档台球","thumb_img":"http://img.ttkhj.3z.cc/slk/images/tq.png?x-oss-process=image/resize,m_fill,h_150,w_150"},{"goods_id":"3","goods_name":"皇牌台球桌3","shop_price":"388.00","goods_brief":"皇牌台球桌 标准桌球美式黑8 家用乒乓球二合一高档台球","thumb_img":"http://img.ttkhj.3z.cc/slk/images/tq.png?x-oss-process=image/resize,m_fill,h_150,w_150"}]
     */

    private String msg;
    private String status;
    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * goods_id : 1
         * goods_name : 皇牌台球桌1
         * shop_price : 188.00
         * goods_brief : 皇牌台球桌 标准桌球美式黑8 家用乒乓球二合一高档台球
         * thumb_img : http://img.ttkhj.3z.cc/slk/images/tq.png?x-oss-process=image/resize,m_fill,h_150,w_150
         */

        private String goods_id;
        private String goods_name;
        private String shop_price;
        private String goods_brief;
        private String thumb_img;

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getShop_price() {
            return shop_price;
        }

        public void setShop_price(String shop_price) {
            this.shop_price = shop_price;
        }

        public String getGoods_brief() {
            return goods_brief;
        }

        public void setGoods_brief(String goods_brief) {
            this.goods_brief = goods_brief;
        }

        public String getThumb_img() {
            return thumb_img;
        }

        public void setThumb_img(String thumb_img) {
            this.thumb_img = thumb_img;
        }
    }
}
