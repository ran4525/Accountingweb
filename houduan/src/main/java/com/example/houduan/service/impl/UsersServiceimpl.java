package com.example.houduan.service.impl;

import com.example.houduan.mapper.UsersMapper;
import com.example.houduan.pojo.Users;
import com.example.houduan.pojo.Bills;
import com.example.houduan.utils.Results;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.houduan.service.UsersService;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;


@Service

public class UsersServiceimpl extends UsersService {
    public static boolean isValidDate(String dateStr) {
        try {
            LocalDate date = LocalDate.parse(dateStr);
            return date.getDayOfMonth() <= date.lengthOfMonth();  // 确保日期在当前月范围内
        } catch (DateTimeParseException e) {
            return false;
        }
    }


    @Autowired(required = false)
    private UsersMapper usersMapper;
    @Override
    public Results login(String name, String password){
        Users users=usersMapper.login(name);
        if(users==null){
            return new Results(1,"用户名不存在",null);
        }else {
            if(users.getPassword().equals(password)){
                return new Results(0,"登录成功",users);
            }else{
                return new Results(2,"密码错误",null);
            }
        }
    }
    public Results regist(String name, String password){
        Users users=usersMapper.login(name);
        if(users!=null){
            return new Results(1,"用户名已存在",null);
        }else {
            int user=usersMapper.regist(name,password);
            return new Results(0,"注册成功",users);
        }
    }
    public Results shouru(String name, int number, String types, String date, String cex){
        int users=usersMapper.shouru(name,number,types,date,cex);
        System.out.println(name);
        return new Results(0,"添加成功",users);
    }
    public Results zhichu(String name,int number,String types,String date,String cex){
        int users=usersMapper.zhichu(name,number,types,date,cex);
        return new Results(0,"添加成功",users);
    }
    public Results search(String name, String year, String month, String day) {
        // 如果年份为空，直接返回错误
        if (year == null) {
            return new Results(1, "年份不可为空", null);
        }
        String date = year;
        // 判断是否提供了月份
        if (month != null && !month.isEmpty()) {
            date += "-" + month;
            // 判断是否提供了日期
            if (day != null && !day.isEmpty()) {
                date += "-" + day;
                List<Bills> users_shouruList = (List<Bills>) usersMapper.searchshouru(name, date);
                List<Bills> users_zhichuList = (List<Bills>) usersMapper.searchzhichu(name, date);
                // 如果没有查询结果，返回空列表
                if (users_shouruList == null) {
                    users_shouruList = new ArrayList<>();
                }
                if (users_zhichuList == null) {
                    users_zhichuList = new ArrayList<>();
                }
                return new Results(0, "查询成功", Arrays.asList(users_shouruList, users_zhichuList));
            } else {
                // 查询该月所有日期
                List<Bills> users_shouruList = new ArrayList<>();
                List<Bills> users_zhichuList = new ArrayList<>();
                for (int i = 1; i <= 31; i++) {
                    String dayStr = String.format("%02d", i);  // 格式化为两位数
                    String date1 = date + "-" + dayStr;
                    if (isValidDate(date1)) {
                        // 查询多个结果
                        List<Bills> users_shouruListForDay = (List<Bills>) usersMapper.searchshouru(name, date1);
                        List<Bills> users_zhichuListForDay = (List<Bills>) usersMapper.searchzhichu(name, date1);
                        if (users_shouruListForDay != null) users_shouruList.addAll(users_shouruListForDay);
                        if (users_zhichuListForDay != null) users_zhichuList.addAll(users_zhichuListForDay);
                    }
                }
                return new Results(0, "查询成功", Arrays.asList(users_shouruList, users_zhichuList));
            }
        } else {
            // 如果没有提供月份，查询整个年份的数据
            List<Bills> users_shouruList = new ArrayList<>();
            List<Bills> users_zhichuList = new ArrayList<>();
            for (int i = 1; i <= 12; i++) {
                String monthStr = String.format("%02d", i);  // 格式化为两位数
                String date1 = date + "-" + monthStr;
                if (day != null && !day.isEmpty()) {
                    // 如果提供了日期，则拼接完整日期
                    String date2 = date1 + "-" + day;
                    List<Bills> users_shouruForDay = (List<Bills>) usersMapper.searchshouru(name, date2);
                    List<Bills> users_zhichuForDay = (List<Bills>) usersMapper.searchzhichu(name, date2);
                    if (users_shouruForDay != null) users_shouruList.addAll(users_shouruForDay);
                    if (users_zhichuForDay != null) users_zhichuList.addAll(users_zhichuForDay);
                } else {
                    // 查询该月所有日期
                    for (int j = 1; j <= 31; j++) {
                        String dayStr = String.format("%02d", j);  // 格式化为两位数
                        String date2 = date1 + "-" + dayStr;
                        if (isValidDate(date2)) {
                            List<Bills> users_shouruForDay = (List<Bills>) usersMapper.searchshouru(name, date2);
                            List<Bills> users_zhichuForDay = (List<Bills>) usersMapper.searchzhichu(name, date2);
                            if (users_shouruForDay != null) users_shouruList.addAll(users_shouruForDay);
                            if (users_zhichuForDay != null) users_zhichuList.addAll(users_zhichuForDay);
                        }
                    }
                }
            }
            return new Results(0, "查询成功", Arrays.asList(users_shouruList, users_zhichuList));
        }
    }
}
