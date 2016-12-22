package com.home.dab.datum.demo.net.tool;


import com.google.gson.JsonObject;

/**
 * Created by DAB on 2016/8/15 17:10.
 */
public class ResultContent {

    private int success;
    private String msg;
    private JsonObject data;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMsg() {
        if (msg == null || msg.equals("")) {
            return "";
        }
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public JsonObject getData() {
        return data;
    }

    public void setData(JsonObject data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultContent{" +
                "success=" + success +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
