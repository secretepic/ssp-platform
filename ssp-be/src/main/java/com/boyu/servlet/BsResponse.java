package com.boyu.servlet;


import jakarta.servlet.http.HttpServletResponse;

import java.util.HashMap;

public class BsResponse extends HashMap<String, Object> {

    public static BsResponse ok() {
        BsResponse res = new BsResponse();
        res.put("code", 200);
        res.put("msg", "success");
        return res;
    }

    public static <T> BsResponse ok(T data) {
        BsResponse res = new BsResponse();
        res.put("code", 200);
        res.put("msg", "success");
        res.put("data", data);
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

    public static BsResponse noAuth(String msg, String path) {
        BsResponse res = new BsResponse();
        res.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        res.put("error", "Unauthorized");
        res.put("message", msg);
        res.put("path", path);
        return res;
    }

}
