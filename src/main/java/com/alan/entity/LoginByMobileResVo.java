package com.alan.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class LoginByMobileResVo implements Serializable {

    private String userId;
    private String accessToken;
}
