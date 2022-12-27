package com.alan.service.impl;

import com.alan.dao.UserInfoDao;
import com.alan.dao.UserSmsCodeDao;
import com.alan.entity.BizException;
import com.alan.entity.GetSmsCodeReqVo;
import com.alan.entity.LoginByMobileReqVo;
import com.alan.entity.LoginByMobileResVo;
import com.alan.entity.LoginExitReqVo;
import com.alan.entity.UserInfo;
import com.alan.entity.UserSmsCode;
import com.alan.service.UserService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    UserSmsCodeDao userSmsCodeDao;

    @Autowired
    UserInfoDao userInfoDao;

    @Autowired(required = false)
    RedisTemplate redisTemplate;

    @Override
    public boolean getSmsCode(GetSmsCodeReqVo getSmsCodeReqVo){
        //隨機生成6位數簡訊驗證碼
        String smsCode = String.valueOf((int) Math.random() * 100000 + 1);

        //真實場景中,這裡需要呼叫簡訊平台介面
        //儲存使用者簡訊驗證碼資訊至簡訊驗證碼資訊表
        UserSmsCode userSmsCode = UserSmsCode.builder().mobileNo(getSmsCodeReqVo.getMobileNo()).smsCode(smsCode)
                .sendTime(new Timestamp(new Date().getTime()))
                .createTime(new Timestamp(new Date().getTime()))
                .build();

        userSmsCodeDao.insert(userSmsCode);
        return true;
    }

    public LoginByMobileResVo loginByMobile(LoginByMobileReqVo loginByMobileReqVo) throws BizException {

        //1.驗證簡訊驗證碼是否正確
        UserSmsCode userSmsCode = userSmsCodeDao.selectByMobileNo(loginByMobileReqVo.getMobileNo());
        if(userSmsCode == null){
            throw new BizException(-1, "驗證碼輸入錯誤");
        } else if( !userSmsCode.getSmsCode().equals(loginByMobileReqVo.getSmsCode())){
            throw new BizException(-1, "驗證碼輸入錯誤");
        }


        //2.判斷使用者是否已註冊
        UserInfo userInfo = userInfoDao.selectByMobileNo(loginByMobileReqVo.getMobileNo());
        if(userInfo == null){
            //隨機生成使用者ID
            String userId = String.valueOf((int) Math.random() * 100000 + 1);


            userInfo = UserInfo.builder().userId(userId).mobileNo(loginByMobileReqVo.getMobileNo())
                    .isLogin("1")
                    .loginTime(new Timestamp(new Date().getTime()))
                    .createTime(new Timestamp(new Date().getTime()))
                    .build();

            //完成系統預設註冊流冊
            userInfoDao.insert(userInfo);
        }else{
            userInfo.setIsLogin("1");
            userInfo.setLoginTime(new Timestamp(new Date().getTime()));
            userInfoDao.updateById(userInfo);
        }

        //3.生成使用者階段資訊
        String accessToken = UUID.randomUUID().toString().toUpperCase().replaceAll("-","");

        //將使用者資訊save to Redis service
        redisTemplate.opsForValue().set("accessToken",userInfo,30, TimeUnit.DAYS);

        //封裝回應參數
        LoginByMobileResVo loginByMobileResVo = LoginByMobileResVo.builder()
                .userId(userInfo.getUserId())
                .accessToken(accessToken).build();

        return loginByMobileResVo;
    }

    @Override
    public boolean loginExit(LoginExitReqVo loginExitReqVo){
        try{
            redisTemplate.delete(loginExitReqVo.getAccessToken());
            return true;
        }catch(Exception e){
            log.error(e.toString() + "_" + e);
            return false;
        }
    }
}
