package com.remidiousE.dto.request;

import lombok.Data;

@Data
public class MemberLoginRequest {
    private String userName;
    private String password;
}
