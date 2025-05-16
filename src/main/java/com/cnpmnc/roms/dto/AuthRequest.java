package com.cnpmnc.roms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {
    private String username;
    private String password;
}

@Getter
@Setter
public class UserResponse {
    private String username;
    private String password;
}
