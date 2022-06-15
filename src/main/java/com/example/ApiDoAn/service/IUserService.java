package com.example.ApiDoAn.service;

import javax.mail.MessagingException;

import com.example.ApiDoAn.entity.UserEntity;
import com.example.ApiDoAn.request.RegisterEmail;
import com.example.ApiDoAn.request.RegisterReq;
import com.example.ApiDoAn.request.ResetPasswordRequest;

import java.io.IOException;

public interface IUserService {// tạo ra để kế thừa
	
    UserEntity findById(Long id);

    boolean finByUserName(String username);
    boolean finByEmail(String email);

    String registerUser(RegisterReq RegisterReq) throws MessagingException, IOException;

    boolean verify(int verificationCode);

    boolean refeshVerifyCode(String email);

    boolean checkForgot(String email);


    boolean ResetPassword(ResetPasswordRequest resetPasswordRequest);

    boolean checklogout(String userName);

    String registerEmail(RegisterEmail registerEmail) throws MessagingException, IOException;
}
