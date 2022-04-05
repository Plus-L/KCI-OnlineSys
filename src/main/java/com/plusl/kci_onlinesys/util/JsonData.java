package com.plusl.kci_onlinesys.util;

/**
 * @program: kci_onlinesys
 * @description: 返回Json数据
 * @author: PlusL
 * @create: 2022-03-09 18:35
 **/

public class JsonData {

    private Integer code; // 状态吗 1代表成功 -1 代表失败

    private Object data; // 返回的数据

    private String msg; // 当前json信息描述

    private Integer count;

    public JsonData() {
        super();
    }

    public JsonData(Integer code, Object data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    /***
     * 操作成功，没有数据返回，携带自定义描述信息
     * @param msg
     * @return
     */
    public static JsonData buildSuccess(String msg){
        return new JsonData(0, null, msg);
    }

    public static JsonData buildSuccess(Object data, String msg){
        return new JsonData(0, data, msg);
    }

    public static JsonData buildError(String msg){
        return new JsonData(-1, null, msg);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "JsonData{" +
                "code=" + code +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                ", count=" + count +
                '}';
    }
}