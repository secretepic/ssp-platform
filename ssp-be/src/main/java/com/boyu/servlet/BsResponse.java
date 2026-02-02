package com.boyu.servlet;


import java.util.HashMap;

public class BsResponse extends HashMap<String, Object> {

    public static BsResponse ok() {
        BsResponse res = new BsResponse();
        res.put("code", 200);
        res.put("msg", "success");
        return res;
    }

    public static BsResponse error() {
        BsResponse res = new BsResponse();
        res.put("code", 500);
        res.put("msg", "未知异常，请联系管理员");
        return res;
    }

    public static BsResponse error(String msg) {
        BsResponse res = new BsResponse();
        res.put("code", 500);
        res.put("msg", msg);
        return res;
    }

}
