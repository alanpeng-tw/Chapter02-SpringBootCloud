package com.alan.dao;

import com.alan.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserInfoDao {
    UserInfo selectByMobileNo(String mobileNo);
    int insert(UserInfo userInfo);
    int updateById(UserInfo userInfo);
}
