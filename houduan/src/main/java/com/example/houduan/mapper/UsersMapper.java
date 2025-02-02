package com.example.houduan.mapper;
import com.example.houduan.pojo.Users;
import com.example.houduan.pojo.Bills;
import com.example.houduan.utils.Results;
import lombok.Data;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

//接口：由interface修饰，均是抽象方法，没有方法体
@Component
@Mapper
public interface UsersMapper {
    public Users login(String name);
    public int regist(String name,String password);
    public int shouru(String name, int number, String types, String date, String cex);
    public int zhichu(String name,int number,String types,String date,String cex);
    public List<Bills> searchshouru(String name, String date);
    public List<Bills> searchzhichu(String name, String date);
}
