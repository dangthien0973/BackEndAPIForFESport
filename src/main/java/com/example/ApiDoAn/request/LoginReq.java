package com.example.ApiDoAn.request;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginReq {
    @NotBlank
    private String username;
    @NotBlank
    @Min(value = 8, message = "Password phải từ 8 kí tự trở lên")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
