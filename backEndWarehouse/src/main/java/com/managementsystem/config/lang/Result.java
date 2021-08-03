package com.managementsystem.config.lang;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {
    private  int code;
    private String msg;
    private Object data;

    public static Result success(Object data){
        Result r = new Result();
        r.setCode(200);
        r.setMsg("操作成功");
        r.setData(data);
        return r;

    }
    public static Result success(int code, String msg, Object data){
        Result r = new Result();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;

    }
    public static Result fail(String msg){
        Result r = new Result();
        r.setCode(400);
        r.setMsg(msg);
        r.setData(null);
        return r;

    }
    public static Result fail(int code, String msg, Object data){
        Result r = new Result();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;

    }
}
