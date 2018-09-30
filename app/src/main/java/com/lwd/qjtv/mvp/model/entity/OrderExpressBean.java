package com.lwd.qjtv.mvp.model.entity;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/7/21.
 */

public class OrderExpressBean {

    /**
     * msg : success
     * status : 1
     * data : {"mailNo":"VA34479089050","update":1500603411472,"updateStr":"2017-07-21 10:16:51","ret_code":0,"flag":true,"dataSize":12,"status":4,"tel":"400-603-3600","data":[{"time":"2017-04-06 08:49:29","context":"货物已完成配送，感谢您选择京东配送"},{"time":"2017-04-06 07:58:15","context":"配送员开始配送，请您准备收货，配送员，张小川，手机号，81370725或14734470725"},{"time":"2017-04-05 18:07:40","context":"配送时无法联系到您，我们将在3日内为你再次配送"},{"time":"2017-04-05 14:48:27","context":"配送员开始配送，请您准备收货，配送员，张小川，手机号，81370725或14734470725"},{"time":"2017-04-05 14:35:15","context":"货物已分配，等待配送"},{"time":"2017-04-05 14:32:04","context":"货物已到达【重庆北碚站】"},{"time":"2017-04-05 12:11:18","context":"货物已完成分拣，离开【重庆巴南分拨中心】"},{"time":"2017-04-05 10:47:23","context":"货物已到达【重庆巴南分拨中心】"},{"time":"2017-04-03 20:43:54","context":"货物已完成分拣，离开【北京通州分拣中心】"},{"time":"2017-04-03 19:38:47","context":"货物已到达【北京通州分拣中心】"},{"time":"2017-04-03 19:26:04","context":"货物已到达【北京通州分拣中心】"},{"time":"2017-04-03 17:14:31","context":"货物已交付京东快递"}],"expSpellName":"jingdong","expTextName":"京东快递"}
     */

    private String msg;
    private String status;
    private DataBeanX data;

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

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * mailNo : VA34479089050
         * update : 1500603411472
         * updateStr : 2017-07-21 10:16:51
         * ret_code : 0
         * flag : true
         * dataSize : 12
         * status : 4
         * tel : 400-603-3600
         * data : [{"time":"2017-04-06 08:49:29","context":"货物已完成配送，感谢您选择京东配送"},{"time":"2017-04-06 07:58:15","context":"配送员开始配送，请您准备收货，配送员，张小川，手机号，81370725或14734470725"},{"time":"2017-04-05 18:07:40","context":"配送时无法联系到您，我们将在3日内为你再次配送"},{"time":"2017-04-05 14:48:27","context":"配送员开始配送，请您准备收货，配送员，张小川，手机号，81370725或14734470725"},{"time":"2017-04-05 14:35:15","context":"货物已分配，等待配送"},{"time":"2017-04-05 14:32:04","context":"货物已到达【重庆北碚站】"},{"time":"2017-04-05 12:11:18","context":"货物已完成分拣，离开【重庆巴南分拨中心】"},{"time":"2017-04-05 10:47:23","context":"货物已到达【重庆巴南分拨中心】"},{"time":"2017-04-03 20:43:54","context":"货物已完成分拣，离开【北京通州分拣中心】"},{"time":"2017-04-03 19:38:47","context":"货物已到达【北京通州分拣中心】"},{"time":"2017-04-03 19:26:04","context":"货物已到达【北京通州分拣中心】"},{"time":"2017-04-03 17:14:31","context":"货物已交付京东快递"}]
         * expSpellName : jingdong
         * expTextName : 京东快递
         */

        private String mailNo;
        private long update;
        private String updateStr;
        private int ret_code;
        private boolean flag;
        private int dataSize;
        private int status;
        private String tel;
        private String expSpellName;
        private String expTextName;
        private List<DataBean> data;

        public String getMailNo() {
            return mailNo;
        }

        public void setMailNo(String mailNo) {
            this.mailNo = mailNo;
        }

        public long getUpdate() {
            return update;
        }

        public void setUpdate(long update) {
            this.update = update;
        }

        public String getUpdateStr() {
            return updateStr;
        }

        public void setUpdateStr(String updateStr) {
            this.updateStr = updateStr;
        }

        public int getRet_code() {
            return ret_code;
        }

        public void setRet_code(int ret_code) {
            this.ret_code = ret_code;
        }

        public boolean isFlag() {
            return flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        public int getDataSize() {
            return dataSize;
        }

        public void setDataSize(int dataSize) {
            this.dataSize = dataSize;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getExpSpellName() {
            return expSpellName;
        }

        public void setExpSpellName(String expSpellName) {
            this.expSpellName = expSpellName;
        }

        public String getExpTextName() {
            return expTextName;
        }

        public void setExpTextName(String expTextName) {
            this.expTextName = expTextName;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * time : 2017-04-06 08:49:29
             * context : 货物已完成配送，感谢您选择京东配送
             */

            private String time;
            private String context;

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getContext() {
                return context;
            }

            public void setContext(String context) {
                this.context = context;
            }
        }
    }
}
