package com.lwd.qjtv.mvp.model.entity;

/**
 * Created by ZhengQian on 2017/12/9.
 * E-mail : wwwiiivip@yeah.net
 * If anyelse things , mail to it . I'll be soon to reply .
 */

public class AliPayBean {

    /**
     * msg : request
     * status : 1
     * data : {"orderInfo":"cGFydG5lcj0iMjA4ODYyMTYxMTc4OTQ0NyImc2VsbGVyX2lkPSI3ODMyODk2QHFxLmNvbSImb3V0X3RyYWRlX25vPSIyMDE3MTIwOTE3MDIxMTAwMjFveTh4ejlTTEsiJnN1YmplY3Q9Iui0reS5sOeQg+W4gSImYm9keT0i6LSt5Lmw55CD5biBIiZ0b3RhbF9mZWU9IjYuMDAiJm5vdGlmeV91cmw9Imh0dHA6Ly9zbGsuM3ouY2MvaW5kZXgucGhwL3BheS9hbGlwYXkvbm90aWZ5X3VybCImc2VydmljZT0ibW9iaWxlLnNlY3VyaXR5cGF5LnBheSImcGF5bWVudF90eXBlPSIxIiZfaW5wdXRfY2hhcnNldD0iVVRGLTgiJml0X2JfcGF5PSIzMG0i","out_trade_no":"201712091702110021oy8xz9SLK","order_sign":"Uek1aQX+1wKhweqkf/5bgawqNQjxGeDzXhNZMDtYu5HBbLKlY+z/BqBJ0nkYAoey1t2F/jAJHQj1GuNVFRaRh1D293OLbKN4CMShnR/AlXToleteWPh6IdejcJjHoWbs6OB5thpdTcAPap/2f42OFefl3ft05srIEbQUrk9QHRo=","md5_sign":"0265b77a15ab23ef81f69a07c2318716"}
     */

    private String msg;
    private String status;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * orderInfo : cGFydG5lcj0iMjA4ODYyMTYxMTc4OTQ0NyImc2VsbGVyX2lkPSI3ODMyODk2QHFxLmNvbSImb3V0X3RyYWRlX25vPSIyMDE3MTIwOTE3MDIxMTAwMjFveTh4ejlTTEsiJnN1YmplY3Q9Iui0reS5sOeQg+W4gSImYm9keT0i6LSt5Lmw55CD5biBIiZ0b3RhbF9mZWU9IjYuMDAiJm5vdGlmeV91cmw9Imh0dHA6Ly9zbGsuM3ouY2MvaW5kZXgucGhwL3BheS9hbGlwYXkvbm90aWZ5X3VybCImc2VydmljZT0ibW9iaWxlLnNlY3VyaXR5cGF5LnBheSImcGF5bWVudF90eXBlPSIxIiZfaW5wdXRfY2hhcnNldD0iVVRGLTgiJml0X2JfcGF5PSIzMG0i
         * out_trade_no : 201712091702110021oy8xz9SLK
         * order_sign : Uek1aQX+1wKhweqkf/5bgawqNQjxGeDzXhNZMDtYu5HBbLKlY+z/BqBJ0nkYAoey1t2F/jAJHQj1GuNVFRaRh1D293OLbKN4CMShnR/AlXToleteWPh6IdejcJjHoWbs6OB5thpdTcAPap/2f42OFefl3ft05srIEbQUrk9QHRo=
         * md5_sign : 0265b77a15ab23ef81f69a07c2318716
         */

        private String orderInfo;
        private String out_trade_no;
        private String order_sign;
        private String md5_sign;

        public String getOrderInfo() {
            return orderInfo;
        }

        public void setOrderInfo(String orderInfo) {
            this.orderInfo = orderInfo;
        }

        public String getOut_trade_no() {
            return out_trade_no;
        }

        public void setOut_trade_no(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }

        public String getOrder_sign() {
            return order_sign;
        }

        public void setOrder_sign(String order_sign) {
            this.order_sign = order_sign;
        }

        public String getMd5_sign() {
            return md5_sign;
        }

        public void setMd5_sign(String md5_sign) {
            this.md5_sign = md5_sign;
        }
    }
}
