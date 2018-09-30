package com.lwd.qjtv.mvp.model.entity;

/**
 * Created by ZhengQian on 2018/1/29.
 * E-mail : wwwiiivip@yeah.net
 * If anyelse things , mail to it . I'll be soon to reply .
 */

public class BBSMessageListBean {

    /**
     * msg : success
     * status : 1
     * data : {"card":{"num":"4","content":"这里是评论这里是评论这里是评论7"},"zan":{"num":"3","user":"u107018、u100000、超管110"}}
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
         * card : {"num":"4","content":"这里是评论这里是评论这里是评论7"}
         * zan : {"num":"3","user":"u107018、u100000、超管110"}
         */

        private CardBean card;
        private ZanBean zan;

        public CardBean getCard() {
            return card;
        }

        public void setCard(CardBean card) {
            this.card = card;
        }

        public ZanBean getZan() {
            return zan;
        }

        public void setZan(ZanBean zan) {
            this.zan = zan;
        }

        public static class CardBean {
            /**
             * num : 4
             * content : 这里是评论这里是评论这里是评论7
             */

            private String num;
            private String content;

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }

        public static class ZanBean {
            /**
             * num : 3
             * user : u107018、u100000、超管110
             */

            private String num;
            private String user;

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public String getUser() {
                return user;
            }

            public void setUser(String user) {
                this.user = user;
            }
        }
    }
}
