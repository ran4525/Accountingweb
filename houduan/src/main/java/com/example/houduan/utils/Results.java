package com.example.houduan.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Results {
    private int code;
    private String msg;
    private Object data;

    public int getCode() {
        return code;
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

    public void setCode(int code) {
        this.code = code;
    }

    public Results(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;  // 允许 null
    }
}
