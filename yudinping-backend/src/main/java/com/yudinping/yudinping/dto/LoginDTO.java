package com.yudinping.yudinping.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class LoginDto {
    private String userid;
    private String password;
    
    @Builder 
    public LoginDto(String userid, String password) {
        this.userid = userid;
        this.password = password;
    }
}
