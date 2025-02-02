package com.example.houduan.controller;

import com.example.houduan.service.UsersService;
import com.example.houduan.utils.Results;
import jakarta.annotation.Resource;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
public class UsersController {
    @Autowired
    private UsersService usersService;

    @RequestMapping("/login")
    public Results login(@RequestBody Map<String, String> params) {
        String name = params.get("username"); // 获取前端传来的 username
        String password = params.get("password"); // 获取前端传来的 password
        return usersService.login(name, password);
    }

    @RequestMapping("/regist")
    public Results regist(@RequestBody Map<String, String> params) {
        String name = params.get("username"); // 获取前端传来的 username
        String password = params.get("password"); // 获取前端传来的 password
        return usersService.regist(name, password);
    }

    @RequestMapping("/submitForm")
    public Results submitForm(@RequestBody Map<String, String> params) {
        String name = params.get("name");
        String type = params.get("type");
        int number = Integer.parseInt(params.get("number"));
        String types = params.get("types");
        String date = params.get("date");
        String cex = params.get("cex");
        if (name.contains(",")) {
            name = name.split(",")[0];  // 取逗号前的部分
        }
        if ("shouru".equals(type)) {
            return usersService.shouru(name, number, types, date, cex);
        } else if ("zhichu".equals(type)) {
            return usersService.zhichu(name, number, types, date, cex);
        } else {
            return new Results(1, "错误请求", "Invalid type");
        }
    }

    @RequestMapping("/search")
    public Results search(@RequestBody Map<String, String> params) {
        try {
            String name = params.get("name");
            String date = params.get("date");
            String year = null, month = null, day = null;

            if (date == null || date.isEmpty()) {
                return new Results(1, "缺少日期参数", "Missing date parameter");
            }

            String[] dateParts = date.split("-");
            if (dateParts.length == 3) {
                year = dateParts[0];
                month = dateParts[1];
                day = dateParts[2];
            } else if (dateParts.length == 2){
                year = dateParts[0];
                month = dateParts[1];
                return usersService.search(name, year, month, day);
            }

            // 调用业务逻辑层
            return usersService.search(name, year, month, day);
        } catch (Exception e) {
            e.printStackTrace();  // 打印详细的异常堆栈信息
            return new Results(500, "服务器内部错误", "Internal Server Error: " + e.getMessage());
        }
    }

}
