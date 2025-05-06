package com.yudinping.yudinping.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignUpDto {
    private String userid;
    private String password;
    private String name;

    @Builder
    public SignUpDto(String userid, String password, String name) {
        this.userid = userid;
        this.password = password;
        this.name = name;
    }
}
