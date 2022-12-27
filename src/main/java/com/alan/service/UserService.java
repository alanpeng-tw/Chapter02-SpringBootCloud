package com.alan.service;

import com.alan.entity.*;

public interface UserService {

    boolean getSmsCode(GetSmsCodeReqVo getSmsCodeReqVo);

    LoginByMobileResVo loginByMobile(LoginByMobileReqVo loginByMobileReqVo) throws BizException;

    boolean loginExit(LoginExitReqVo loginExitReqVo);
}
