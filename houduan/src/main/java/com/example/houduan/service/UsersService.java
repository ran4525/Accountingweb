package com.example.houduan.service;

import com.example.houduan.utils.Results;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
public abstract class UsersService {
    public abstract Results login(String name, String password);
    public abstract Results regist(String name, String password);
    public abstract Results shouru(String name, int number, String types, String date, String cex);
    public abstract Results zhichu(String name, int number,String types,String date,String cex);
    public abstract Results search(String name, String year,String month,String day);
}
