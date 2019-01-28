package com.clearliang.frameworkdemo.model.bean;

/**
 * Created by ClearLiang on 2018/11/23
 * <p>
 * Function :
 */
public class LoginBean {

    /**
     * msg : null
     * status : 0
     * data : {"token":"C4EX22244V7IHF58ZD693DAMXJVS327W"}
     */

    private String msg;
    private int status;
    private DataBean data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
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
         * token : C4EX22244V7IHF58ZD693DAMXJVS327W
         */

        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
