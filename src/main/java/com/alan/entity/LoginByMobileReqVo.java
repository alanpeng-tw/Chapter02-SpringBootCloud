package com.alan.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class LoginByMobileReqVo implements Serializable {

    private String reqId;
    private String mobileNo;
    private String smsCode;
}
