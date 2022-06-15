package com.example.ApiDoAn.reponse;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class TokenRefreshResponse {
    private  int status;
    private String accessToken;
    private String refreshToken;
    private String type = "Bearer";

    public TokenRefreshResponse(int status,String accessToken, String refreshToken) {
       this.status=status;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
